package org.yuanbaobaoo.dify.client;

import org.yuanbaobaoo.dify.client.types.DifyFileResult;
import org.yuanbaobaoo.dify.routes.HttpMethod;
import org.yuanbaobaoo.dify.types.DifyException;
import org.yuanbaobaoo.dify.types.DifyRoute;

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
     */
    String getAppInfo() throws IOException, InterruptedException;

    /**
     * 获取应用参数信息
     */
    String getAppParameters() throws IOException, InterruptedException;

    /**
     * 获取应用元数据
     */
    String getAppMetaInfo() throws IOException, InterruptedException;

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @param user 用户标识
     */
    DifyFileResult uploadFile(File file, String user) throws DifyException, IOException, InterruptedException;

    /**
     * 发送同步接口请求
     *
     * @param route  DifyRoute
     * @param params Body params
     */
    String sendBlocking(DifyRoute route, Map<String, Object> params) throws DifyException, IOException, InterruptedException;

    /**
     * 发送同步接口请求
     *
     * @param route  DifyRoute
     * @param query  Query params
     * @param params Body params
     */
    String sendBlocking(DifyRoute route, Map<String, Object> query, Map<String, Object> params)
            throws DifyException, IOException, InterruptedException
    ;

    /**
     * 发送流式接口请求
     *
     * @param route  DifyRoute
     * @param params Body params
     */
    CompletableFuture<Void> sendStreaming(DifyRoute route, Map<String, Object> params, Consumer<String> consumer);

    /**
     * 发送流式接口请求
     *
     * @param route  DifyRoute
     * @param query  Query params
     * @param params Body params
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
     * @param query  Query 查询参数
     * @param params Body 参数
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
     */
    String requestMultipart(String url, HttpMethod method, Map<String, Object> query, Map<String, Object> params)
            throws DifyException, IOException, InterruptedException
    ;

}
