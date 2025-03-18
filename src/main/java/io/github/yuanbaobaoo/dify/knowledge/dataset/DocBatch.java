package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class DocBatch {
    private String id;
    private String indexingStatus;
    private Long processingStartedAt;
    private Long parsingCompletedAt;
    private Long cleaningCompletedAt;
    private Long splittingCompletedAt;
    private Long completedAt;
    private Long pausedAt;
    private String error;
    private Long stoppedAt;
    private Integer completedSegments;
    private Integer totalSegments;
}
