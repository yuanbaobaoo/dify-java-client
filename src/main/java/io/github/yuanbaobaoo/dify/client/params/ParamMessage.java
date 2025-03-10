package io.github.yuanbaobaoo.dify.client.params;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class ParamMessage {
    /**
     * 用户输入/提问内容。
     */
    private String query;

    /**
     * 允许传入 App 定义的各变量值
     * 如果变量是文件类型，请指定一个包含以下 files 中所述键的对象
     */
    private Map<String, Object> inputs;

    /**
     * 用户标识
     */
    private String user;

    /**
     * 会话 ID
     */
    @JSONField(name = "conversation_id", alternateNames = {"conversationId"})
    private String conversationId;

    /**
     * 文件列表，适用于传入文件结合文本理解并回答问题，仅当模型支持 Vision 能力时可用
     * 具体请看官方文档
     */
    private List<Map<String, Object>> files;

    /**
     * 自动生成标题，默认 true。
     * 若设置为 false，则可通过调用会话重命名接口并设置 auto_generate 为 true 实现异步生成标题。
     */
    @JSONField(name = "auto_generate_name", alternateNames = {"autoGenerateName"})
    private Boolean autoGenerateName;

    /**
     * ToMap
     * @return Map<String, Object>
     */
    public Map<String, Object> toMap() {
        return new HashMap<>(){{
           put("query", query);
           put("inputs", inputs);
           put("user", user);
           put("conversation_id", conversationId);
           put("files", files);
           put("auto_generate_name", autoGenerateName == null || autoGenerateName);
        }};
    }

}
