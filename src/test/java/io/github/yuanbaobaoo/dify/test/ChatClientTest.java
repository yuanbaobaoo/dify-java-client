package io.github.yuanbaobaoo.dify.test;

import com.alibaba.fastjson2.JSON;
import io.github.yuanbaobaoo.dify.client.DifyClientBuilder;
import io.github.yuanbaobaoo.dify.client.IDifyChatClient;
import io.github.yuanbaobaoo.dify.client.params.ParamMessage;
import io.github.yuanbaobaoo.dify.client.types.DifyChatResult;
import io.github.yuanbaobaoo.dify.types.DifyException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ChatClientTest {
    IDifyChatClient client = DifyClientBuilder.create()
            .apiKey("app-LhdosV7BICq72oQJBmjkv5U8")
            .baseUrl("http://192.168.101.1:4000/v1")
            .buildChat();

    @Test
    public void blockTest() {
        try {
            DifyChatResult result = client.sendMessages(ParamMessage.builder()
                    .query("测试方法有哪些")
                    .user("abc-123")
                    .inputs(new HashMap<>() {{
                put("name", "大袁");
            }}).build());

            System.out.println("ok: " + result.getPayload().toJSONString());
        } catch (DifyException e) {
            System.out.println("error dify: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void streamTest() {
        try {
            ParamMessage m = ParamMessage.builder().query("测试方法有哪些").user("abc-123").inputs(new HashMap<>() {{
                put("name", "大袁");
            }}).build();

            CompletableFuture<Void> future = client.sendMessagesAsync(m, (r) -> {
                System.out.println("ok: " + r.getPayload().toJSONString());
            });

            future.join();
        } catch (DifyException e) {
            System.out.println("error dify: " + e.toString());
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof DifyException) {
                System.out.println("error dify: " + cause);
            } else {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void jsonTest() {
        ParamMessage m = ParamMessage.builder().query("测试方法有哪些").user("abc-123").inputs(new HashMap<>() {{
            put("name", "大袁");
        }}).conversationId("123").build();

        ParamMessage m1 = JSON.parseObject(
                "{\"conversationId\":\"123\",\"inputs\":{\"name\":\"大袁\"},\"query\":\"测试方法有哪些\",\"user\":\"abc-123\"}",
                ParamMessage.class
        );

        Map<String, Object> h = new HashMap<>(){{
            put("conversation_id", null);
            put("user", 1);
            put("first_id", null);
            put("limit", null);
        }};

        System.out.println(JSON.toJSONString(h));
        System.out.println(JSON.toJSONString(m));
        System.out.println(JSON.toJSONString(m1));
    }

    @Test
    public void apiTest() {
//        // 历史记录
//        JSONObject result = client.history(
//                "0f24fe08-4567-448f-b8cb-592d40d83305",
//                "abc-123",
//                1,
//                "e9e9c1b0-53fd-46e8-ae83-6034d31884e1"
//        );
//
//        System.out.println(result.toJSONString());

//        // 会话列表
//        JSONObject result2 = client.conversations(
//                "abc-123",
//                1,
//                "created_at",
//                null
//        );
//
//        System.out.println(result2.toJSONString());
//
//        // 删除会话
//        System.out.println(client.deleteConversation(
//                "ff59f57c-d247-481f-aabc-86d012b332f3",
//                "abc-123"
//        ));

//        // 停止响应
//        System.out.println(client.stopResponse(
//                "ff59f57c-d247-481f-aabc-86d012b332f3",
//                "abc-123"
//        ));

        // 获取下一轮建议问题列表
        System.out.println(client.suggestedList(
                "ff59f57c-d247-481f-aabc-86d012b332f3",
                "abc-123"
        ));
    }

}
