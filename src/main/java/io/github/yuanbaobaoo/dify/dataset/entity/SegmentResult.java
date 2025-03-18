package io.github.yuanbaobaoo.dify.dataset.entity;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import io.github.yuanbaobaoo.dify.dataset.types.DatasetConsts;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class SegmentResult {
    private DatasetConsts.DocForm docForm;
    private List<Segment> data;
}
