package io.github.yuanbaobaoo.dify.types;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeArgs {
    /**
     * 知识库唯一 ID
     */
    private String knowledge_id;

    /**
     * 用户的查询
     */
    private String query;

    /**
     * 知识检索参数
     */
    private Setting retrieval_setting;

    @Setter
    @Getter
    public static class Setting {
        /**
         * 检索结果的最大数量
         */
        private String top_k;

        /**
         * 结果与查询相关性的分数限制，范围：0~1
         */
        private Double score_threshold;
    }
}
