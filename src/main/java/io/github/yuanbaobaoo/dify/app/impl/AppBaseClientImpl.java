package io.github.yuanbaobaoo.dify.app.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.types.ApiConfig;
import io.github.yuanbaobaoo.dify.SimpleHttpClient;
import io.github.yuanbaobaoo.dify.app.IAppBaseClient;
import io.github.yuanbaobaoo.dify.app.types.DifyFileResult;
import io.github.yuanbaobaoo.dify.routes.AppRoutes;
import io.github.yuanbaobaoo.dify.types.AudioFile;
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
public class AppBaseClientImpl implements IAppBaseClient {
    protected final ApiConfig config;

    /**
     * constructor
     *
     * @param server Dify Server URL
     * @param apiKey The App Api Key
     */
    public AppBaseClientImpl(String server, String apiKey) {
        config = ApiConfig.builder().server(server).apiKey(apiKey).build();
    }

    @Override
    public SimpleHttpClient httpClient() {
        return SimpleHttpClient.get(config);
    }

    @Override
    public String getAppInfo()  {
        return SimpleHttpClient.get(config).requestJson(AppRoutes.INFO, null, null);
    }

    @Override
    public String getAppParameters() {
        return SimpleHttpClient.get(config).requestJson(AppRoutes.PARAMETERS, null, null);
    }

    @Override
    public String getAppMetaInfo() {
        return SimpleHttpClient.get(config).requestJson(AppRoutes.META_INFO, null, null);
    }

    @Override
    public DifyFileResult uploadFile(File file, String user) {
        Map<String, Object> data = new HashMap<>();
        data.put("file", file);
        data.put("user", user);

        String result = SimpleHttpClient.get(config).requestMultipart(AppRoutes.FILE_UPLOAD, null, data);
        return JSON.parseObject(result, DifyFileResult.class);
    }

    @Override
    public Boolean feedbacks(String messageId, String rating, String user, String content) {
        try {
            String result = SimpleHttpClient.get(config).requestJson(
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
                SimpleHttpClient.get(config).requestMultipart(AppRoutes.AUDIO_TO_TEXT, null, data)
        );

        return result.getString("text");
    }

    @Override
    public AudioFile textToAudioByMessage(String user, String messageId) {
        return textToAudio(user, null, messageId);
    }

    @Override
    public AudioFile textToAudio(String user, String text) {
        return textToAudio(user, text, null);
    }

    @Override
    public AudioFile textToAudio(String user, String text, String messageId) {
        Map<String, Object> data = new HashMap<>();
        data.put("message_id", messageId);
        data.put("text", text);
        data.put("user", user);

        return SimpleHttpClient.get(config).requestFile(AppRoutes.TEXT_TO_AUDIO, null, data);
    }

    @Override
    public String requestBlocking(DifyRoute route, Map<String, Object> query, Map<String, Object> params) {
        // body请求对象中，强制覆盖 response_mode
        params.put("response_mode", ResponseMode.blocking);
        return SimpleHttpClient.get(config).requestJson(route, query, params);
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
        return SimpleHttpClient.get(config).requestJsonAsync(route, query, params, line -> {
            final String FLAG = "data:";

            if (line.startsWith(FLAG)) {
                consumer.accept(line.substring(FLAG.length()).trim());
            }
        });
    }

}
