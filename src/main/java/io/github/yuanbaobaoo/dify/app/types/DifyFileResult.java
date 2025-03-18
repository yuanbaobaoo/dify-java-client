package io.github.yuanbaobaoo.dify.app.types;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DifyFileResult {
    /**
     * ID
     */
    private String id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件大小（byte）
     */
    private Long size;

    /**
     * 文件后缀
     */
    private String extension;

    /**
     * 文件 mime-type
     */
    private String mimeType;

    /**
     * 上传人 ID
     */
    private String createdBy;

    /**
     * 上传时间
     */
    private Long createdAt;
}
