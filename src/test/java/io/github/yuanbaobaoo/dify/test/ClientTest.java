package io.github.yuanbaobaoo.dify.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.client.DifyClientBuilder;
import io.github.yuanbaobaoo.dify.client.IDifyClient;
import io.github.yuanbaobaoo.dify.client.types.DifyFileResult;
import io.github.yuanbaobaoo.dify.routes.DifyRoutes;
import io.github.yuanbaobaoo.dify.routes.HttpMethod;
import io.github.yuanbaobaoo.dify.types.DifyException;
import io.github.yuanbaobaoo.dify.types.DifyRoute;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class ClientTest {
    IDifyClient client = DifyClientBuilder.create()
            .apiKey("app-")
            .baseUrl("http://localhost:4000/v1")
            .build();
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

    @Test
    public void uploadTest() {
        try {
            DifyFileResult result = client.uploadFile( new File("pom.xml"), "abc-123");
            System.out.println("ok: " + JSON.toJSONString(result));
        } catch (DifyException e) {
            System.out.println("dify error: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void publicApiTest() {
        try {
            DifyRoute route = DifyRoute.builder().url("/test").method(HttpMethod.GET).build();

            String a = client.getAppInfo();
            System.out.println(a);

            String a1 = client.getAppMetaInfo();
            System.out.println(a1);

            String a2 = client.getAppParameters();
            System.out.println(a2);

        } catch (DifyException e) {
            System.out.println("dify error: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
