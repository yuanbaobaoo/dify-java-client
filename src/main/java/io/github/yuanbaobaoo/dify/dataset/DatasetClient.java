package io.github.yuanbaobaoo.dify.dataset;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import io.github.yuanbaobaoo.dify.dataset.entity.Dataset;
import io.github.yuanbaobaoo.dify.types.DifyPage;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDataset;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.app.routes.AppRoutes;
import io.github.yuanbaobaoo.dify.dataset.heros.DatasetHero;
import io.github.yuanbaobaoo.dify.dataset.heros.DocumentHero;
import io.github.yuanbaobaoo.dify.types.HttpMethod;

import java.io.IOException;
import java.util.HashMap;

public class DatasetClient {
    private final DifyHttpClient client;

    /**
     * constructor
     * @param server Dify Server Address
     * @param apiKey API KEY
     */
    public DatasetClient(String server, String apiKey) {
        client = new DifyHttpClient(server, apiKey);
    }

    /**
     * Create an Empty Knowledge Base. 创建空知识库
     * @param data ParamDataset
     */
    public DatasetHero create(ParamDataset data) throws IOException, InterruptedException {
        String result = client.requestJson(AppRoutes.DATASETS, null, data);

        DatasetHero dataset = JSON.parseObject(result, DatasetHero.class);
        dataset.resetDifyClient(this.client);

        return dataset;
    }

    /**
     * 查询知识库列表
     * @param page 页码
     * @param limit 返回条数，默认 20，范围 1-100
     */
    public DifyPage<Dataset> list(int page, int limit) throws IOException, InterruptedException {
        String result = client.requestJson(AppRoutes.DATASETS.getUrl(), HttpMethod.GET, new HashMap<>() {{
            put("page", page);
            put("limit", limit);
        }}, null);

        return JSON.parseObject(result, new TypeReference<DifyPage<Dataset>>() {});
    }

    /**
     * 删除数据库
     * @param datasetId 知识库ID
     */
    public void delete(String datasetId) throws IOException, InterruptedException {
        ofDataset(datasetId).delete();
    }

    /**
     * 快捷构建一个Dataset对象
     * @param datasetId 知识库ID
     */
    public DatasetHero ofDataset(String datasetId) {
        return new DatasetHero(datasetId, this.client);
    }

    /**
     * 快捷创建一个Document对象
     * @param documentId 文档ID
     */
    public DocumentHero ofDocument(String datasetId, String documentId) {
        return new DocumentHero(datasetId, documentId, this.client);
    }

}
