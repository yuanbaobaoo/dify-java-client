package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class Document {
    private String id;
    private Integer position;
    private String dataSourceType;
    private JSONObject dataSourceInfo;
    private JSONObject dataSourceDetailDict;
    private String datasetProcessRuleId;
    private String name;
    private String createdFrom;
    private String createdBy;
    private Long createdAt;
    private String tokens;
    private String indexingStatus;
    private String error;
    private Boolean enabled;
    private Long disabledAt;
    private String disabledBy;
    private Boolean archived;
    private String displayStatus;
    private Long wordCount;
    private Long hitCount;
    private DatasetConsts.DocForm docForm;
}
