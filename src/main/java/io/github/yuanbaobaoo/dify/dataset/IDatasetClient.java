package io.github.yuanbaobaoo.dify.dataset;

import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.SimpleHttpClient;
import io.github.yuanbaobaoo.dify.dataset.entity.*;
import io.github.yuanbaobaoo.dify.dataset.heros.DatasetHero;
import io.github.yuanbaobaoo.dify.dataset.heros.DocumentHero;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDataset;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDocument;
import io.github.yuanbaobaoo.dify.dataset.params.ParamUpdateDataset;
import io.github.yuanbaobaoo.dify.dataset.types.*;
import io.github.yuanbaobaoo.dify.types.DifyPage;

import java.io.File;
import java.util.List;

public interface IDatasetClient {
    /**
     * 获取一个SimpleHttpClient对象
     */
    SimpleHttpClient httpClient();

    /**
     * 快捷构建一个Dataset对象
     * @param datasetId 知识库ID
     */
    DatasetHero ofDataset(String datasetId);

    /**
     * 快捷创建一个Document对象
     * @param documentId 文档ID
     */
    DocumentHero ofDocument(String datasetId, String documentId);

    /**
     * 获取嵌入模型列表
     */
    List<JSONObject> selectTextEmbedding();

    /**
     * 查询知识库列表
     * @param page 页码
     * @param limit 返回条数，默认 20，范围 1-100
     */
    DifyPage<Dataset> list(int page, int limit);

    /**
     * Create an Empty Knowledge Base. 创建空知识库
     * @param data ParamDataset
     */
    DatasetHero create(ParamDataset data);

    /**
     * 查看知识库详情
     * @param datasetId 知识库ID
     */
    DatasetHero get(String datasetId);

    /**
     * 修改知识库详情
     * @param datasetId 知识库ID
     * @param data ParamUpdateDataset
     */
    DatasetHero update(String datasetId, ParamUpdateDataset data);

    /**
     * 删除知识库
     * @param datasetId 知识库ID
     */
    void delete(String datasetId);

    /**
     * 通过文本创建文档
     * @param datasetId 知识库ID
     * @param doc ParamDocument
     */
    DocumentResult insertDocByText(String datasetId, ParamDocument doc) ;

    /**
     * 通过文本创建文档
     * @param datasetId 知识库ID
     * @param doc ParamDocument
     * @param retrievalModel RetrievalModel
     * @param embeddingModel Embedding 模型名称
     * @param embeddingModelProvider Embedding 模型供应商
     */
    DocumentResult insertDocByText(
            String datasetId,
            ParamDocument doc,
            RetrievalModel retrievalModel,
            String embeddingModel,
            String embeddingModelProvider
    );

    /**
     * 通过文件创建文档
     * @param datasetId 知识库ID
     * @param file File
     * @param data ParamDocument
     */
    DocumentResult insertDocByFile(String datasetId, File file, ParamDocument data) ;

    /**
     * 通过文件创建文档
     * @param datasetId 知识库ID
     * @param file File
     * @param data ParamDocument
     * @param retrievalModel retrievalModel
     * @param embeddingModel Embedding 模型名称
     * @param embeddingModelProvider Embedding 模型供应商
     */
    DocumentResult insertDocByFile(
            String datasetId,
            File file,
            ParamDocument data,
            RetrievalModel retrievalModel,
            String embeddingModel,
            String embeddingModelProvider
    );

    /**
     * 获取文档嵌入状态（进度）
     * @param datasetId 知识库ID
     * @param batch 上传文档的批次号
     */
    List<BatchStatus> queryBatchStatus(String datasetId, String batch);

    /**
     * 获取知识库文档列表
     * @param datasetId 知识库ID
     */
    DifyPage<Document> documents(String datasetId) ;

    /**
     * 获取知识库文档列表
     * @param datasetId 知识库ID
     * @param page 页码
     * @param limit 返回条数
     * @param keyword 搜索关键词，可选，目前仅搜索文档名称
     */
    DifyPage<Document> documents(String datasetId, Integer page, Integer limit, String keyword) ;

