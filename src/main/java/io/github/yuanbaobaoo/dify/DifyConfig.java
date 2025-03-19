package io.github.yuanbaobaoo.dify;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DifyConfig {
    /**
     * Dify Server Address
     */
    private String server;

    /**
     * Api Key
     */
    private String apiKey;
}
