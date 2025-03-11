package io.github.yuanbaobaoo.dify;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.client.IDifyBaseClient;
import io.github.yuanbaobaoo.dify.routes.DifyRoutes;
import io.github.yuanbaobaoo.dify.types.DifyException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class DifyClientTest {
    IDifyBaseClient client = IDifyBaseClient.newClient(
            "http://localhost:4000/v1",
            "app-"
    );

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
            String res = client.sendBlocking(DifyRoutes.CHAT_CHAT_MESSAGES, object);
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
            CompletableFuture<Void> future = client.sendStreaming(DifyRoutes.CHAT_CHAT_MESSAGES, object, (s) -> {
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
}
