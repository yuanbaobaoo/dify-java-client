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
public class ParamUpdateDataset {
    private String name;
    private DatasetConsts.DocType indexingTechnique;
    private DatasetConsts.Permission permission;
    private String embeddingModelProvider;
    private String embeddingModel;
    private String retrievalModel;
    private String partialMemberList;
}