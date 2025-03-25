package io.github.yuanbaobaoo.dify.types;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DifyFile {
    /**
     * 返回的文件Content-Type
     */
    private String type;

    /**
     * 文件默认后缀
     */
    private String suffix;

    /**
     * 文件内容
     */
    private byte[] data;
}
