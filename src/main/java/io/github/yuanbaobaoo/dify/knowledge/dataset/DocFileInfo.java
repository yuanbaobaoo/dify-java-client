package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class DocFileInfo {
    private String id;
    private String name;
    private Long size;
    private String extension;
    private String url;
    private String downloadUrl;
    private String mimeType;
    private String createdBy;
    private Long createdAt;
}
