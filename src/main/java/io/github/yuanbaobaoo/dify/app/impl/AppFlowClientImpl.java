package io.github.yuanbaobaoo.dify.app.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.SimpleHttpClient;
import io.github.yuanbaobaoo.dify.app.IAppFlowClient;
import io.github.yuanbaobaoo.dify.app.params.ParamMessage;
import io.github.yuanbaobaoo.dify.app.types.DifyWorkFlowResult;
import io.github.yuanbaobaoo.dify.app.types.WorkflowStatus;
import io.github.yuanbaobaoo.dify.routes.AppRoutes;
import io.github.yuanbaobaoo.dify.types.HttpMethod;
import io.github.yuanbaobaoo.dify.types.DifyException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
public class AppFlowClientImpl extends AppBaseClientImpl implements IAppFlowClient {
    /**
     * constructor
     *
     * @param server Dify Server URL
     * @param apiKey The App Api Key
     */
    public AppFlowClientImpl(String server, String apiKey) {
        super(server, apiKey);
    }

    @Override
    public CompletableFuture<Void> runStreaming(ParamMessage message, Consumer<DifyWorkFlowResult> consumer) {
        return requestStreaming(AppRoutes.WORKFLOW_RUN, null, message.toMap(), (line) -> {
            JSONObject json = JSON.parseObject(line);
            consumer.accept(DifyWorkFlowResult.builder().event(json.getString("event")).payload(json).build());
        });
    }

    @Override
    public JSONObject runBlocking(ParamMessage message) {
        try {
            String result = requestBlocking(AppRoutes.WORKFLOW_RUN, null, message.toMap());
            return JSON.parseObject(result);
        } catch (DifyException e) {
            throw e;
        } catch (Exception e) {
            log.error("sendMessages", e);
            throw new DifyException("[client] 消息发送异常", 500);
        }
    }

    @Override
    public JSONObject getWorkFlowStatus(String workFlowId) {
        try {
            String result = SimpleHttpClient.get(config).requestJson(
                    AppRoutes.WORKFLOW_RUN.getUrl() + "/" + workFlowId,
                    HttpMethod.GET,
                    null,
                    null
            );

            return JSON.parseObject(result);
        } catch (DifyException e) {
            log.error("getWorkFlowStatus: {}", e.getOriginal());
        } catch (Exception e) {
            log.error("getWorkFlowStatus", e);
        }

        return null;
    }

    @Override
    public Boolean stopWorkFlow(String taskId, String user) {
        try {
            String result = SimpleHttpClient.get(config).requestJson(
                    String.format("%s/%s/stop", AppRoutes.WORKFLOW_TASK.getUrl(), taskId),
                    HttpMethod.POST,
                    null,
                    new HashMap<>() {{
                        put("user", user);
                    }}
            );

            JSONObject json = JSON.parseObject(result);
            return "success".equals(json.getString("result"));
        } catch (DifyException e) {
            log.error("stopWorkFlow: {}", e.getOriginal());
        } catch (Exception e) {
            log.error("stopWorkFlow", e);
        }

        return false;
    }

    @Override
    public JSONObject getWorkFlowLog(String keyword, WorkflowStatus status, Integer page, Integer limit) {
        try {
            String result = SimpleHttpClient.get(config).requestJson(AppRoutes.WORKFLOW_LOGS, new HashMap<>() {{
                put("keyword", keyword);
                put("status", status.name());
                put("page", page);
                put("limit", limit);
            }}, null);

            if (result == null) {
                return null;
            }

            return JSON.parseObject(result);
        } catch (DifyException e) {
            throw e;
        } catch (Exception e) {
            log.error("history", e);
            throw new DifyException("[client] 获取workflow日志异常", 500);
        }
    }

}
