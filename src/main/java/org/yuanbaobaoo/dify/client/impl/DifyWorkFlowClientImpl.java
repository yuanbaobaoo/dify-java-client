package org.yuanbaobaoo.dify.client.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.types.DifyException;
import lombok.extern.slf4j.Slf4j;
import org.yuanbaobaoo.dify.client.IDifyWorkFlowClient;
import org.yuanbaobaoo.dify.client.params.ParamMessage;
import org.yuanbaobaoo.dify.client.types.DifyWorkFlowResult;
import org.yuanbaobaoo.dify.routes.DifyRoutes;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
public class DifyWorkFlowClientImpl extends DifyBaseClientImpl implements IDifyWorkFlowClient {

    /**
     * constructor
     *
     * @param server Dify Server URL
     * @param apiKey The App Api Key
     */
    public DifyWorkFlowClientImpl(String server, String apiKey) {
        super(server, apiKey);
    }


    @Override
    public CompletableFuture<Void> runStreaming(ParamMessage message, Consumer<DifyWorkFlowResult> consumer) {
        return sendStreaming(DifyRoutes.WORKFLOW_RUN, message.toMap(), (line) -> {
            JSONObject json = JSON.parseObject(line);
            consumer.accept(DifyWorkFlowResult.builder().event(json.getString("event")).payload(json).build());
        });
    }

    @Override
    public JSONObject runBlocking(ParamMessage message) {
        try {
            String result = sendBlocking(DifyRoutes.WORKFLOW_RUN, message.toMap());
            return JSON.parseObject(result);
        } catch (DifyException e) {
            throw e;
        } catch (Exception e) {
            log.error("sendMessages", e);
            throw new DifyException("消息发送异常", 500);
        }
    }

    @Override
    public JSONObject getWorkFlowStatus(String workFlowId) {
        return null;
    }

    @Override
    public Boolean stopWorkFlow(String taskId) {
        return null;
    }

}
