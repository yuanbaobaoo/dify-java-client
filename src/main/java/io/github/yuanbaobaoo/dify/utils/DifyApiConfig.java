package io.github.yuanbaobaoo.dify.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DifyApiConfig {
    /**
     * Dify Server Address
     */
    private String server;

    /**
     * Api Key
     */
    private String apiKey;
}
