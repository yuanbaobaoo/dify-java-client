package io.github.yuanbaobaoo.dify.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.client.DifyClientBuilder;
import io.github.yuanbaobaoo.dify.client.IDifyWorkFlowClient;
import io.github.yuanbaobaoo.dify.client.params.ParamMessage;
import io.github.yuanbaobaoo.dify.types.DifyException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WorkFlowClientTest {
    IDifyWorkFlowClient client = DifyClientBuilder.create()
            .apiKey("")
            .baseUrl("http://localhost:4000/v1")
            .buildWorkFlow();

    @Test
    public void blockTest() {
        try {
            JSONObject result = client.runBlocking(ParamMessage.builder()
                    .query("测试方法有哪些")
                    .user("abc-123")
                    .inputs(new HashMap<>() {{
                        put("name", "元宝宝");
                    }}).build());

            System.out.println("ok: " + result.toJSONString());
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
                put("name", "元宝宝");
            }}).build();

            CompletableFuture<Void> future = client.runStreaming(m, (r) -> {
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

}
