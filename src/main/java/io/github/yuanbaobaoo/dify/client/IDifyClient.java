package io.github.yuanbaobaoo.dify.client;

import io.github.yuanbaobaoo.dify.types.DifyException;
import io.github.yuanbaobaoo.dify.types.DifyRoute;
import io.github.yuanbaobaoo.dify.client.types.DifyFileResult;
import io.github.yuanbaobaoo.dify.routes.HttpMethod;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * 基础Dify客户端
 */
public interface IDifyClient {
    /**
     * 获取应用基础信息
     * @return String
     */
    String getAppInfo() throws IOException, InterruptedException;

    /**
     * 获取应用参数信息
     * @return String
     */
    String getAppParameters() throws IOException, InterruptedException;

    /**
     * 获取应用元数据
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
     * 发送同步接口请求
     *
     * @param route  DifyRoute
     * @param params Body params
     * @return String
     */
    String sendBlocking(DifyRoute route, Map<String, Object> params) throws DifyException, IOException, InterruptedException;

    /**
     * 发送同步接口请求
     *
     * @param route  DifyRoute
     * @param query  Query params
     * @param params Body params
     * @return String
     */
    String sendBlocking(DifyRoute route, Map<String, Object> query, Map<String, Object> params)
            throws DifyException, IOException, InterruptedException
    ;

    /**
     * 发送流式接口请求
     *
     * @param route  DifyRoute
     * @param params Body params
     * @return CompletableFuture<Void>
     */
    CompletableFuture<Void> sendStreaming(DifyRoute route, Map<String, Object> params, Consumer<String> consumer);

    /**
     * 发送流式接口请求
     *
     * @param route  DifyRoute
     * @param query  Query params
     * @param params Body params
     * @return CompletableFuture<Void>
     */
    CompletableFuture<Void> sendStreaming(
            DifyRoute route,
            Map<String, Object> query,
            Map<String, Object> params,
            Consumer<String> consumer
    );

    /**
     * request by application/json content-type
     *
     * @param route  DifyRoute
     * @return String
     */
    String requestJson(DifyRoute route)
            throws DifyException, IOException, InterruptedException
    ;

    /**
     * request by application/json content-type
     *
     * @param route  DifyRoute
     * @param query  Query 查询参数
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
