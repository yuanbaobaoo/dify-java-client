package io.github.yuanbaobaoo.dify.app.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.SimpleHttpClient;
import io.github.yuanbaobaoo.dify.app.IAppChatClient;
import io.github.yuanbaobaoo.dify.app.params.ParamMessage;
import io.github.yuanbaobaoo.dify.app.types.DifyChatEvent;
import io.github.yuanbaobaoo.dify.app.types.DifyChatResult;
import io.github.yuanbaobaoo.dify.routes.AppRoutes;
import io.github.yuanbaobaoo.dify.types.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
public class AppChatClientImpl extends AppBaseClientImpl implements IAppChatClient {
    /**
     * constructor
     *
     * @param server Dify Server URL
     * @param apiKey The App Api Key
     */
    public AppChatClientImpl(String server, String apiKey) {
        super(server, apiKey);
    }

    @Override
    public DifyChatResult sendMessages(ParamMessage message) {
        String result = requestBlocking(AppRoutes.CHAT_MESSAGES, null, message.toMap());
        return DifyChatResult.builder().event(DifyChatEvent.message).payload(JSON.parseObject(result)).build();
    }

    @Override
    public CompletableFuture<Void> sendMessagesAsync(ParamMessage message, Consumer<DifyChatResult> consumer) {
        return requestStreaming(AppRoutes.CHAT_MESSAGES, null, message.toMap(), (line) -> {
            JSONObject json = JSON.parseObject(line);
            consumer.accept(DifyChatResult.builder().event(json.getString("event")).payload(json).build());
        });
    }

    @Override
    public Boolean stopResponse(String taskId, String user) {
        String result = SimpleHttpClient.get(config).requestJson(
                String.format("%s/%s/stop", AppRoutes.CHAT_MESSAGES.getUrl(), taskId),
                HttpMethod.POST,
                null,
                new HashMap<>() {{
                    put("user", user);
                }}
        );

        JSONObject json = JSON.parseObject(result);
        return "success".equals(json.getString("result"));
    }

    @Override
    public List<String> suggestedList(String messageId, String user) {
        String result = SimpleHttpClient.get(config).requestJson(
                String.format("%s/%s/suggested", AppRoutes.MESSAGES.getUrl(), messageId),
                HttpMethod.GET,
                new HashMap<>() {{
                    put("user", user);
                }},
                null

        );

        JSONObject json = JSON.parseObject(result);

        if ("success".equals(json.getString("result"))) {
            return json.getJSONArray("data").toList(String.class);
        }

        return new ArrayList<>();
    }

    @Override
    public JSONObject history(String conversationId, String user, Integer limit, String firstId) {
        String result = SimpleHttpClient.get(config).requestJson(AppRoutes.MESSAGES, new HashMap<>() {{
            put("conversation_id", conversationId);
            put("user", user);
            put("limit", limit);
            put("first_id", firstId);
        }}, null);

        if (result == null) {
            return null;
        }

        return JSON.parseObject(result);
    }

    @Override
    public JSONObject conversations(String user, Integer limit, String sortBy, String lastId) {
        String result = SimpleHttpClient.get(config).requestJson(AppRoutes.CHAT_CONVERSATIONS, new HashMap<>() {{
            put("user", user);
            put("last_id", lastId);
            put("sort_by", sortBy);
            put("limit", limit);
        }}, null);

        if (result == null) {
            return null;
        }

        return JSON.parseObject(result);
    }

    @Override
    public Boolean deleteConversation(String conversationId, String user) {
        String result = SimpleHttpClient.get(config).requestJson(
                AppRoutes.CHAT_CONVERSATIONS.getUrl() + "/" + conversationId,
                HttpMethod.DELETE,
                null,
                new HashMap<>() {{
                    put("user", user);
                }}
        );

        JSONObject json = JSON.parseObject(result);
        return "success".equals(json.getString("result"));
    }

    @Override
    public JSONObject renameConversation(String conversationId, String user, String name) {
        return renameConversation(conversationId, user, name, false);
    }

    @Override
    public JSONObject renameConversation(String conversationId, String user, String name, boolean autoGenerate) {
        String result = SimpleHttpClient.get(config).requestJson(
                String.format("%s/%s/name", AppRoutes.CHAT_CONVERSATIONS.getUrl(), conversationId),
                HttpMethod.POST,
                null,
                new HashMap<>() {{
                    put("user", user);
                    put("name", name);
                    put("auto_generate", autoGenerate);
                }}
        );

        return JSON.parseObject(result);
    }

}
