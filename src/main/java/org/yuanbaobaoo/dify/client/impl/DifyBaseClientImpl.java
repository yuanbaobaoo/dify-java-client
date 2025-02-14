package org.yuanbaobaoo.dify.client.impl;

import cn.hutool.core.net.url.UrlBuilder;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.yuanbaobaoo.dify.client.IDifyClient;
import org.yuanbaobaoo.dify.client.types.DifyFileResult;
import org.yuanbaobaoo.dify.routes.DifyRoutes;
import org.yuanbaobaoo.dify.routes.HttpMethod;
import org.yuanbaobaoo.dify.types.DifyException;
import org.yuanbaobaoo.dify.types.DifyRoute;
import org.yuanbaobaoo.dify.types.ResponseMode;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
public class DifyBaseClientImpl implements IDifyClient {
    private final String server;
    private final String apiKey;
    private final HttpClient httpClient;

    /**
     * constructor
     *
     * @param server Dify Server URL
     * @param apiKey The App Api Key
     */
    public DifyBaseClientImpl(String server, String apiKey) {
        this.apiKey = apiKey;
        this.server = server;
        this.httpClient = HttpClient.newHttpClient();
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
    public String sendBlocking(DifyRoute route, Map<String, Object> params) throws DifyException, IOException, InterruptedException {
        return sendBlocking(route, null, params);
    }

    @Override
    public String sendBlocking(DifyRoute route, Map<String, Object> query, Map<String, Object> params)
            throws DifyException, IOException, InterruptedException {
        // body请求对象中，强制覆盖 response_mode
        params.put("response_mode", ResponseMode.blocking);
        return requestJson(route, query, params);
    }

    @Override
    public CompletableFuture<Void> sendStreaming(DifyRoute route, Map<String, Object> params, Consumer<String> consumer) {
        return sendStreaming(route, null, params, consumer);
    }

    @Override
    public CompletableFuture<Void> sendStreaming(DifyRoute route, Map<String, Object> query, Map<String, Object> params, Consumer<String> consumer) {
        // body请求对象中，强制覆盖 response_mode
        params.put("response_mode", ResponseMode.streaming);
        HttpRequest.Builder builder = buildRequest(route.getUrl(), query);

        if (route.getMethod() == HttpMethod.GET) {
            builder.method(route.getMethod().name(), BodyPublishers.noBody());
        } else {
            builder.header("Content-Type", "application/json");
            builder.method(route.getMethod().name(), BodyPublishers.ofString(JSON.toJSONString(params)));
        }

        // 异步发送请求并处理响应
        return httpClient.sendAsync(builder.build(), BodyHandlers.ofLines()).thenAccept(response -> {
            response.body().forEach(line -> {
                if (response.statusCode() >= 400) {
                    throw new DifyException(line, response.statusCode());
                }

                final String FLAG = "data:";

                if (line.startsWith(FLAG)) {
                    consumer.accept(line.substring(FLAG.length()).trim());
                }
            });
        });
    }

    @Override
    public String requestJson(DifyRoute route, Map<String, Object> query, Object params)
            throws DifyException, IOException, InterruptedException {
        return requestJson(route.getUrl(), route.getMethod(), query, params);
    }

    @Override
    public String requestJson(String url, HttpMethod method, Map<String, Object> query, Object params)
            throws DifyException, IOException, InterruptedException {
        HttpRequest.Builder builder = buildRequest(url, query);

        if (params == null) {
            params = new HashMap<>();
        }

        if (method == HttpMethod.GET) {
            builder.method(method.name(), BodyPublishers.noBody());
        } else {
            builder.header("Content-Type", "application/json");
            builder.method(method.name(), BodyPublishers.ofString(JSON.toJSONString(params)));
        }

        HttpResponse<String> response = httpClient.send(builder.build(), BodyHandlers.ofString());

        // 所有非200响应都被视为dify平台的错误响应，统一返回异常对象
        if (response.statusCode() >= 400) {
            throw new DifyException(response.body(), response.statusCode());
        }

        return response.body();
    }

    @Override
    public String requestMultipart(DifyRoute route, Map<String, Object> query, Map<String, Object> params)
            throws DifyException, IOException, InterruptedException {
        return requestMultipart(route.getUrl(), route.getMethod(), query, params);
    }

    @Override
    public String requestMultipart(String url, HttpMethod method, Map<String, Object> query, Map<String, Object> params)
            throws DifyException, IOException, InterruptedException {
        HttpRequest.Builder builder = buildRequest(url, query);

        if (params == null) {
            params = new HashMap<>();
        }

        String boundary = "-----------" + UUID.randomUUID().toString().replaceAll("-", "");
        builder.header("Content-Type", "multipart/form-data; boundary=" + boundary);
        builder.method(method.name(), convertMapToMultipart(boundary, params));
        HttpResponse<String> response = httpClient.send(builder.build(), BodyHandlers.ofString());

        // 所有非200响应都被视为dify平台的错误响应，统一返回异常对象
        if (response.statusCode() >= 400) {
            throw new DifyException(response.body(), response.statusCode());
        }

        return response.body();
    }

    /**
     * build request
     *
     * @param url  api url
     * @param query  Query params
     */
    private HttpRequest.Builder buildRequest(String url, Map<String, Object> query) {
        HttpRequest.Builder builder = HttpRequest.newBuilder();

        UrlBuilder urlBuilder = UrlBuilder.ofHttp(this.server);
        urlBuilder.addPath(url);

        if (query != null) {
            query.forEach((key, value) -> {
                if (value != null && key != null) {
                    urlBuilder.addQuery(key, value.toString());
                }
            });
        }

        builder.uri(urlBuilder.toURI());
        builder.header("Authorization", "Bearer " + this.apiKey);

        return builder;
    }

    /**
     * 将 Map 转换为 multipart/form-data 格式的 BodyPublisher
     * 支持类型：String（文本）、Path/File（文件）
     */
    private HttpRequest.BodyPublisher convertMapToMultipart(String boundary, Map<String, Object> data) throws IOException {
        var parts = new ArrayList<HttpRequest.BodyPublisher>();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Path || value instanceof File) {
                // 文件字段（支持 Path 或 File 对象）
                Path filePath = (value instanceof Path) ? (Path) value : ((File) value).toPath();
                String fileName = filePath.getFileName().toString();
                byte[] fileBytes = Files.readAllBytes(filePath);
                String mimeType = Files.probeContentType(filePath);

                parts.add(BodyPublishers.ofString(
                        "--" + boundary + "\r\n" +
                                "Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + fileName + "\"\r\n" +
                                "Content-Type: " + (mimeType != null ? mimeType : "application/octet-stream") + "\r\n\r\n"
                ));

                parts.add(BodyPublishers.ofByteArray(fileBytes));
                parts.add(BodyPublishers.ofString("\r\n"));
            } else {
                // 其他字符都当作字符串
                parts.add(BodyPublishers.ofString(
                        "--" + boundary + "\r\n" +
                                "Content-Disposition: form-data; name=\"" + key + "\"\r\n" +
                                "Content-Type: text/plain\r\n\r\n" +
                                value + "\r\n"
                ));
            }
        }

        // 添加结束边界
        parts.add(BodyPublishers.ofString("--" + boundary + "--\r\n"));
        // 合并所有部分
        return BodyPublishers.concat(parts.toArray(new HttpRequest.BodyPublisher[0]));
    }

}
