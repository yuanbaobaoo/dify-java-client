package io.github.yuanbaobaoo.dify.knowledge.dataset;

public class DatasetConsts {
    /**
     * 索引方式
     */
    public enum IndexingTechnique {
        /**
         * 高质量：使用 embedding 模型进行嵌入，构建为向量数据库索引
         */
        high_quality,

        /**
         * 经济：使用 keyword table index 的倒排索引进行构建
         */
        economy,
    }

    /**
     * 索引内容的形式
     */
    public enum DocForm {
        /**
         * text 文档直接 embedding，经济模式默认为该模式
         */
        text_model,

        /**
         * parent-child 模式
         */
        hierarchical_model,

        /**
         * Q&A 模式：为分片文档生成 Q&A 对，然后对问题进行 embedding
         */
        qa_model,

    }

    /**
     * 权限
     */
    public enum Permission {
        /**
         * 仅自己
         */
        only_me,

        /**
         * 所有团队成员
         */
        all_team_members,

        /**
         * 部分团队成员
         */
        partial_members,
    }

    /**
     * Provider
     */
    public enum Provider {
        /**
         * 上传文件
         */
        vendor ,

        /**
         *  外部知识库
         */
        external ,
    }

    /**
     * Document type
     * book 图书 Book
     *       web_page 网页 Web page
     *       paper 学术论文/文章 Academic paper/article
     *       social_media_post 社交媒体帖子 Social media post
     *       wikipedia_entry 维基百科条目 Wikipedia entry
     *       personal_document 个人文档 Personal document
     *       business_document 商业文档 Business document
     *       im_chat_log 即时通讯记录 Chat log
     *       synced_from_notion Notion同步文档 Notion document
     *       synced_from_github GitHub同步文档 GitHub document
     *       others 其他文档类型 Other document types
     */
    public enum DocType {
        book,
        web_page,
        paper,
        social_media_post,
        wikipedia_entry,
        personal_document,
        business_document,
        im_chat_log,
        synced_from_notion,
        synced_from_github,
        others,
    }

}
