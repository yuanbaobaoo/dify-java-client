package io.github.yuanbaobaoo.dify.dataset.entity;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class RetrieveResult {
    private JSONObject query;
    private List<Record> records;

    @Getter
    @Setter
    public static class Record {
        private Double score;
        private String tsnePosition;
        private Segment segment;
    }
}
