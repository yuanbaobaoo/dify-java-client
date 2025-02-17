package io.github.yuanbaobaoo.dify.client.types;

/**
 * dify chat-message 消息事件
 */
public class DifyChatEvent {
    public static final String message = "message";
    public static final String nodeRetry = "node_retry";
    public static final String messageFile = "message_file";
    public static final String messageEnd = "message_end";
    public static final String ttsMessage = "tts_message";
    public static final String ttsMessageEnd = "tts_message_end";
    public static final String messageReplace = "message_replace";
    public static final String workflowStarted = "workflow_started";
    public static final String nodeStarted = "node_started";
    public static final String nodeFinished = "node_finished";
    public static final String workflowFinished = "workflow_finished";
    public static final String error = "error";
}
