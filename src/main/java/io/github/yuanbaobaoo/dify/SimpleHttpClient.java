package io.github.yuanbaobaoo.dify;

import com.alibaba.fastjson2.JSON;
import io.github.yuanbaobaoo.dify.types.*;
import io.github.yuanbaobaoo.dify.types.ApiConfig;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class SimpleHttpClient {
    private final static Map<String, SimpleHttpClient> cache = new ConcurrentHashMap<>();

    private final String server;
    private final String apiKey;
    private final HttpClient httpClient;

    /**
     * constructor
     *
     * @param server Dify Server Address
     * @param apiKey Api Key
     */
    private SimpleHttpClient(String server, String apiKey) {
        this.apiKey = apiKey;
        this.server = server;
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * 从缓存池中获取一个client对象，如果没有则创建
     *
     * @param config DifyConfig
     */
    public static SimpleHttpClient get(ApiConfig config) {
        return get(config.getServer(), config.getApiKey());
    }

    /**
     * 从缓存池中获取一个client对象，如果没有则创建
     *
     * @param server Dify Server Address
     * @param apiKey Api Key
     */
    public static SimpleHttpClient get(String server, String apiKey) {
        return cache.computeIfAbsent(String.format("%s-%s", server, apiKey), id -> new SimpleHttpClient(server, apiKey));
    }

    /**
     * 实例化一个新对象
     *
     * @param server Dify Server Address
     */
    public static SimpleHttpClient newHttpClient(String server) {
        return new SimpleHttpClient(server, null);
    }

    /**
     * 实例化一个新对象
     *
     * @param server Dify Server Address
     * @param apiKey Api Key
     */
    public static SimpleHttpClient newHttpClient(String server, String apiKey) {
        return new SimpleHttpClient(server, apiKey);
    }

    /**
     * request by application/json content-type
     *
     * @param route DifyRoute
     * @return String
     */
    public String requestJson(DifyRoute route) {
        return requestJson(route.getUrl(), route.getMethod(), null, null);
    }

    /**
     * request by application/json content-type
     *
     * @param route DifyRoute
     * @param query Query 查询参数
     * @return String
     */
    public String requestJson(DifyRoute route, Map<String, Object> query) {
        return requestJson(route.getUrl(), route.getMethod(), query, null);
    }

    /**
     * request by application/json content-type
     *
     * @param route  DifyRoute
     * @param query  Query 查询参数
     * @param params Body 参数
     * @return String
     */
    public String requestJson(DifyRoute route, Map<String, Object> query, Object params) {
        return requestJson(route.getUrl(), route.getMethod(), query, params);
    }

    /**
     * request by application/json content-type
     *
     * @param url    API URL
     * @param method HTTP请求方法
     * @param query  Query 查询参数
     * @param params Body 参数
     * @return String
     */
    public String requestJson(String url, HttpMethod method, Map<String, Object> query, Object params) {
        try {
            HttpRequest.Builder builder = buildRequest(url, query);

            if (params == null) {
                params = new HashMap<>();
            }

            if (method == HttpMethod.GET) {
                builder.method(method.name(), HttpRequest.BodyPublishers.noBody());
            } else {
                builder.header("Content-Type", "application/json");
                builder.method(method.name(), HttpRequest.BodyPublishers.ofString(JSON.toJSONString(params)));
            }

            HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());

            // 所有非200响应都被视为dify平台的错误响应，统一返回异常对象
            if (response.statusCode() >= 400) {
                throw new DifyException(response.body(), response.statusCode());
            }

            return response.body();
        } catch (DifyException e) {
            throw e;
        } catch (Exception e) {
            throw new DifyClientException(e);
        }
    }

    /**
     * request by application/json content-type. return File bytes
     *
     * @param route  DifyRoute
     * @param query  query params
     * @param params body params
     */
    public AudioFile requestFile(DifyRoute route, Map<String, Object> query, Object params) {
        return requestFile(route.getUrl(), route.getMethod(), query, params);
    }

    /**
     * request by application/json content-type. return File bytes
     *
     * @param url    API URL
     * @param method HTTP METHOD
     * @param query  query map
     * @param params body map
     */
    public AudioFile requestFile(String url, HttpMethod method, Map<String, Object> query, Object params) {
        try {
            HttpRequest.Builder builder = buildRequest(url, query);

            if (params == null) {
                params = new HashMap<>();
            }

            if (method == HttpMethod.GET) {
                builder.method(method.name(), HttpRequest.BodyPublishers.noBody());
            } else {
                builder.header("Content-Type", "application/json");
                builder.method(method.name(), HttpRequest.BodyPublishers.ofString(JSON.toJSONString(params)));
            }

            HttpResponse<byte[]> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofByteArray());

            // 所有非200响应都被视为dify平台的错误响应，统一返回异常对象
            if (response.statusCode() >= 400) {
                throw new DifyException(new String(response.body()), response.statusCode());
            }

            String suffix = null;
            String contentType = null;
            Optional<String> contentTypeOpt = response.headers().firstValue("Content-Type");

            if (contentTypeOpt.isPresent()) {
                contentType = contentTypeOpt.get();
                suffix = contentType.replaceAll(".*/", "");
            }

            return AudioFile.builder().type(contentType).suffix(suffix).data(response.body()).build();
        } catch (DifyException e) {
            throw e;
        } catch (Exception e) {
            throw new DifyClientException(e);
        }
    }

    /**
     * async request by application/json content-type
     *
     * @param route    DifyRoute
     * @param query    Query params
     * @param params   Body params
     * @param consumer Consumer<String> consumer
     */
    public CompletableFuture<Void> requestJsonAsync(
            DifyRoute route,
            Map<String, Object> query,
            Map<String, Object> params,
            Consumer<String> consumer
    ) {
        HttpRequest.Builder builder = buildRequest(route.getUrl(), query);

        if (route.getMethod() == HttpMethod.GET) {
            builder.method(route.getMethod().name(), HttpRequest.BodyPublishers.noBody());
        } else {
            builder.header("Content-Type", "application/json");
            builder.method(route.getMethod().name(), HttpRequest.BodyPublishers.ofString(JSON.toJSONString(params)));
        }

        // 异步发送请求并处理响应
        return httpClient.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofLines()).thenAccept(response -> {
            response.body().forEach(line -> {
                if (response.statusCode() >= 400) {
                    throw new DifyException(line, response.statusCode());
                }

                consumer.accept(line);
            });
        });
    }

    /**
     * request by multipart/form-data content-type
     *
     * @param route  DifyRoute
     * @param query  Query 查询参数
     * @param params Body 参数，文件流需自行在params中传入
     * @return String
     */
    public String requestMultipart(DifyRoute route, Map<String, Object> query, Map<String, Object> params) {
        return requestMultipart(route.getUrl(), route.getMethod(), query, params, null);
    }

    /**
     * request by multipart/form-data content-type
     *
     * @param route  DifyRoute
     * @param query  Query 查询参数
     * @param params Body 参数，文件流需自行在params中传入
     * @param fileType 如果该参数不为空，那么params中所有File或者Path类型的对象的ContentType将设置为该值
     * @return String
     */
    public String requestMultipart(DifyRoute route, Map<String, Object> query, Map<String, Object> params, String fileType) {
        return requestMultipart(route.getUrl(), route.getMethod(), query, params, fileType);
    }

    /**
     * request by multipart/form-data content-type
     *
     * @param url    API URL
     * @param method HTTP请求方法
     * @param query  Query 查询参数
     * @param params Body 参数，文件流需自行在params中传入
     * @param fileType 如果该参数不为空，那么params中所有File或者Path类型的对象的ContentType将设置为该值
     * @return String
     */
    public String requestMultipart(String url, HttpMethod method, Map<String, Object> query, Map<String, Object> params, String fileType) {
        try {
            HttpRequest.Builder builder = buildRequest(url, query);

            if (params == null) {
                params = new HashMap<>();
            }

            String boundary = "-----------" + UUID.randomUUID().toString().replaceAll("-", "");
            builder.header("Content-Type", "multipart/form-data; boundary=" + boundary);
            builder.method(method.name(), convertMapToMultipart(boundary, params, fileType));
            HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());

            // 所有非200响应都被视为dify平台的错误响应，统一返回异常对象
            if (response.statusCode() >= 400) {
                throw new DifyException(response.body(), response.statusCode());
            }

            return response.body();
        } catch (DifyException e) {
            throw e;
        } catch (Exception e) {
            throw new DifyClientException(e);
        }
    }

    /**
     * build request
     *
     * @param url   api url
     * @param query Query params
     */
    public HttpRequest.Builder buildRequest(String url, Map<String, Object> query) {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        StringBuilder sb = new StringBuilder();

        if (this.server.endsWith("/") && url.startsWith("/")) {
            sb.append(this.server, 0, this.server.length() - 1);
        } else if (!this.server.endsWith("/") && !url.startsWith("/")) {
            sb.append(this.server).append('/');
        } else {
            sb.append(this.server);
        }

        sb.append(url);

        if (query != null && !query.isEmpty()) {
            sb.append("?");

            query.forEach((key, value) -> {
                if (key != null && value != null) {
                    sb.append(key).append("=").append(value).append("&");
                }
            });

            sb.setLength(sb.length() - 1);
        }

        builder.uri(URI.create(sb.toString()));

        if (this.apiKey != null) {
            builder.header("Authorization", "Bearer " + this.apiKey);
        }

        return builder;
    }

    /**
     * 将 Map 转换为 multipart/form-data 格式的 BodyPublisher
     * 支持类型：String（文本）、Path/File（文件）
     */
    private HttpRequest.BodyPublisher convertMapToMultipart(String boundary, Map<String, Object> data, String fileType) throws IOException {
        var parts = new ArrayList<HttpRequest.BodyPublisher>();
        Tika tika = new Tika();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (key == null || value == null) {
                continue;
            }

            if (value instanceof Path || value instanceof File) {
                // 文件字段（支持 Path 或 File 对象）
                Path filePath = (value instanceof Path) ? (Path) value : ((File) value).toPath();
                String fileName = filePath.getFileName().toString();
                byte[] fileBytes = Files.readAllBytes(filePath);

                parts.add(HttpRequest.BodyPublishers.ofString(
                        "--" + boundary + "\r\n" +
                                "Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + fileName + "\"\r\n" +
                                "Content-Type: " +  tika.detect(filePath) + "\r\n\r\n"
                ));

                parts.add(HttpRequest.BodyPublishers.ofByteArray(fileBytes));
                parts.add(HttpRequest.BodyPublishers.ofString("\r\n"));
            } else if (value instanceof String) {
                // 字符串
                parts.add(HttpRequest.BodyPublishers.ofString(
                        "--" + boundary + "\r\n" +
                                "Content-Disposition: form-data; name=\"" + key + "\"\r\n" +
                                "Content-Type: text/plain\r\n\r\n" +
                                value + "\r\n"
                ));
            } else {
                // 其他类型都使用JSON转换为字符串处理
                parts.add(HttpRequest.BodyPublishers.ofString(
                        "--" + boundary + "\r\n" +
                                "Content-Disposition: form-data; name=\"" + key + "\"\r\n" +
                                "Content-Type: text/plain\r\n\r\n" +
                                JSON.toJSONString(value) + "\r\n"
                ));
            }
        }

        // 添加结束边界
        parts.add(HttpRequest.BodyPublishers.ofString("--" + boundary + "--\r\n"));
        // 合并所有部分
        return HttpRequest.BodyPublishers.concat(parts.toArray(new HttpRequest.BodyPublisher[0]));
    }

}
