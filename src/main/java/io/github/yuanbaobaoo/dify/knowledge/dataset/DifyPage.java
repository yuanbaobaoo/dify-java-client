package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class DifyPage<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int page;
    private int limit;
    private long total;
    private List<T> data;
    private Boolean hasMore;
}
