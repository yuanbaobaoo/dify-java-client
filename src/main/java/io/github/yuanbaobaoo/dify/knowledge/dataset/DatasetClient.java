package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.routes.DifyRoutes;
import io.github.yuanbaobaoo.dify.routes.HttpMethod;

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
    public Dataset create(ParamDataset data) throws IOException, InterruptedException {
        String result = client.requestJson(DifyRoutes.DATASETS, null, data);

        Dataset dataset = JSON.parseObject(result, Dataset.class);
        dataset.resetDifyClient(this.client);

        return dataset;
    }

    /**
     * 查询知识库列表
     * @param page 页码
     * @param limit 返回条数，默认 20，范围 1-100
     */
    public DifyPage<Dataset> list(int page, int limit) throws IOException, InterruptedException {
        String result = client.requestJson(DifyRoutes.DATASETS.getUrl(), HttpMethod.GET, new HashMap<>() {{
            put("page", page);
            put("limit", limit);
        }}, null);

        DifyPage<Dataset> sets = JSON.parseObject(result, new TypeReference<DifyPage<Dataset>>() {});

        for (Dataset set: sets.getData()) {
            set.resetDifyClient(this.client);
        }

        return sets;
    }

    /**
     * 删除数据库
     * @param datasetId 知识库ID
     */
    public void delete(String datasetId) throws IOException, InterruptedException {
        client.requestJson(
                String.format("%s/%s", DifyRoutes.DATASETS.getUrl(), datasetId),
                HttpMethod.DELETE,
                null,
                null
        );
    }

    /**
     * 快捷构建一个Dataset对象
     * @param datasetId 知识库ID
     */
    public Dataset ofDataset(String datasetId) {
        return new Dataset(datasetId, this.client);
    }

    /**
     * 快捷创建一个DifyDocument对象
     * @param documentId 文档ID
     */
    public void ofDocument(String documentId) {

    }

}
