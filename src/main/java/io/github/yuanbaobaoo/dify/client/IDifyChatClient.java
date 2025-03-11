package io.github.yuanbaobaoo.dify.client;

import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.client.params.ParamMessage;
import io.github.yuanbaobaoo.dify.client.types.DifyChatResult;
import io.github.yuanbaobaoo.dify.types.DifyException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * 适用于对话应用的客户端
 * ChatBot、Agent、ChatFlow
 */
public interface IDifyChatClient extends IDifyBaseClient {
    /**
     * new Dify Chat Client
     *
     * @param baseUrl Dify Server Base URL
     * @param apiKey  Api Key
     */
    static IDifyChatClient newClient(String baseUrl, String apiKey) {
        return DifyClientBuilder.create().baseUrl(baseUrl).apiKey(apiKey).buildChat();
    }

    /**
     * 发送对话消息（同步接收）
     *
     * @param message ParamMessage
     */
    DifyChatResult sendMessages(ParamMessage message);

    /**
     * 发送对话消息（流式接收）
     *
     * @param message  ParamMessage
     * @param consumer Consumer<DifyChatResult>
     */
    CompletableFuture<Void> sendMessagesAsync(ParamMessage message, Consumer<DifyChatResult> consumer);

    /**
     * 停止响应(仅支持流式模式)
     *
     * @param taskId 任务 ID，可在流式返回 Chunk 中获取
     * @param user   用户标识，用于定义终端用户的身份，必须和发送消息接口传入 user 保持一致。
     */
    Boolean stopResponse(String taskId, String user);

    /**
     * 获取下一轮建议问题列表
     *
     * @param messageId 消息ID
     * @param user      用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     */
    List<String> suggestedList(String messageId, String user);

    /**
     * 获取会话历史消息
     *
     * @param conversationId 会话ID
     * @param user           用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     * @param limit          一次请求返回多少条聊天记录，默认 20 条。
     * @param firstId        当前页第一条聊天记录的 ID，默认为空，表示获取第一页数据
     */
    JSONObject history(String conversationId, String user, Integer limit, String firstId);

    /**
     * 获取会话列表
     *
     * @param user   用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     * @param lastId （选填）当前页最后面一条记录的 ID，默认 null
     * @param sortBy （选填）排序字段，默认 -updated_at(按更新时间倒序排列)
     *               可选值：created_at, -created_at, updated_at, -updated_at
     *               字段前面的符号代表顺序或倒序，-代表倒序
     * @param limit  （选填）一次请求返回多少条记录，默认 20 条，最大 100 条，最小 1 条。
     */
    JSONObject conversations(String user, Integer limit, String sortBy, String lastId);

    /**
     * 删除会话
     *
     * @param conversationId 会话 ID
     * @param user           用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     */
    Boolean deleteConversation(String conversationId, String user);

    /**
     * 会话重命名
     *
     * @param conversationId 会话ID
     * @param user           用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     * @param name           会话名称
     */
    JSONObject renameConversation(String conversationId, String user, String name);

    /**
     * 语音转文字
     *
     * @param file 语音文件。 支持格式：['mp3', 'mp4', 'mpeg', 'mpga', 'm4a', 'wav', 'webm'] 文件
     *             大小限制：15MB
     * @param user 用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     */
    String audioToText(File file, String user) throws DifyException, IOException, InterruptedException;


}
