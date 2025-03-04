package io.github.yuanbaobaoo.dify.routes;

import io.github.yuanbaobaoo.dify.types.DifyRoute;

public class DifyRoutes {
    /**
     * DESC: Get Application Basic Information
     * Type: Public
     */
    public static final DifyRoute INFO = new DifyRoute("/info", HttpMethod.GET);

    /**
     * DESC: Get Application Parameters Information
     * TYPE: Public
     */
    public static final DifyRoute PARAMETERS = new DifyRoute("/parameters", HttpMethod.GET);

    /**
     * DESC: Get Application Meta Information
     */
    public static final DifyRoute META_INFO = new DifyRoute("/meta", HttpMethod.GET);

    /**
     * DESC: File Upload
     * Type: Public
     */
    public static final DifyRoute FILE_UPLOAD = new DifyRoute("/files/upload", HttpMethod.POST);

    /**
     * Desc: Get Conversation History Messages
     * Type: ChatFlow、Chat、Agent
     */
    public static final DifyRoute CHAT_MESSAGES = new DifyRoute("/messages", HttpMethod.GET);

    /**
     * Get | Delete Conversations
     * Type: ChatFlow、Chat、Agent
     */
    public static final DifyRoute CHAT_CONVERSATIONS = new DifyRoute("/conversations", HttpMethod.GET);

    /**
     * Desc: Get Conversation History Messages
     * Type: ChatFlow、Chat、Agent
     */
    public static final DifyRoute CHAT_CHAT_MESSAGES = new DifyRoute("/chat-messages", HttpMethod.POST);

    /**
     * Desc: Audio to Text
     * Type: ChatFlow、Chat、Agent
     */
    public static final DifyRoute AUDIO_TO_TEXT = new DifyRoute("/audio-to-text", HttpMethod.POST);

    /**
     * Desc: run workflow
     * Type: workflow
     */
    public static final DifyRoute WORKFLOW_RUN = new DifyRoute("/workflows/run", HttpMethod.POST);

    /**
     * Type: workflow
     */
    public static final DifyRoute WORKFLOW_TASK = new DifyRoute("/workflows/task", HttpMethod.POST);

    /**
     * Type: workflow
     */
    public static final DifyRoute WORKFLOW_LOGS = new DifyRoute("/workflows/logs", HttpMethod.GET);
}
