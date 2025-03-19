package io.github.yuanbaobaoo.dify.app.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.DifyConfig;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.app.IDifyBaseClient;
import io.github.yuanbaobaoo.dify.app.types.DifyFileResult;
import io.github.yuanbaobaoo.dify.routes.AppRoutes;
import io.github.yuanbaobaoo.dify.types.HttpMethod;
import io.github.yuanbaobaoo.dify.types.DifyException;
import io.github.yuanbaobaoo.dify.types.DifyRoute;
import io.github.yuanbaobaoo.dify.app.types.ResponseMode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
public class BaseClientImpl implements IDifyBaseClient {
    protected final DifyConfig config;

    /**
     * constructor
     *
     * @param server Dify Server URL
     * @param apiKey The App Api Key
     */
    public BaseClientImpl(String server, String apiKey) {
        config = DifyConfig.builder().server(server).apiKey(apiKey).build();
    }

    @Override
    public String getAppInfo()  {
        return DifyHttpClient.get(config).requestJson(AppRoutes.INFO, null, null);
    }

    @Override
    public String getAppParameters() {
        return DifyHttpClient.get(config).requestJson(AppRoutes.PARAMETERS, null, null);
    }

    @Override
    public String getAppMetaInfo() {
        return DifyHttpClient.get(config).requestJson(AppRoutes.META_INFO, null, null);
    }

    @Override
    public DifyFileResult uploadFile(File file, String user) {
        Map<String, Object> data = new HashMap<>();
        data.put("file", file);
        data.put("user", user);

        String result = DifyHttpClient.get(config).requestMultipart(AppRoutes.FILE_UPLOAD, null, data);
        return JSON.parseObject(result, DifyFileResult.class);
    }

    @Override
    public Boolean feedbacks(String messageId, String rating, String user, String content) {
        try {
            String result = DifyHttpClient.get(config).requestJson(
                    String.format("%s/%s/feedbacks", AppRoutes.MESSAGES.getUrl(), messageId),
                    HttpMethod.POST,
                    null,
                    new HashMap<>() {{
                        put("rating", rating);
                        put("user", user);
                        put("content", content);
                    }}
            );

            JSONObject json = JSON.parseObject(result);
            return "success".equals(json.getString("result"));
        } catch (DifyException e) {
            log.error("feedbacks: {}", e.getOriginal());
        } catch (Exception e) {
            log.error("feedbacks", e);
        }

        return false;
    }

    @Override
    public String audioToText(File file, String user) {
        Map<String, Object> data = new HashMap<>();
        data.put("file", file);
        data.put("user", user);

        JSONObject result = JSON.parseObject(
                DifyHttpClient.get(config).requestMultipart(AppRoutes.AUDIO_TO_TEXT, null, data)
        );

        return result.getString("text");
    }

    @Override
    public String requestBlocking(DifyRoute route, Map<String, Object> query, Map<String, Object> params) {
        // body请求对象中，强制覆盖 response_mode
        params.put("response_mode", ResponseMode.blocking);
        return DifyHttpClient.get(config).requestJson(route, query, params);
    }

    @Override
    public CompletableFuture<Void> requestStreaming(
            DifyRoute route,
            Map<String, Object> query,
            Map<String, Object> params,
            Consumer<String> consumer
    ) {
        // body请求对象中，强制覆盖 response_mode
        params.put("response_mode", ResponseMode.streaming);
        // 异步请求
        return DifyHttpClient.get(config).requestJsonAsync(route, query, params, line -> {
            final String FLAG = "data:";

            if (line.startsWith(FLAG)) {
                consumer.accept(line.substring(FLAG.length()).trim());
            }
        });
    }

}
