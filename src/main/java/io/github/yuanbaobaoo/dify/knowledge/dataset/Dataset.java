package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.annotation.JSONType;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.routes.DifyRoutes;
import io.github.yuanbaobaoo.dify.types.DifyRoute;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class Dataset {
    private String id;
    private String name;
    private String description;
    private String provider;
    private String permission;
    private String dataSourceType;
    private DatasetConsts.IndexingTechnique indexingTechnique;
    private Integer appCount;
    private Long documentCount;
    private Long wordCount;
    private String createdBy;
    private Integer createdAt;
    private String updatedBy;
    private Integer updatedAt;
    private String embeddingModel;
    private String embeddingModelProvider;
    private String embeddingAvailable;
    private List<Object> tags;
    private Object docForm;
    private JSONObject retrievalModelDict;
    private JSONObject externalKnowledgeInfo;
    private JSONObject externalRetrievalModel;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private DifyHttpClient apiClient;

    /**
     * Constructor
     */
    public Dataset() {

    }

    /**
     * Constructor
     *
     * @param id     dataset id
     * @param client DatasetClient
     */
    public Dataset(String id, DifyHttpClient client) {
        this.id = id;
        this.apiClient = client;
    }

    /**
     * DifyHttpClient
     * @param client DifyHttpClient
     */
    public void resetDifyClient(DifyHttpClient client) {
        this.apiClient = client;
    }

    /**
     * 通过文本创建文档
     * @param doc ParamDocument
     */
    public DatasetDoc insertText(ParamDocument doc) throws IOException, InterruptedException {
        return insertText(doc, null, null, null);
    }

    /**
     * 通过文本创建文档
     * @param doc ParamDocument
     * @param retrievalModel RetrievalModel
     * @param embeddingModel Embedding 模型名称
     * @param embeddingModelProvider Embedding 模型供应商
     */
    public DatasetDoc insertText(ParamDocument doc, RetrievalModel retrievalModel, String embeddingModel, String embeddingModelProvider)
            throws IOException, InterruptedException {
        Map<String, Object> params = doc.toMap();
        params.put("retrieval_model", retrievalModel);
        params.put("embedding_model", embeddingModel);
        params.put("embedding_model_provider", embeddingModelProvider);

        DifyRoute route = DifyRoutes.DATASETS_DOC_CREATE_TEXT.format(new HashMap<>() {{
            put("datasetId", id);
        }});

        String result = apiClient.requestJson(route, null, params);
        DatasetDoc document = JSON.parseObject(result, DatasetDoc.class);
        document.getDocument().resetDifyClient(this.apiClient);

        return document;
    }

    /**
     * 通过文件创建文档
     * @param file File
     * @param data ParamDocument
     */
    public DatasetDoc insertFile(File file, ParamDocument data) throws IOException, InterruptedException {
        return insertFile(file, data, null, null, null);
    }

    /**
     * 通过文件创建文档
     * @param file File
     * @param data ParamDocument
     * @param retrievalModel retrievalModel
     * @param embeddingModel Embedding 模型名称
     * @param embeddingModelProvider Embedding 模型供应商
     */
    public DatasetDoc insertFile(File file, ParamDocument data, RetrievalModel retrievalModel, String embeddingModel, String embeddingModelProvider)
            throws IOException, InterruptedException {
        Map<String, Object> params = new HashMap<>();
        params.put("file", file);
        params.put("data", data);
        params.put("retrieval_model", retrievalModel);
        params.put("embedding_model", embeddingModel);
        params.put("embedding_model_provider", embeddingModelProvider);

        DifyRoute route = DifyRoutes.DATASETS_DOC_CREATE_FILE.format(new HashMap<>() {{
            put("datasetId", id);
        }});

        String result = apiClient.requestMultipart(route, null, params);
        DatasetDoc document = JSON.parseObject(result, DatasetDoc.class);
        document.getDocument().resetDifyClient(this.apiClient);

        return document;
    }

    /**
     * 获取知识库文档列表
     */
    public DifyPage<Document> documents() throws IOException, InterruptedException {
        return documents(null ,null, null);
    }

    /**
     * 获取知识库文档列表
     * @param page 页码
     * @param limit 返回条数
     * @param keyword 搜索关键词，可选，目前仅搜索文档名称
     */
    public DifyPage<Document> documents(Integer page, Integer limit, String keyword) throws IOException, InterruptedException {
        DifyRoute route = DifyRoutes.DATASETS_DOC_LIST.format(new HashMap<>() {{
            put("datasetId", id);
        }});

        Map<String, Object> query = new HashMap<>(){{
            put("page", page);
            put("limit", limit);
            put("keyword", keyword);
        }};

        String result = apiClient.requestJson(route, query, null);
        System.out.println(result);
        DifyPage<Document> docs = JSON.parseObject(result, new TypeReference<DifyPage<Document>>() {});

        for (Document doc : docs.getData()) {
            doc.resetDifyClient(this.apiClient);
        }

        return docs;
    }

    /**
     * 检索知识库
     */
    public void retrieve(String query, RetrievalModel retrievalModel) throws IOException, InterruptedException {
        String result = apiClient.requestJson(DifyRoutes.DATASETS_RETRIEVE.format(new HashMap<>() {{
            put("datasetId", id);
        }}), null, retrievalModel);
    }

}
