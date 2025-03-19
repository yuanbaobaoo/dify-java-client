package io.github.yuanbaobaoo.dify.types;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Dify exception type
 */
public class DifyException extends RuntimeException {
    /**
     * original dify response
     */
    @Getter
    private String original;

    /**
     * response status | http status
     */
    private Integer status;

    /**
     * dify response -> code
     */
    @Getter
    private String code;

    /**
     * dify response -> message
     */
    private String message;

    /**
     * dify response -> params
     */
    @Getter
    private String params;

    /**
     * constructor
     */
    public DifyException() {
        super();
    }

    /**
     * constructor
     * @param response String
     * @param status http status
     */
    public DifyException(String response, Integer status) {
        try {
            JSONObject object = JSON.parseObject(response);

            this.status = object.getInteger("status");
            this.code = object.getString("code");
            this.message = object.getString("message");
            this.params = object.getString("params");
        } catch (Exception e) {
            this.status = status;
            this.message = response;
        }

        this.original = response;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("code", code);
        map.put("message", message);
        map.put("params", params);
        return JSON.toJSONString(map);
    }

}
