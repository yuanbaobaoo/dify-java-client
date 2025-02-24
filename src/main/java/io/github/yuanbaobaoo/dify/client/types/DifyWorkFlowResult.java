package io.github.yuanbaobaoo.dify.client.types;

import com.alibaba.fastjson2.JSONObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DifyWorkFlowResult {
    /**
     * 事件类型
     */
    private String event;

    /**
     * 返回的消息内容
     */
    private JSONObject payload;
}
