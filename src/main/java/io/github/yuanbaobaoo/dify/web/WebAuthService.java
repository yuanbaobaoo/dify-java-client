package io.github.yuanbaobaoo.dify.web;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import io.github.yuanbaobaoo.dify.routes.ConsoleRoutes;
import io.github.yuanbaobaoo.dify.types.DifyClientException;
import io.github.yuanbaobaoo.dify.SimpleHttpClient;
import io.github.yuanbaobaoo.dify.types.WebConfig;
import io.github.yuanbaobaoo.dify.web.entity.TokenList;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class WebAuthService {
    @Getter
    @Setter
    private static class LoginResult {
        private TokenList data;
        private String result;
    }

    /**
     * 登录
     * @param config DifyWebConfig
     */
    public static TokenList login(WebConfig config) {
        SimpleHttpClient client = SimpleHttpClient.newHttpClient(config.getServer());

        String result = client.requestJson(ConsoleRoutes.LOGIN, null, new HashMap<>() {{
            put("email", config.getUserName());
            put("password", config.getPassword());
            put("language", "zh-Hans");
            put("remember_me", "zh-true");
        }});

        LoginResult loginResult = JSON.parseObject(result, LoginResult.class);

        if ("success".equals(loginResult.getResult())) {
            return loginResult.getData();
        }

        throw new DifyClientException("登录失败: " + result);
    }
}
