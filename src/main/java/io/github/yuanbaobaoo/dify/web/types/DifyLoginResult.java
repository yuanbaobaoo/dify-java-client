package io.github.yuanbaobaoo.dify.web.types;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DifyLoginResult {
    private String token;
    private String userId;
    private String userName;
}
