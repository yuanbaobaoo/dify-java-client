package io.github.yuanbaobaoo.dify.dataset.params;

import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import io.github.yuanbaobaoo.dify.dataset.types.DatasetConsts;
import io.github.yuanbaobaoo.dify.dataset.types.ProcessRule;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

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
     * To HashMap
     */
    public HashMap<String, Object> toMap() {
        return new HashMap<>() {{
            put("name", name);
            put("text", text);
            put("doc_type", docType);
            put("doc_metadata", docMetadata);
            put("indexing_technique", indexingTechnique);
            put("doc_form", docForm);
            put("doc_language", docLanguage);
            put("process_rule", processRule);
        }};
    }

}
