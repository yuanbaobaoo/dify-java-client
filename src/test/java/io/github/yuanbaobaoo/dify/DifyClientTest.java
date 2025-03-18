package io.github.yuanbaobaoo.dify;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.app.IDifyBaseClient;
import io.github.yuanbaobaoo.dify.app.routes.AppRoutes;
import io.github.yuanbaobaoo.dify.types.DifyException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class DifyClientTest {
    IDifyBaseClient client = DifyClientBuilder.create().baseUrl("http://localhost:4000").apiKey("app-").build();

    @Test
    public void blockTest() {
        JSONObject object = JSON.parseObject("""
                {
                   "inputs": {
                                          "name": "元宝宝"
                                      },
                    "user": "abc-123",
                    "query": "测试方法有哪些"
                                
                }
""");

        try {
            String res = client.requestBlocking(AppRoutes.CHAT_MESSAGES, null, object);
            System.out.println("ok: " + JSON.parse(res).toString());
        } catch (DifyException e) {
            System.out.println("error dify: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void streamTest() {
        JSONObject object = JSON.parseObject("""
                {
                   "inputs": {
                                   "name":"元宝宝"
                                      },
                    "user": "abc-123",
                    "query": "测试方法有哪些"
                                
                }
""");

        try {
            CompletableFuture<Void> future = client.requestStreaming(AppRoutes.CHAT_MESSAGES, null, object, (s) -> {
                System.out.println("ok: " + JSON.parse(s).toString());
            });

            future.join();
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
    public void testFeed() {
        try {
            Boolean b = client.feedbacks("a915e09b-ec5e-49b6-9be2-8b8f0f806ac7", "like","abc-1234", "a");
            System.out.println(b);
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
