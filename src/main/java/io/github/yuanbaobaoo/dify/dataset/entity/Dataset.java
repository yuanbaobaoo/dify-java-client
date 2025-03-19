package io.github.yuanbaobaoo.dify.dataset.entity;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import io.github.yuanbaobaoo.dify.dataset.types.DatasetConsts;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class Dataset {
    private String id;
    private String name;
    private String description;
    private String provider;
    private String permission;
    private String dataSourceType;
    private DatasetConsts.IndexingTechnique indexingTechnique;
    private Integer appCount;
    private Long documentCount;
    private Long wordCount;
    private String createdBy;
    private Long createdAt;
    private String updatedBy;
    private Long updatedAt;
    private String embeddingModel;
    private String embeddingModelProvider;
    private String embeddingAvailable;
    private List<Object> tags;
    private Object docForm;
    private JSONObject retrievalModelDict;
    private JSONObject externalKnowledgeInfo;
    private JSONObject externalRetrievalModel;
}
