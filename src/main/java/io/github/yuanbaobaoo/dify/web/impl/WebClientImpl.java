package io.github.yuanbaobaoo.dify.web.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import io.github.yuanbaobaoo.dify.routes.ConsoleRoutes;
import io.github.yuanbaobaoo.dify.types.DifyPage;
import io.github.yuanbaobaoo.dify.types.ApiConfig;
import io.github.yuanbaobaoo.dify.SimpleHttpClient;
import io.github.yuanbaobaoo.dify.web.IWebConsoleClient;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class WebClientImpl implements IWebConsoleClient {
    private final ApiConfig config;

    /**
     * constructor
     * @param server Dify 服务地址
     * @param token Dify access token
     */
    public WebClientImpl(String server, String token) {
        config = ApiConfig.builder().server(server).apiKey(token).build();
    }

    @Override
    public String token() {
        return config.getApiKey();
    }

    @Override
    public DifyPage<JSONObject> queryApps(int page, int limit, String name) {
        String result = SimpleHttpClient.get(config).requestJson(ConsoleRoutes.APPS, new HashMap<>() {{
            put("page", page);
            put("limit", limit);
            put("name", name);
        }});

        return JSON.parseObject(result, new TypeReference<DifyPage<JSONObject>>() {});
    }

}
