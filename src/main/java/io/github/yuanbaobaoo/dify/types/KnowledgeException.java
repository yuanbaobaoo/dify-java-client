package io.github.yuanbaobaoo.dify.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeException extends RuntimeException {
    /**
     * 错误代码
     */
    private Integer error_code;

    /**
     * 错误信息
     */
    private String error_message;

    /**
     * toObject
     */
    public Object toObject() {
        Map<String, Object> obj = new HashMap<>();
        obj.put("error_code", error_code);
        obj.put("error_message", error_message);

        return obj;
    }

    @Override
    public String toString() {
        return "{" +
                "error_code: " + error_code +
                ", error_message: '" + error_message + '\'' +
                '}';
    }
}
