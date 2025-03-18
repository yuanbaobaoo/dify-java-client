package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class RetrieveResult {
    private Double score;
    private String tsnePosition;
    private List<Segment> records;
}
