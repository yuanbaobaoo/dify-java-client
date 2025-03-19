package io.github.yuanbaobaoo.dify.dataset.heros;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import io.github.yuanbaobaoo.dify.DifyConfig;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.routes.DatasetRoutes;
import io.github.yuanbaobaoo.dify.dataset.entity.Dataset;
import io.github.yuanbaobaoo.dify.dataset.entity.Document;
import io.github.yuanbaobaoo.dify.dataset.types.RetrieveResult;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDocument;
import io.github.yuanbaobaoo.dify.dataset.types.BatchStatus;
import io.github.yuanbaobaoo.dify.dataset.types.DocumentResult;
import io.github.yuanbaobaoo.dify.dataset.types.RetrievalModel;
import io.github.yuanbaobaoo.dify.types.DifyPage;
import io.github.yuanbaobaoo.dify.types.DifyRoute;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatasetHero extends Dataset {
    private final DifyConfig config;

    /**
     * new DatasetHero
     *
     * @param id     Dataset id
     * @param config DifyConfig
     */
    public static DatasetHero of(String id, DifyConfig config) {
        return new DatasetHero(config.getServer(), config.getApiKey(), id);
    }

    /**
     * new DatasetHero
     *
     * @param dataset Dataset
     * @param config  DifyConfig
     */
    public static DatasetHero of(Dataset dataset, DifyConfig config) {
        return new DatasetHero(config.getServer(), config.getApiKey(), dataset);
    }

    /**
     * Constructor
     *
     * @param server Dify Server Address
     * @param apiKey Api Key
     * @param id     Dataset id
     */
    private DatasetHero(String server, String apiKey, String id) {
        this.config = DifyConfig.builder().server(server).apiKey(apiKey).build();
        this.setId(id);
    }

    /**
     * Constructor
     *
     * @param server  Dify Server Address
     * @param apiKey  Api Key
     * @param dataset Dataset
     */
    private DatasetHero(String server, String apiKey, Dataset dataset) {
        this.config = DifyConfig.builder().server(server).apiKey(apiKey).build();

        this.setId(dataset.getId());
        this.setName(dataset.getName());
        this.setDescription(dataset.getDescription());
        this.setProvider(dataset.getProvider());
        this.setPermission(dataset.getPermission());
        this.setDataSourceType(dataset.getDataSourceType());
        this.setIndexingTechnique(dataset.getIndexingTechnique());
        this.setAppCount(dataset.getAppCount());
        this.setDocumentCount(dataset.getDocumentCount());
        this.setWordCount(dataset.getWordCount());
        this.setCreatedBy(dataset.getCreatedBy());
        this.setCreatedAt(dataset.getCreatedAt());
        this.setUpdatedBy(dataset.getUpdatedBy());
        this.setUpdatedAt(dataset.getUpdatedAt());
        this.setEmbeddingModel(dataset.getEmbeddingModel());
        this.setEmbeddingModelProvider(dataset.getEmbeddingModelProvider());
        this.setEmbeddingAvailable(dataset.getEmbeddingAvailable());
        this.setTags(dataset.getTags());
        this.setDocForm(dataset.getDocForm());
        this.setRetrievalModelDict(dataset.getRetrievalModelDict());
        this.setExternalKnowledgeInfo(dataset.getExternalKnowledgeInfo());
        this.setExternalRetrievalModel(dataset.getExternalRetrievalModel());
    }

    /**
     * 快捷创建一个Document对象
     *
     * @param documentId 文档ID
     */
    public DocumentHero ofDocument(String documentId) {
        return DocumentHero.of(this.getId(), documentId, this.config);
    }

    /**
     * 删除数据库
     */
    public void delete() {
        DifyHttpClient.get(config).requestJson(DatasetRoutes.DATASETS_DELETE);
    }

    /**
     * 通过文本创建文档
     *
     * @param doc ParamDocument
     */
    public DocumentResult insertText(ParamDocument doc) {
        return insertText(doc, null, null, null);
    }

    /**
     * 通过文本创建文档
     *
     * @param doc                    ParamDocument
     * @param retrievalModel         RetrievalModel
     * @param embeddingModel         Embedding 模型名称
     * @param embeddingModelProvider Embedding 模型供应商
     */
    public DocumentResult insertText(ParamDocument doc, RetrievalModel retrievalModel, String embeddingModel, String embeddingModelProvider) {
        Map<String, Object> params = doc.toMap();
        params.put("retrieval_model", retrievalModel);
        params.put("embedding_model", embeddingModel);
        params.put("embedding_model_provider", embeddingModelProvider);

        DifyRoute route = DatasetRoutes.DATASETS_CREATE_DOC_TEXT.format(new HashMap<>() {{
            put("datasetId", getId());
        }});

        String result = DifyHttpClient.get(config).requestJson(route, null, params);
        return DocumentResult.parse(this.getId(), result, config);
    }

    /**
     * 通过文件创建文档
     *
     * @param file File
     * @param data ParamDocument
     */
    public DocumentResult insertFile(File file, ParamDocument data) {
        return insertFile(file, data, null, null, null);
    }

    /**
     * 通过文件创建文档
     *
     * @param file                   File
     * @param data                   ParamDocument
     * @param retrievalModel         retrievalModel
     * @param embeddingModel         Embedding 模型名称
     * @param embeddingModelProvider Embedding 模型供应商
     */
    public DocumentResult insertFile(File file, ParamDocument data, RetrievalModel retrievalModel, String embeddingModel, String embeddingModelProvider) {
        Map<String, Object> params = new HashMap<>();
        params.put("file", file);
        params.put("data", data);
        params.put("retrieval_model", retrievalModel);
        params.put("embedding_model", embeddingModel);
        params.put("embedding_model_provider", embeddingModelProvider);

        DifyRoute route = DatasetRoutes.DATASETS_CREATE_DOC_FILE.format(new HashMap<>() {{
            put("datasetId", getId());
        }});

        String result = DifyHttpClient.get(config).requestMultipart(route, null, params);
        return DocumentResult.parse(this.getId(), result, config);
    }

    /**
     * 获取文档嵌入状态（进度）
     *
     * @param batch 上传文档的批次号
     */
    public List<BatchStatus> queryBatchStatus(String batch) {
        DifyRoute route = DatasetRoutes.DATASETS_INDEXING_STATUS.format(new HashMap<>() {{
            put("datasetId", getId());
            put("batch", batch);
        }});

        JSONObject result = JSON.parseObject(
                DifyHttpClient.get(config).requestJson(route)
        );

        return result.getJSONArray("data").toJavaList(BatchStatus.class);
    }

    /**
     * 获取知识库文档列表
     */
    public DifyPage<Document> documents() {
        return documents(null, null, null);
    }

    /**
     * 获取知识库文档列表
     *
     * @param page    页码
     * @param limit   返回条数
     * @param keyword 搜索关键词，可选，目前仅搜索文档名称
     */
    public DifyPage<Document> documents(Integer page, Integer limit, String keyword) {
        DifyRoute route = DatasetRoutes.DATASETS_DOCS.format(new HashMap<>() {{
            put("datasetId", getId());
        }});

        Map<String, Object> query = new HashMap<>() {{
            put("page", page);
            put("limit", limit);
            put("keyword", keyword);
        }};

        String result = DifyHttpClient.get(config).requestJson(route, query, null);
        return JSON.parseObject(result, new TypeReference<DifyPage<Document>>() {
        });
    }

    /**
     * 检索知识库
     *
     * @param query 检索关键词
     */
    public RetrieveResult retrieve(String query) {
        return retrieve(query, null);
    }

    /**
     * 检索知识库
     *
     * @param query          检索关键词
     * @param retrievalModel RetrievalModel
     */
    public RetrieveResult retrieve(String query, RetrievalModel retrievalModel) {
        String result = DifyHttpClient.get(config).requestJson(DatasetRoutes.DATASETS_RETRIEVE.format(new HashMap<>() {{
            put("datasetId", getId());
        }}), null, new HashMap<>() {{
            put("query", query);
            put("retrieval_model", retrievalModel);
        }});

        return JSON.parseObject(result, RetrieveResult.class);
    }

}
