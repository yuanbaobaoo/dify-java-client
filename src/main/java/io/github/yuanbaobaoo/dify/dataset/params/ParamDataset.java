package io.github.yuanbaobaoo.dify.dataset.params;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import io.github.yuanbaobaoo.dify.dataset.types.DatasetConsts;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class ParamDataset {
    private String name;
    private String description;
    private DatasetConsts.DocType indexingTechnique;
    private DatasetConsts.Permission permission;
    private DatasetConsts.Provider provider;
    private String externalKnowledgeApiId;
    private String externalKnowledgeId;
}
