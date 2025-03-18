package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.routes.DifyRoutes;
import io.github.yuanbaobaoo.dify.types.DifyRoute;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SuperDocument extends Document {
    private String datasetId;
    private DifyHttpClient client;


    /**
     * Constructor
     */
    public SuperDocument() {

    }

    /**
     * Constructor
     *
     * @param datasetId dataset id
     * @param id        dataset id
     * @param client    DatasetClient
     */
    public SuperDocument(String datasetId, String id, DifyHttpClient client) {
        this.setId(id);
        this.client = client;
        this.datasetId = datasetId;
    }

    /**
     * DifyHttpClient
     *
     * @param client DifyHttpClient
     */
    public void resetDifyClient(DifyHttpClient client) {
        this.client = client;
    }

    /**
     * 通过文本更新文档
     */
    public DocAlterResult updateByText(ParamDocument data) throws IOException, InterruptedException {
        DifyRoute route = DifyRoutes.DATASETS_DOCS_UPDATE_TEXT.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = client.requestJson(route, null, data);
        DocAlterResult document = JSON.parseObject(result, DocAlterResult.class);
        document.getDocument().resetDifyClient(this.client);

        return document;
    }

    /**
     * 通过文件更新文档
     */
    public void updateByFile() {

    }

    /**
     * 删除文档
     */
    public void delete() {

    }

    /**
     * 获取文档嵌入状态（进度）
     *
     * @param batch 上传文档的批次号
     */
    public void queryIndexingStatus(List<String> batch) {

    }

    public String querySegments() {
        return "ss";
    }

    public void addSegments() {

    }

    public void delSegments() {

    }

    @JSONField(deserialize = false)
    public void setSegments() {

    }

    @JSONField(serialize = false)
    public void getFileInfo() {

    }

}
