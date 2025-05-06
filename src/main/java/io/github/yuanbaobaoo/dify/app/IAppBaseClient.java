package io.github.yuanbaobaoo.dify.app;

import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.SimpleHttpClient;
import io.github.yuanbaobaoo.dify.app.types.DifyFileResult;
import io.github.yuanbaobaoo.dify.types.AudioFile;
import io.github.yuanbaobaoo.dify.types.DifyPage;
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
     * 语音转文字
     *
     * @param file 语音文件。 支持格式：['mp3', 'mp4', 'mpeg', 'mpga', 'm4a', 'wav', 'webm'] 文件
     *             大小限制：15MB
     * @param user 用户标识，由开发者定义规则，需保证用户标识在应用内唯一。
     * @param fileType 如果该参数不为空，那么file对象的ContentType将设置为该值。
     */
    String audioToText(File file, String user, String fileType);

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

    /**
     * 获取标注列表
     * @param page 页码
     * @param limit 每页数量
     */
    DifyPage<JSONObject> queryAnnotations(Integer page, Integer limit);

    /**
     * 创建标注
     * @param question 问题
     * @param answer 答案内容
     */
    JSONObject createAnnotation(String question, String answer);

    /**
     * 更新标注
     * @param annotationId 标注ID
     * @param question 问题
     * @param answer 答案内容
     */
    JSONObject updateAnnotation(String annotationId, String question, String answer);

    /**
     * 删除标注
     * @param annotationId 标注ID
     */
    Boolean deleteAnnotation(String annotationId);

    /**
     * 标注回复初始设置
     * @param enable 动作，true 代表 'enable'，false 代表 'disable'
     * @param embeddingModelProvider 指定的嵌入模型提供商, 必须先在系统内设定好接入的模型，对应的是provider字段
     * @param embeddingModel 指定的嵌入模型，对应的是model字段
     * @param scoreThreshold 相似度阈值，当相似度大于该阈值时，系统会自动回复，否则不回复
     */
    JSONObject setAnnotationReply(Boolean enable, String embeddingModelProvider, String embeddingModel, Double scoreThreshold);

    /**
     * 查询标注回复初始设置任务状态
     * @param enable 动作，true 代表 'enable'，false 代表 'disable'，必须和标注回复初始设置接口的动作一致
     * @param jobId 任务 ID，从标注回复初始设置接口返回的 job_id
     */
    JSONObject getAnnotationJobStatus(Boolean enable, String jobId);
}
