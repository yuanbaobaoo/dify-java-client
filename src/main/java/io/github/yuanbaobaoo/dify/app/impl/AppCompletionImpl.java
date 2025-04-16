package io.github.yuanbaobaoo.dify.app.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.SimpleHttpClient;
import io.github.yuanbaobaoo.dify.app.IAppCompletion;
import io.github.yuanbaobaoo.dify.app.params.ParamMessage;
import io.github.yuanbaobaoo.dify.app.types.DifyChatEvent;
import io.github.yuanbaobaoo.dify.app.types.DifyChatResult;
import io.github.yuanbaobaoo.dify.routes.AppRoutes;
import io.github.yuanbaobaoo.dify.types.HttpMethod;
import io.github.yuanbaobaoo.dify.types.DifyException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
public class AppCompletionImpl extends AppBaseClientImpl implements IAppCompletion {
    /**
     * constructor
     *
     * @param server Dify Server URL
     * @param apiKey The App Api Key
     */
    public AppCompletionImpl(String server, String apiKey) {
        super(server, apiKey);
    }

    @Override
    public DifyChatResult sendMessages(ParamMessage message) {
        formatMessage(message);
        String result = requestBlocking(AppRoutes.COMPLETION_MESSAGES, null, message.toMap());
        return DifyChatResult.builder().event(DifyChatEvent.message).payload(JSON.parseObject(result)).build();
    }

    @Override
    public CompletableFuture<Void> sendMessagesAsync(ParamMessage message, Consumer<DifyChatResult> consumer) {
        formatMessage(message);

        return requestStreaming(AppRoutes.COMPLETION_MESSAGES, null, message.toMap(), (line) -> {
            JSONObject json = JSON.parseObject(line);
            consumer.accept(DifyChatResult.builder().event(json.getString("event")).payload(json).build());
        });
    }

    @Override
    public Boolean stopGenerate(String taskId, String user) {
        String result = SimpleHttpClient.get(config).requestJson(
                String.format("%s/%s/stop", AppRoutes.COMPLETION_MESSAGES.getUrl(), taskId),
                HttpMethod.POST,
                null,
                new HashMap<>() {{
                    put("user", user);
                }}
        );

        JSONObject json = JSON.parseObject(result);
        return "success".equals(json.getString("result"));
    }

    /**
     * reset message params
     * @param message ParamMessage
     */
    private void formatMessage(ParamMessage message) {
        if (message.getQuery() != null) {
            Map<String, Object> inputs = message.getInputs();

            if (inputs == null) {
                inputs = new HashMap<>();
            }

            if (!inputs.containsKey("query") || inputs.get("query") == null) {
                inputs.put("query", message.getQuery());
            }

            message.setQuery(null);
            message.setInputs(inputs);
        }
    }

}
