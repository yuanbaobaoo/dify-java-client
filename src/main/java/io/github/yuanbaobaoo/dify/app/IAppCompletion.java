package io.github.yuanbaobaoo.dify.app;

import io.github.yuanbaobaoo.dify.app.params.ParamMessage;
import io.github.yuanbaobaoo.dify.app.types.DifyChatResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * 适用于文本生成应用的客户端
 * ChatBot、Agent、ChatFlow
 */
public interface IAppCompletion extends IAppBaseClient {
    /**
     * 发送消息
     *
     * @param message ParamMessage
     */
    DifyChatResult sendMessages(ParamMessage message);

    /**
     * 发送消息（流式）
     *
     * @param message  ParamMessage
     * @param consumer Consumer<DifyChatResult>
     */
    CompletableFuture<Void> sendMessagesAsync(ParamMessage message, Consumer<DifyChatResult> consumer);

    /**
     * 停止响应（仅支持流式模式）
     *
     * @param taskId 任务 ID，可在流式返回 Chunk 中获取
     * @param user   用户标识，用于定义终端用户的身份，必须和发送消息接口传入 user 保持一致。
     */
    Boolean stopGenerate(String taskId, String user);
}
