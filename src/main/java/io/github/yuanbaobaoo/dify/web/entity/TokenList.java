package io.github.yuanbaobaoo.dify.web.entity;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class TokenList {
    private String accessToken;
    private String refreshToken;
}
