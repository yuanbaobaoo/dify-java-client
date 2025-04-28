package io.github.yuanbaobaoo.dify.types;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiConfig {
    /**
     * Dify Server Address
     */
    private String server;

    /**
     * Api Key
     */
    private String apiKey;

    /**
     * refreshToken
     */
    private String refreshToken;
}
