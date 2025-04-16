package io.github.yuanbaobaoo.dify.routes;

import io.github.yuanbaobaoo.dify.types.DifyRoute;
import io.github.yuanbaobaoo.dify.types.HttpMethod;

public class AppRoutes {
    public static final DifyRoute INFO = new DifyRoute(
            "/info",
            HttpMethod.GET
    );

    public static final DifyRoute PARAMETERS = new DifyRoute(
            "/parameters",
            HttpMethod.GET
    );

    public static final DifyRoute META_INFO = new DifyRoute(
            "/meta",
            HttpMethod.GET
    );

    public static final DifyRoute FILE_UPLOAD = new DifyRoute(
            "/files/upload",
            HttpMethod.POST
    );

    public static final DifyRoute MESSAGES = new DifyRoute(
            "/messages",
            HttpMethod.GET
    );

    public static final DifyRoute CHAT_CONVERSATIONS = new DifyRoute(
            "/conversations",
            HttpMethod.GET
    );

    public static final DifyRoute CHAT_MESSAGES = new DifyRoute(
            "/chat-messages",
            HttpMethod.POST
    );

    public static final DifyRoute AUDIO_TO_TEXT = new DifyRoute(
            "/audio-to-text",
            HttpMethod.POST
    );

    public static final DifyRoute TEXT_TO_AUDIO = new DifyRoute(
            "/text-to-audio",
            HttpMethod.POST
    );

    public static final DifyRoute WORKFLOW_RUN = new DifyRoute(
            "/workflows/run",
            HttpMethod.POST
    );

    public static final DifyRoute WORKFLOW_TASK = new DifyRoute(
            "/workflows/task",
            HttpMethod.POST
    );

    public static final DifyRoute WORKFLOW_LOGS = new DifyRoute(
            "/workflows/logs",
            HttpMethod.GET
    );

    public static final DifyRoute COMPLETION_MESSAGES = new DifyRoute(
            "/completion-messages",
            HttpMethod.POST
    );

    public static final DifyRoute ANNOTATION_GET = new DifyRoute(
            "/apps/annotations",
            HttpMethod.GET
    );

    public static final DifyRoute ANNOTATION_ADD = new DifyRoute(
            "/apps/annotations",
            HttpMethod.POST
    );

    public static final DifyRoute ANNOTATION_SET = new DifyRoute(
            "/apps/annotations/${annotationId}",
            HttpMethod.PUT
    );

    public static final DifyRoute ANNOTATION_DEL = new DifyRoute(
            "/apps/annotations/${annotationId}",
            HttpMethod.DELETE
    );

    public static final DifyRoute ANNOTATION_REPLY_SET = new DifyRoute(
            "/apps/annotation-reply/${action}",
            HttpMethod.POST
    );

    public static final DifyRoute ANNOTATION_REPLY_GET = new DifyRoute(
            "/apps/annotation-reply/${action}/status/${jobId}",
            HttpMethod.GET
    );
}
