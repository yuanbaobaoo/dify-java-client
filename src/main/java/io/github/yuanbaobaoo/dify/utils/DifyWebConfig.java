package io.github.yuanbaobaoo.dify.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DifyWebConfig {
    /**
     * Dify Server Address
     */
    private String server;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;
}
