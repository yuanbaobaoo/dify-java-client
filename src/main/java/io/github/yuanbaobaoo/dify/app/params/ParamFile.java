package io.github.yuanbaobaoo.dify.app.params;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParamFile {
    /**
     * 文件类型
     */
    private FileType type;

    /**
     * 传递方式
     */
    @JSONField(name = "transfer_method", alternateNames = {"transferMethod"})
    private TransferMethod transferMethod;

    /**
     * 图片地址（仅当传递方式为 remote_url 时）
     */
    private String url;

    /**
     * 上传文件ID（仅当传递方式为 local_file 时）
     */
    @JSONField(name = "upload_file_id", alternateNames = {"uploadFileId"})
    private String uploadFileId;

    /**
     * document 具体类型包含：'TXT', 'MD', 'MARKDOWN', 'PDF', 'HTML', 'XLSX', 'XLS', 'DOCX', 'CSV', 'EML', 'MSG', 'PPTX', 'PPT', 'XML', 'EPUB'
     * image 具体类型包含：'JPG', 'JPEG', 'PNG', 'GIF', 'WEBP', 'SVG'
     * audio 具体类型包含：'MP3', 'M4A', 'WAV', 'WEBM', 'AMR'
     * video 具体类型包含：'MP4', 'MOV', 'MPEG', 'MPGA'
     * custom 具体类型包含：其他文件类型
     */
    public enum FileType {
        document,
        image,
        audio,
        video,
        custom
    }

    /**
     * 传递方式
     */
    public enum TransferMethod {
        /**
         * 图片地址
         */
        remote_url,

        /**
         * 上传文件
         */
        local_file,
    }
}
