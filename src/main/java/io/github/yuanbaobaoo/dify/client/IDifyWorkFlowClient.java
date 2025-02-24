package io.github.yuanbaobaoo.dify.client;

import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.client.params.ParamMessage;
import io.github.yuanbaobaoo.dify.client.types.DifyWorkFlowResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * 适用于工作流应用的客户端
 * @example workflow
 */
public interface IDifyWorkFlowClient extends IDifyClient {

    /**
     * 执行工作流（流式模式）
     * @param message ParamMessage
     * @param consumer Consumer<DifyWorkFlowResult>
     */
    CompletableFuture<Void> runStreaming(ParamMessage message, Consumer<DifyWorkFlowResult> consumer);

    /**
     * 执行工作流（阻塞模式）
     * @param message ParamMessage
     */
    JSONObject runBlocking(ParamMessage message);

    /**
     * 获取工作流执行情况
     * @param workFlowId 工作流ID
     */
    JSONObject getWorkFlowStatus(String workFlowId);

    /**
     * 停止工作流（仅限流式模式）
     * @param taskId 任务ID（流式返回chunk中获取）
     */
    Boolean stopWorkFlow(String taskId);

//    /**
//     * 上传文件
//     */
//    JSONObject uploadFile(String filePath, String user);
//
//    /**
//     * 获取工作流日志
//     * @param keyword 关键字
//     * @param status 执行状态（succeeded/failed/stopped)
//     * @param page 页码
//     * @param limit 每页数量
//     */
//    JSONObject getWorkFlowLog(String keyword, String status, Integer page, Integer limit);
//
//    /**
//     * 获取应用信息
//     */
//    JSONObject getWorkFlowInfo();
//
//    /**
//     * 获取应用参数
//     */
//    JSONObject getWorkFlowParameters();

}
