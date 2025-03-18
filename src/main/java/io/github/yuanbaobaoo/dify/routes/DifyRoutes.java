package io.github.yuanbaobaoo.dify.routes;

import io.github.yuanbaobaoo.dify.types.DifyRoute;

public class DifyRoutes {
    /**
     * Supported the dify api version
     */
    public static final String version = "/v1";

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
     * Type: Public
     */
    public static final DifyRoute META_INFO = new DifyRoute("/meta", HttpMethod.GET);

    /**
     * DESC: File Upload
     * Type: Public
     */
    public static final DifyRoute FILE_UPLOAD = new DifyRoute("/files/upload", HttpMethod.POST);

    /**
     * Type: ChatFlow、Chat、Agent、Completion
     */
    public static final DifyRoute MESSAGES = new DifyRoute("/messages", HttpMethod.GET);

    /**
     * Get | Delete Conversations
     * Type: ChatFlow、Chat、Agent
     */
    public static final DifyRoute CHAT_CONVERSATIONS = new DifyRoute("/conversations", HttpMethod.GET);

    /**
     * Desc: Get Conversation History Messages
     * Type: ChatFlow、Chat、Agent
     */
    public static final DifyRoute CHAT_MESSAGES = new DifyRoute("/chat-messages", HttpMethod.POST);

    /**
     * Desc: Audio to Text
     * Type: ChatFlow、Chat、Agent、Completion
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
     * Desc: Get Workflow Logs
     * Type: workflow
     */
    public static final DifyRoute WORKFLOW_LOGS = new DifyRoute("/workflows/logs", HttpMethod.GET);

    /**
     * Desc: Get Completion Messages
     * Type: Completion
     */
    public static final DifyRoute COMPLETION_MESSAGES = new DifyRoute("/completion-messages", HttpMethod.POST);

    /**
     * Desc: Datasets
     * Type: Dataset
     */
    public static final DifyRoute DATASETS = new DifyRoute("/datasets", HttpMethod.POST);

    public static final DifyRoute DATASETS_RETRIEVE = new DifyRoute(
            "/datasets/${datasetId}/retrieve",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_CREATE_DOC_TEXT = new DifyRoute(
            "/datasets/${datasetId}/document/create-by-text",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_CREATE_DOC_FILE = new DifyRoute(
            "/datasets/${datasetId}/document/create-by-file",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS = new DifyRoute(
            "/datasets/${datasetId}/documents",
            HttpMethod.GET
    );

    public static final DifyRoute DATASETS_INDEXING_STATUS = new DifyRoute(
            "/datasets/${datasetId}/documents/${batch}/indexing-status",
            HttpMethod.GET
    );

    public static final DifyRoute DATASETS_DOCS_UPDATE_TEXT = new DifyRoute(
            "/datasets/${datasetId}/documents/${documentId}/update-by-text",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS_UPDATE_FILE = new DifyRoute(
            "/datasets/${datasetId}/documents/${documentId}/update-by-file",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS_DELETE = new DifyRoute(
            "/datasets/${datasetId}/documents/${documentId}",
            HttpMethod.DELETE
    );

    public static final DifyRoute DATASETS_DOCS_FILE_INFO = new DifyRoute(
            "/datasets/${datasetId}/documents/${documentId}/upload-file",
            HttpMethod.GET
    );


    public static final DifyRoute DATASETS_DOCS_SEGMENTS = new DifyRoute(
            "/datasets/${datasetId}/documents/${documentId}/segments",
            HttpMethod.GET
    );

    public static final DifyRoute DATASETS_DOCS_SEGMENTS_ADD = new DifyRoute(
            DATASETS_DOCS_SEGMENTS.getUrl(),
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS_SEGMENTS_DEL = new DifyRoute(
            "/datasets/${datasetId}/documents/${documentId}/segments/${segmentId}",
            HttpMethod.DELETE
    );

    public static final DifyRoute DATASETS_DOCS_SEGMENTS_UPDATE = new DifyRoute(
            "/datasets/${datasetId}/documents/${documentId}/segments/${segmentId}",
            HttpMethod.POST
    );

}
