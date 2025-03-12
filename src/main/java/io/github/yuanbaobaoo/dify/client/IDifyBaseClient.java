package io.github.yuanbaobaoo.dify.client;

import io.github.yuanbaobaoo.dify.client.types.DifyFileResult;
import io.github.yuanbaobaoo.dify.routes.HttpMethod;
import io.github.yuanbaobaoo.dify.types.DifyException;
import io.github.yuanbaobaoo.dify.types.DifyRoute;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * 基础Dify客户端
 */
public interface IDifyBaseClient {
    /**
     * 获取应用基础信息
     *
     * @return String
     */
    String getAppInfo() throws IOException, InterruptedException;

    /**
     * 获取应用参数信息
     *
     * @return String
     */
    String getAppParameters() throws IOException, InterruptedException;

    /**
     * 获取应用元数据
     *
     * @return String
     */
    String getAppMetaInfo() throws IOException, InterruptedException;

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @param user 用户标识
     * @return DifyFileResult
     */
    DifyFileResult uploadFile(File file, String user) throws DifyException, IOException, InterruptedException;

    /**
     * 消息反馈
     *
     * @param messageId 消息ID
     * @param rating    点赞 like, 点踩 dislike, 撤销点赞 null
     * @param user      用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     * @param content   消息反馈的具体信息。
     */
    Boolean feedbacks(String messageId, String rating, String user, String content);

    /**
     * 语音转文字
     *
     * @param file 语音文件。 支持格式：['mp3', 'mp4', 'mpeg', 'mpga', 'm4a', 'wav', 'webm'] 文件
     *             大小限制：15MB
     * @param user 用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     */
    String audioToText(File file, String user) throws DifyException, IOException, InterruptedException;

    /**
     * 发送同步接口请求
     *
     * @param route  DifyRoute
     * @param query  Query params
     * @param params Body params
     * @return String
     */
    String requestBlocking(DifyRoute route, Map<String, Object> query, Map<String, Object> params)
            throws DifyException, IOException, InterruptedException
    ;

    /**
     * 发送流式接口请求
     *
     * @param route  DifyRoute
     * @param query  Query params
     * @param params Body params
     * @return CompletableFuture<Void>
     */
    CompletableFuture<Void> requestStreaming(
            DifyRoute route,
            Map<String, Object> query,
            Map<String, Object> params,
            Consumer<String> consumer
    );

    /**
     * request by application/json content-type
     *
     * @param route DifyRoute
     * @return String
     */
    String requestJson(DifyRoute route)
            throws DifyException, IOException, InterruptedException
    ;

    /**
     * request by application/json content-type
     *
     * @param route DifyRoute
     * @param query Query 查询参数
     * @return String
     */
    String requestJson(DifyRoute route, Map<String, Object> query)
            throws DifyException, IOException, InterruptedException
    ;

    /**
     * request by application/json content-type
     *
     * @param route  DifyRoute
     * @param query  Query 查询参数
     * @param params Body 参数
     * @return String
     */
    String requestJson(DifyRoute route, Map<String, Object> query, Object params)
            throws DifyException, IOException, InterruptedException
    ;

    /**
     * request by application/json content-type
     *
     * @param url    API URL
     * @param method HTTP请求方法
     * @param query  Query 查询参数
     * @param params Body 参数
     * @return String
     */
    String requestJson(String url, HttpMethod method, Map<String, Object> query, Object params)
            throws DifyException, IOException, InterruptedException
    ;

    /**
     * request by multipart/form-data content-type
     *
     * @param route  DifyRoute
     * @param query  Query 查询参数
     * @param params Body 参数，文件流需自行在params中传入
     * @return String
     */
    String requestMultipart(DifyRoute route, Map<String, Object> query, Map<String, Object> params)
            throws DifyException, IOException, InterruptedException
    ;

    /**
     * request by multipart/form-data content-type
     *
     * @param url    API URL
     * @param method HTTP请求方法
     * @param query  Query 查询参数
     * @param params Body 参数，文件流需自行在params中传入
     * @return String
     */
    String requestMultipart(String url, HttpMethod method, Map<String, Object> query, Map<String, Object> params)
            throws DifyException, IOException, InterruptedException
    ;

}
