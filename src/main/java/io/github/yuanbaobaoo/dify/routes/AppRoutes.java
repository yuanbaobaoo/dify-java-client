package io.github.yuanbaobaoo.dify.routes;

import io.github.yuanbaobaoo.dify.types.DifyRoute;
import io.github.yuanbaobaoo.dify.types.HttpMethod;

public class AppRoutes {
    /**
     * DESC: Get Application Basic Information
     * Type: Public
     */
    public static final DifyRoute INFO = new DifyRoute("/v1/info", HttpMethod.GET);

    /**
     * DESC: Get Application Parameters Information
     * TYPE: Public
     */
    public static final DifyRoute PARAMETERS = new DifyRoute("/v1/parameters", HttpMethod.GET);

    /**
     * DESC: Get Application Meta Information
     * Type: Public
     */
    public static final DifyRoute META_INFO = new DifyRoute("/v1/meta", HttpMethod.GET);

    /**
     * DESC: File Upload
     * Type: Public
     */
    public static final DifyRoute FILE_UPLOAD = new DifyRoute("/v1/files/upload", HttpMethod.POST);

    /**
     * Type: ChatFlow、Chat、Agent、Completion
     */
    public static final DifyRoute MESSAGES = new DifyRoute("/v1/messages", HttpMethod.GET);

    /**
     * Get | Delete Conversations
     * Type: ChatFlow、Chat、Agent
     */
    public static final DifyRoute CHAT_CONVERSATIONS = new DifyRoute("/v1/conversations", HttpMethod.GET);

    /**
     * Desc: Get Conversation History Messages
     * Type: ChatFlow、Chat、Agent
     */
    public static final DifyRoute CHAT_MESSAGES = new DifyRoute("/v1/chat-messages", HttpMethod.POST);

    /**
     * Desc: Audio to Text
     * Type: ChatFlow、Chat、Agent
     */
    public static final DifyRoute AUDIO_TO_TEXT = new DifyRoute("/v1/audio-to-text", HttpMethod.POST);

    /**
     * Text to Audio
     * Type: ChatFlow、Chat、Agent、Completion
     */
    public static final DifyRoute TEXT_TO_AUDIO = new DifyRoute("/v1/text-to-audio", HttpMethod.POST);

    /**
     * Desc: run workflow
     * Type: workflow
     */
    public static final DifyRoute WORKFLOW_RUN = new DifyRoute("/v1/workflows/run", HttpMethod.POST);

    /**
     * Type: workflow
     */
    public static final DifyRoute WORKFLOW_TASK = new DifyRoute("/v1/workflows/task", HttpMethod.POST);

    /**
     * Desc: Get Workflow Logs
     * Type: workflow
     */
    public static final DifyRoute WORKFLOW_LOGS = new DifyRoute("/v1/workflows/logs", HttpMethod.GET);

    /**
     * Desc: Get Completion Messages
     * Type: Completion
     */
    public static final DifyRoute COMPLETION_MESSAGES = new DifyRoute("/v1/completion-messages", HttpMethod.POST);

}
