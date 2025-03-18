package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.routes.DifyRoutes;
import io.github.yuanbaobaoo.dify.routes.HttpMethod;
import io.github.yuanbaobaoo.dify.types.DifyRoute;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperDataset extends Dataset {
    private DifyHttpClient client;

    /**
     * Constructor
     */
    public SuperDataset() {

    }

    /**
     * Constructor
     *
     * @param id     dataset id
     * @param client DatasetClient
     */
    public SuperDataset(String id, DifyHttpClient client) {
        this.setId(id);
        this.client = client;
    }

    /**
     * DifyHttpClient
     * @param client DifyHttpClient
     */
    public void resetDifyClient(DifyHttpClient client) {
        this.client = client;
    }

    /**
     * 快捷创建一个Document对象
     * @param documentId 文档ID
     */
    public SuperDocument ofDocument(String documentId) {
        return new SuperDocument(this.getId(), documentId, this.client);
    }

    /**
     * 删除数据库
     */
    public void delete() throws IOException, InterruptedException {
        client.requestJson(
                String.format("%s/%s", DifyRoutes.DATASETS.getUrl(), getId()),
                HttpMethod.DELETE,
                null,
                null
        );
    }

    /**
     * 通过文本创建文档
     * @param doc ParamDocument
     */
    public DocAlterResult insertText(ParamDocument doc) throws IOException, InterruptedException {
        return insertText(doc, null, null, null);
    }

    /**
     * 通过文本创建文档
     * @param doc ParamDocument
     * @param retrievalModel RetrievalModel
     * @param embeddingModel Embedding 模型名称
     * @param embeddingModelProvider Embedding 模型供应商
     */
    public DocAlterResult insertText(ParamDocument doc, RetrievalModel retrievalModel, String embeddingModel, String embeddingModelProvider)
            throws IOException, InterruptedException {
        Map<String, Object> params = doc.toMap();
        params.put("retrieval_model", retrievalModel);
        params.put("embedding_model", embeddingModel);
        params.put("embedding_model_provider", embeddingModelProvider);

        DifyRoute route = DifyRoutes.DATASETS_CREATE_DOC_TEXT.format(new HashMap<>() {{
            put("datasetId", getId());
        }});

        String result = client.requestJson(route, null, params);
        DocAlterResult document = JSON.parseObject(result, DocAlterResult.class);
        document.getDocument().resetDifyClient(this.client);

        return document;
    }

    /**
     * 通过文件创建文档
     * @param file File
     * @param data ParamDocument
     */
    public DocAlterResult insertFile(File file, ParamDocument data) throws IOException, InterruptedException {
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
    public DocAlterResult insertFile(File file, ParamDocument data, RetrievalModel retrievalModel, String embeddingModel, String embeddingModelProvider)
            throws IOException, InterruptedException {
        Map<String, Object> params = new HashMap<>();
        params.put("file", file);
        params.put("data", data);
        params.put("retrieval_model", retrievalModel);
        params.put("embedding_model", embeddingModel);
        params.put("embedding_model_provider", embeddingModelProvider);

        DifyRoute route = DifyRoutes.DATASETS_CREATE_DOC_FILE.format(new HashMap<>() {{
            put("datasetId", getId());
        }});

        String result = client.requestMultipart(route, null, params);
        DocAlterResult document = JSON.parseObject(result, DocAlterResult.class);
        document.getDocument().resetDifyClient(this.client);

        return document;
    }

    /**
     * 获取文档嵌入状态（进度）
     * @param batch 上传文档的批次号
     */
    public List<DocBatch> queryBatchStatus(String batch) throws IOException, InterruptedException {
        DifyRoute route = DifyRoutes.DATASETS_INDEXING_STATUS.format(new HashMap<>() {{
            put("datasetId", getId());
            put("batch", batch);
        }});

        JSONObject result = JSON.parseObject(
                client.requestJson(route)
        );

        return result.getJSONArray("data").toJavaList(DocBatch.class);
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
        DifyRoute route = DifyRoutes.DATASETS_DOCS.format(new HashMap<>() {{
            put("datasetId", getId());
        }});

        Map<String, Object> query = new HashMap<>(){{
            put("page", page);
            put("limit", limit);
            put("keyword", keyword);
        }};

        String result = client.requestJson(route, query, null);
        return JSON.parseObject(result, new TypeReference<DifyPage<Document>>() {});
    }

    /**
     * 检索知识库
     * @param query 检索关键词
     * @param retrievalModel RetrievalModel
     */
    public RetrieveResult retrieve(String query, RetrievalModel retrievalModel) throws IOException, InterruptedException {
        String result = client.requestJson(DifyRoutes.DATASETS_RETRIEVE.format(new HashMap<>() {{
            put("datasetId", getId());
        }}), null, new HashMap<>() {{
            put("query", query);
            put("retrieval_model", retrievalModel);
        }});

        return JSON.parseObject(result, RetrieveResult.class);
    }
}
