package io.github.yuanbaobaoo.dify;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.types.DifyClientException;
import io.github.yuanbaobaoo.dify.types.DifyException;
import io.github.yuanbaobaoo.dify.types.DifyPage;
import io.github.yuanbaobaoo.dify.web.IWebConsoleClient;
import org.junit.jupiter.api.Test;

public class DifyWebClientTest {

    @Test
    public void testApps() {
        try {
            IWebConsoleClient client = connect();
            System.out.println(client.token());

            DifyPage<JSONObject> res = client.queryApps(1, 30, null);
            System.out.println("ok: " + JSON.toJSONString(res));
        } catch (DifyException | DifyClientException e) {
            System.out.println("error dify: " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * connect to dify server
     */
    private IWebConsoleClient connect() {
        return DifyClientBuilder.web(
                "http://127.0.0.1:8080",
                "user",
                "password"
        ).connect();
    }
}
