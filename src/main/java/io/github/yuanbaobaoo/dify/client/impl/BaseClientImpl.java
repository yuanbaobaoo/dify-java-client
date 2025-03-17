package io.github.yuanbaobaoo.dify.client.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.client.IDifyBaseClient;
import io.github.yuanbaobaoo.dify.client.types.DifyFileResult;
import io.github.yuanbaobaoo.dify.routes.DifyRoutes;
import io.github.yuanbaobaoo.dify.routes.HttpMethod;
import io.github.yuanbaobaoo.dify.types.DifyException;
import io.github.yuanbaobaoo.dify.types.DifyRoute;
import io.github.yuanbaobaoo.dify.types.ResponseMode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
public class BaseClientImpl extends DifyHttpClient implements IDifyBaseClient {
    /**
     * constructor
     *
     * @param server Dify Server URL
     * @param apiKey The App Api Key
     */
    public BaseClientImpl(String server, String apiKey) {
        super(server, apiKey);
    }

    @Override
    public String getAppInfo() throws DifyException, IOException, InterruptedException {
        return requestJson(DifyRoutes.INFO, null, null);
    }

    @Override
    public String getAppParameters() throws DifyException, IOException, InterruptedException {
        return requestJson(DifyRoutes.PARAMETERS, null, null);
    }

    @Override
    public String getAppMetaInfo() throws DifyException, IOException, InterruptedException {
        return requestJson(DifyRoutes.META_INFO, null, null);
    }

    @Override
    public DifyFileResult uploadFile(File file, String user) throws DifyException, IOException, InterruptedException {
        Map<String, Object> data = new HashMap<>();
        data.put("file", file);
        data.put("user", user);

        String result = requestMultipart(DifyRoutes.FILE_UPLOAD, null, data);
        return JSON.parseObject(result, DifyFileResult.class);
    }

    @Override
    public Boolean feedbacks(String messageId, String rating, String user, String content) {
        try {
            String result = requestJson(
                    String.format("%s/%s/feedbacks", DifyRoutes.MESSAGES.getUrl(), messageId),
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
    public String audioToText(File file, String user) throws DifyException, IOException, InterruptedException {
        Map<String, Object> data = new HashMap<>();
        data.put("file", file);
        data.put("user", user);

        JSONObject result = JSON.parseObject(requestMultipart(DifyRoutes.AUDIO_TO_TEXT, null, data));
        return result.getString("text");

    }

    @Override
    public String requestBlocking(DifyRoute route, Map<String, Object> query, Map<String, Object> params)
            throws DifyException, IOException, InterruptedException {
        // body请求对象中，强制覆盖 response_mode
        params.put("response_mode", ResponseMode.blocking);
        return requestJson(route, query, params);
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
        return requestJsonAsync(route, query, params, line -> {
            final String FLAG = "data:";

            if (line.startsWith(FLAG)) {
                consumer.accept(line.substring(FLAG.length()).trim());
            }
        });
    }

}