    /**
     * 检索知识库
     * @param datasetId 知识库ID
     * @param query 检索关键词
     * @param retrievalModel RetrievalModel
     */
    RetrieveResult retrieve(String datasetId, String query, RetrievalModel retrievalModel) ;


    /**
     * 通过文本更新文档
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param data ParamDocument
     */
    DocumentResult updateDocByText(String datasetId, String documentId, ParamDocument data);

    /**
     * 通过文件更新文档
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param file File
     * @param rule ProcessRule
     */
    DocumentResult updateDocByFile(String datasetId, String documentId, File file, ProcessRule rule);

    /**
     * 通过文件更新文档
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param name 文档名称
     * @param file 需要上传的文件
     * @param rule 处理规则
     */
    DocumentResult updateDocByFile(String datasetId, String documentId, String name, File file, ProcessRule rule);

    /**
     * 删除文档
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     */
    Boolean deleteDocument(String datasetId, String documentId);

    /**
     * 获取文档文件信息
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     */
    DocFileInfo queryFileInfo(String datasetId, String documentId);

    /**
     * 获取文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     */
    SegmentResult querySegments(String datasetId, String documentId);

    /**
     * 获取文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param keyword 关键词
     */
    SegmentResult querySegments(String datasetId, String documentId, String keyword);

    /**
     * 获取文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param keyword 关键词
     * @param status 搜索状态
     */
    SegmentResult querySegments(String datasetId, String documentId, String keyword, String status);

    /**
     * 插入文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segments  List<Segment>
     *      content  文本内容/问题内容
     *      keywords 关键字，非必填
     *      answer   答案内容，非必填，如果知识库的模式为 Q&A 模式则传值
     */
    SegmentResult insertSegment(String datasetId, String documentId, List<Segment> segments);

    /**
     * 删除文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 分段ID
     */
    Boolean deleteSegment(String datasetId, String documentId, String segmentId);

    /**
     * 更新文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 分段ID
     * @param content  文本内容/问题内容
     * @param regenerate 是否重新生成向量
     */
    SegmentResult updateSegment(String datasetId, String documentId, String segmentId, String content, boolean regenerate);

    /**
     * 更新文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segment Segment
     * @param regenerate 是否重新生成子分段
     */
    SegmentResult updateSegment(String datasetId, String documentId, Segment segment, boolean regenerate);

    /**
     * 新增文档子分段
     * @param datasetId 知识库 ID
     * @param documentId 文档 ID
     * @param segmentId 分段 ID
     * @param content 子分段内容
     */
    SegmentChildChunk insertSegmentChildChunks(String datasetId, String documentId, String segmentId, String content);

    /**
     * 查询文档子分段
     * @param datasetId 知识库 ID
     * @param documentId 文档 ID
     * @param segmentId 分段 ID
     */
    DifyPage<SegmentChildChunk> querySegmentChildChunks(
            String datasetId,
            String documentId,
            String segmentId,
            Integer page,
            Integer limit
    );

    /**
     * 查询文档子分段
     * @param datasetId 知识库 ID
     * @param documentId 文档 ID
     * @param segmentId 分段 ID
     * @param page 页码（选填，默认1）
     * @param limit 每页数量（选填，默认20，最大100）
     * @param keyword 搜索关键词（选填）
     */
    DifyPage<SegmentChildChunk> querySegmentChildChunks(
            String datasetId,
            String documentId,
            String segmentId,
            Integer page,
            Integer limit,
            String keyword
    );

    /**
     * TODO 删除文档子分段
     * @param datasetId 知识库 ID
     * @param documentId 文档 ID
     * @param segmentId 分段 ID
     * @param childChunkId 子分段 ID
     */
    Boolean deleteSegmentChildChunks(String datasetId, String documentId, String segmentId, String childChunkId);

    /**
     * TODO 更新文档子分段
     * @param datasetId 知识库 ID
     * @param documentId 文档 ID
     * @param segmentId 分段 ID
     * @param childChunkId 子分段 ID
     * @param content 子分段内容
     */
    SegmentChildChunk updateSegmentChildChunks(
            String datasetId,
            String documentId,
            String segmentId,
            String childChunkId,
            String content
    );

}
