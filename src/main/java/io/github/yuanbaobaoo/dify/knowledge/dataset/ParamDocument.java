package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class ParamDocument {
    /**
     * 文档名称
     */
    private String name;

    /**
     * 文档内容
     */
    private String text;

    /**
     * 文档类型（选填）
     */
    private DatasetConsts.DocType docType;

    /**
     * 文档元数据（如提供文档类型则必填）。字段因文档类型而异
     * 请查看 <a href="https://github.com/langgenius/dify/blob/main/api/services/dataset_service.py#L475">...</a>
     * 了解各文档类型所需字段的详细信息。
     * <p>
     * 针对图书 For book:
     * <p>
     * title 书名 Book title
     * language 图书语言 Book language
     * author 作者 Book author
     * publisher 出版社 Publisher name
     * publication_date 出版日期 Publication date
     * isbn ISBN号码 ISBN number
     * category 图书分类 Book category
     * <p>
     * 针对网页 For web_page:
     * <p>
     * title 页面标题 Page title
     * url 页面网址 Page URL
     * language 页面语言 Page language
     * publish_date 发布日期 Publish date
     * author/publisher 作者/发布者 Author or publisher
     * topic/keywords 主题/关键词 Topic or keywords
     * description 页面描述 Page description
     * <p>
     * 针对"其他"类型文档，接受任何有效的JSON对象
     */
    private Object docMetadata;

    /**
     * 索引方式
     */
    private DatasetConsts.IndexingTechnique indexingTechnique;

    /**
     * 索引内容的形式
     */
    private DatasetConsts.DocForm docForm;

    /**
     * 在 Q&A 模式下，指定文档的语言，例如：English、Chinese
     */
    private String docLanguage;

    /**
     * 处理规则
     */
    private ProcessRule processRule;

    /**
     * 检索模式
     * search_method (string) 检索方法
     * hybrid_search 混合检索
     * semantic_search 语义检索
     * full_text_search 全文检索
     * reranking_enable (bool) 是否开启rerank
     * reranking_model (object) Rerank 模型配置
     * reranking_provider_name (string) Rerank 模型的提供商
     * reranking_model_name (string) Rerank 模型的名称
     * top_k (int) 召回条数
     * score_threshold_enabled (bool)是否开启召回分数限制
     * score_threshold (float) 召回分数限制
     */
    private Object retrievalModel;

    /**
     * Embedding 模型名称
     */
    private String embeddingModel;

    /**
     * Embedding 模型供应商
     */
    private String embeddingModelProvider;
}
