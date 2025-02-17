package io.github.yuanbaobaoo.dify.types;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KnowledgeResult {
    /**
     * 从知识库查询的记录列表
     */
    private List<Record> records;

    /**
     * constructor
     */
    public KnowledgeResult() {

    }

    /**
     * constructor
     * @param data Record
     */
    public KnowledgeResult(Record data) {
        this.records = List.of(data);
    }

    /**
     * constructor
     * @param data List<Record>
     */
    public KnowledgeResult(List<Record> data) {
        this.records = data;
    }

    @Getter
    @Setter
    @Builder
    public static class Record {
        /**
         * 包含知识库中数据源的文本块
         */
        private String content;

        /**
         * 结果与查询的相关性分数，范围：0~1
         */
        private Double score;

        /**
         * 文档标题
         */
        private String title;

        /**
         * 包含数据源中文档的元数据属性及其值
         */
        private Object metadata;
    }
}
