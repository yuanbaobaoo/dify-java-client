package org.yuanbaobaoo.dify.types;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DifyException extends RuntimeException {
    // error message {code, message, params, status}
    private String original;
    // error code | http status
    private Integer status;

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
        this.status = status;
        this.original = response;
    }

    @Override
    public String getMessage() {
        return original;
    }

    @Override
    public String toString() {
        return String.format("{original='%s', status=%s}", original, status);
    }

}
