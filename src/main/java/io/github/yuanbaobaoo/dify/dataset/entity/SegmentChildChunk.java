package io.github.yuanbaobaoo.dify.dataset.entity;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class SegmentChildChunk {
    private String id;
    private String segmentId;
    private String content;
    private Long wordCount;
    private Long tokens;
    private String indexNodeId;
    private String indexNodeHash;
    private String status;
    private String createdBy;
    private Long createdAt;
    private Long indexingAt;
    private Long completedAt;
    private String error;
    private Long stopped_at;
}
