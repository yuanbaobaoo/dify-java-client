package io.github.yuanbaobaoo.dify.client.types;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DifyFileResult {
    private String id;
    private String name;
    private Long size;
    private String extension;
    private String mimeType;
    private String createdBy;
    private Long createdAt;
}
