package io.github.yuanbaobaoo.dify.web;

import com.alibaba.fastjson2.JSON;
import io.github.yuanbaobaoo.dify.routes.ConsoleRoutes;
import io.github.yuanbaobaoo.dify.utils.DifyHttpClient;
import io.github.yuanbaobaoo.dify.utils.DifyWebConfig;
import io.github.yuanbaobaoo.dify.web.types.DifyLoginResult;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class DifyAuthService {
    @Getter
    @Setter
    private static class LoginResult {
        private TokenList data;
        private String result;
    }

    @Getter
    @Setter
    private static class TokenList {
        private String accessToken;
        private String refreshToken;
    }

    /**
     * 登录
     * @param config DifyWebConfig
     */
    public static DifyLoginResult login(DifyWebConfig config) {
        DifyHttpClient client = DifyHttpClient.newHttpClient(config.getServer());

        String result = client.requestJson(ConsoleRoutes.LOGIN, null, new HashMap<>() {{
            put("email", config.getUserName());
            put("password", config.getPassword());
            put("language", "zh-Hans");
            put("remember_me", "zh-true");
        }});

        LoginResult loginResult = JSON.parse(result, LoginResult.class);

        return ;
    }
}
