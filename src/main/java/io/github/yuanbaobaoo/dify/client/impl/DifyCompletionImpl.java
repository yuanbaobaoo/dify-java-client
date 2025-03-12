package io.github.yuanbaobaoo.dify.client.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.client.IDifyCompletion;
import io.github.yuanbaobaoo.dify.client.params.ParamMessage;
import io.github.yuanbaobaoo.dify.client.types.DifyChatEvent;
import io.github.yuanbaobaoo.dify.client.types.DifyChatResult;
import io.github.yuanbaobaoo.dify.routes.DifyRoutes;
import io.github.yuanbaobaoo.dify.routes.HttpMethod;
import io.github.yuanbaobaoo.dify.types.DifyException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
public class DifyCompletionImpl extends DifyBaseClientImpl implements IDifyCompletion {
    /**
     * constructor
     *
     * @param server Dify Server URL
     * @param apiKey The App Api Key
     */
    public DifyCompletionImpl(String server, String apiKey) {
        super(server, apiKey);
    }

    @Override
    public DifyChatResult sendMessages(ParamMessage message) {
        try {
            formatMessage(message);
            String result = requestBlocking(DifyRoutes.COMPLETION_MESSAGES, null, message.toMap());
            return DifyChatResult.builder().event(DifyChatEvent.message).payload(JSON.parseObject(result)).build();
        } catch (DifyException e) {
            throw e;
        } catch (Exception e) {
            log.error("sendMessages", e);
            throw new DifyException("[client] 消息发送异常", 500);
        }
    }

    @Override
    public CompletableFuture<Void> sendMessagesAsync(ParamMessage message, Consumer<DifyChatResult> consumer) {
        formatMessage(message);

        return requestStreaming(DifyRoutes.COMPLETION_MESSAGES, null, message.toMap(), (line) -> {
            JSONObject json = JSON.parseObject(line);
            consumer.accept(DifyChatResult.builder().event(json.getString("event")).payload(json).build());
        });
    }

    @Override
    public Boolean stopGenerate(String taskId, String user) {
        try {
            String result = requestJson(
                    String.format("%s/%s/stop", DifyRoutes.COMPLETION_MESSAGES.getUrl(), taskId),
                    HttpMethod.POST,
                    null,
                    new HashMap<>() {{
                        put("user", user);
                    }}
            );

            JSONObject json = JSON.parseObject(result);
            return "success".equals(json.getString("result"));
        } catch (DifyException e) {
            log.error("stopGenerate: {}", e.getOriginal());
        } catch (Exception e) {
            log.error("stopGenerate", e);
        }

        return false;
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
