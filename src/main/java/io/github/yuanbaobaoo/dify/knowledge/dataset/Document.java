package io.github.yuanbaobaoo.dify.knowledge.dataset;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Document {


    /**
     * Document Api Util
     */
    public class Api {
        /**
         * 通过文本更新文档
         */
        public void updateByText() {

        }

        /**
         * 通过文件更新文档
         */
        public void updateByFile() {

        }

        /**
         * 删除文档
         */
        public void delete() {

        }

        /**
         * 获取文档嵌入状态（进度）
         * @param batch 上传文档的批次号
         */
        public void getIndexingStatus(List<String> batch) {

        }

        public void selectSegments() {

        }

        public void addSegments() {

        }

        public void delSegments() {

        }


        public void setSegments() {

        }

        public void selectUploadFiles() {

        }
    }
}
