package io.github.yuanbaobaoo.dify.knowledge.dataset;

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
public class Segment {
    private String id;
    private Integer position;
    private String documentId;
    private String content;
    private String answer;
    private Long wordCount;
    private Long tokens;
    private List<String> keywords;
    private String indexNodeId;
    private String indexNodeHash;
    private Long hitCount;
    private Boolean enabled;
    private Long disabledAt;
    private String disabledBy;
    private String status;
    private String createdBy;
    private Long createdAt;
    private Long indexingAt;
    private Long completedAt;
    private String error;
    private Long stopped_at;
    private Document document;
}
