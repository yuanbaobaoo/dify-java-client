package io.github.yuanbaobaoo.dify.app;

import io.github.yuanbaobaoo.dify.SimpleHttpClient;
import io.github.yuanbaobaoo.dify.app.types.DifyFileResult;
import io.github.yuanbaobaoo.dify.types.AudioFile;
import io.github.yuanbaobaoo.dify.types.DifyRoute;

import java.io.File;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * 基础Dify客户端
 */
public interface IAppBaseClient {
    /**
     * 获取一个Simple http client 对象
     */
    SimpleHttpClient httpClient();

    /**
     * 获取应用基础信息
     *
     * @return String
     */
    String getAppInfo();

    /**
     * 获取应用参数信息
     *
     * @return String
     */
    String getAppParameters();

    /**
     * 获取应用元数据
     *
     * @return String
     */
    String getAppMetaInfo();

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @param user 用户标识
     * @return DifyFileResult
     */
    DifyFileResult uploadFile(File file, String user);

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
    String audioToText(File file, String user);

    /**
     * 文字转语音
     *
     * @param user      用户标识，由开发者定义规则，需保证用户标识在应用内唯一
     * @param messageId Dify 生成的文本消息 message-id
     */
    AudioFile textToAudioByMessage(String user, String messageId);

    /**
     * 文字转语音
     *
     * @param user 用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     * @param text 语音生成内容。
     */
    AudioFile textToAudio(String user, String text);

    /**
     * 文字转语音
     *
     * @param user      用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     * @param text      语音生成内容。如果没有传 message-id的话，则会使用这个字段的内容
     * @param messageId Dify 生成的文本消息，那么直接传递生成的message-id 即可，后台会通过 message_id 查找相应的内容直接合成语音信息。
     *                  如果同时传 message_id 和 text，优先使用 message_id。
     */
    AudioFile textToAudio(String user, String text, String messageId);

    /**
     * 发送同步接口请求
     *
     * @param route  DifyRoute
     * @param query  Query params
     * @param params Body params
     * @return String
     */
    String requestBlocking(DifyRoute route, Map<String, Object> query, Map<String, Object> params);

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

}
