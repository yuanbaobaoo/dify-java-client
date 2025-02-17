package io.github.yuanbaobaoo.dify.client.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.types.DifyException;
import lombok.extern.slf4j.Slf4j;
import io.github.yuanbaobaoo.dify.client.IDifyChatClient;
import io.github.yuanbaobaoo.dify.client.params.ParamMessage;
import io.github.yuanbaobaoo.dify.client.types.DifyChatEvent;
import io.github.yuanbaobaoo.dify.client.types.DifyChatResult;
import io.github.yuanbaobaoo.dify.routes.DifyRoutes;
import io.github.yuanbaobaoo.dify.routes.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
public class DifyChatClientImpl extends DifyBaseClientImpl implements IDifyChatClient {
    /**
     * constructor
     *
     * @param server Dify Server URL
     * @param apiKey The App Api Key
     */
    public DifyChatClientImpl(String server, String apiKey) {
        super(server, apiKey);
    }


    @Override
    public DifyChatResult sendMessages(ParamMessage message) {
        try {
            String result = sendBlocking(DifyRoutes.CHAT_CHAT_MESSAGES, message.toMap());
            return DifyChatResult.builder().event(DifyChatEvent.message).payload(JSON.parseObject(result)).build();
        } catch (DifyException e) {
            throw e;
        } catch (Exception e) {
            log.error("sendMessages", e);
            throw new DifyException("消息发送异常", 500);
        }
    }

    @Override
    public CompletableFuture<Void> sendMessagesAsync(ParamMessage message, Consumer<DifyChatResult> consumer) {
        return sendStreaming(DifyRoutes.CHAT_CHAT_MESSAGES, message.toMap(), (line) -> {
            JSONObject json = JSON.parseObject(line);
            consumer.accept(DifyChatResult.builder().event(json.getString("event")).payload(json).build());
        });
    }

    @Override
    public Boolean stopResponse(String taskId, String user) {
        try {
            String result = requestJson(
                    String.format("%s/%s/stop", DifyRoutes.CHAT_CHAT_MESSAGES.getUrl(), taskId),
                    HttpMethod.POST,
                    null,
                    new HashMap<>() {{
                        put("user", user);
                    }}
            );

            JSONObject json = JSON.parseObject(result);
            return "success".equals(json.getString("result"));
        } catch (DifyException e) {
            log.error("stopResponse: {}", e.getOriginal());
        } catch (Exception e) {
            log.error("stopResponse", e);
        }

        return false;
    }

    @Override
    public List<String> suggestedList(String messageId, String user) {
        try {
            String result = requestJson(
                    String.format("%s/%s/suggested", DifyRoutes.CHAT_MESSAGES.getUrl(), messageId),
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
        } catch (DifyException e) {
            log.error("suggestedList: {}", e.getOriginal());
        } catch (Exception e) {
            log.error("suggestedList", e);
        }

        return null;
    }

    @Override
    public JSONObject history(String conversationId, String user, Integer limit, String firstId) {
        try {
            String result = requestJson(DifyRoutes.CHAT_MESSAGES, new HashMap<>() {{
                put("conversation_id", conversationId);
                put("user", user);
                put("limit", limit);
                put("first_id", firstId);
            }}, null);

            if (result == null) {
                return null;
            }

            return JSON.parseObject(result);
        } catch (DifyException e) {
            throw e;
        } catch (Exception e) {
            log.error("history", e);
            throw new DifyException("获取会话历史消息异常", 500);
        }
    }

    @Override
    public JSONObject conversations(String user, Integer limit, String sortBy, String lastId) {
        try {
            String result = requestJson(DifyRoutes.CHAT_CONVERSATIONS, new HashMap<>() {{
                put("user", user);
                put("last_id", lastId);
                put("sort_by", sortBy);
                put("limit", limit);
            }}, null);

            if (result == null) {
                return null;
            }

            return JSON.parseObject(result);
        } catch (DifyException e) {
            throw e;
        } catch (Exception e) {
            log.error("conversations", e);
            throw new DifyException("获取会话列表异常", 500);
        }
    }

    @Override
    public Boolean deleteConversation(String conversationId, String user) {
        try {
            String result = requestJson(
                    DifyRoutes.CHAT_CONVERSATIONS.getUrl() + "/" + conversationId,
                    HttpMethod.DELETE,
                    null,
                    new HashMap<>() {{
                        put("user", user);
                    }}
            );

            JSONObject json = JSON.parseObject(result);
            return "success".equals(json.getString("result"));
        } catch (DifyException e) {
            log.error("deleteConversation: {}", e.getOriginal());
        } catch (Exception e) {
            log.error("deleteConversation", e);
        }

        return false;
    }

    @Override
    public JSONObject renameConversation(String conversationId, String user,  String name) {
        try {
            String result = requestJson(
                    String.format("%s/%s/name", DifyRoutes.CHAT_CONVERSATIONS.getUrl(), conversationId),
                    HttpMethod.POST,
                    null,
                    new HashMap<>() {{
                        put("user", user);
                        put("name", name);
                        put("auto_generate", false);
                    }}
            );

            return JSON.parseObject(result);
        } catch (DifyException e) {
            log.error("renameConversation: {}", e.getOriginal());
        } catch (Exception e) {
            log.error("renameConversation", e);
        }

        return null;
    }

}
