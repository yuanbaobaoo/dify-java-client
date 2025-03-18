package io.github.yuanbaobaoo.dify.dataset.heros;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.app.routes.AppRoutes;
import io.github.yuanbaobaoo.dify.dataset.entity.DocFileInfo;
import io.github.yuanbaobaoo.dify.dataset.entity.Document;
import io.github.yuanbaobaoo.dify.dataset.entity.Segment;
import io.github.yuanbaobaoo.dify.dataset.entity.SegmentResult;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDocument;
import io.github.yuanbaobaoo.dify.dataset.types.DocumentResult;
import io.github.yuanbaobaoo.dify.dataset.types.ProcessRule;
import io.github.yuanbaobaoo.dify.types.DifyRoute;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class DocumentHero extends Document {
    private String datasetId;
    private DifyHttpClient client;

    /**
     * Constructor
     */
    public DocumentHero() {

    }

    /**
     * Constructor
     *
     * @param datasetId dataset id
     * @param id        dataset id
     * @param client    DatasetClient
     */
    public DocumentHero(String datasetId, String id, DifyHttpClient client) {
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
    public DocumentResult updateByText(ParamDocument data) throws IOException, InterruptedException {
        DifyRoute route = AppRoutes.DATASETS_DOCS_UPDATE_TEXT.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = client.requestJson(route, null, data);
        DocumentResult document = JSON.parseObject(result, DocumentResult.class);
        document.getDocument().resetDifyClient(this.client);

        return document;
    }

    /**
     * 通过文件更新文档
     * @param file File
     * @param rule ProcessRule
     */
    public DocumentResult updateByFile(File file, ProcessRule rule) throws IOException, InterruptedException {
        return updateByFile(null, file, rule);
    }

    /**
     * 通过文件更新文档
     *
     * @param name 文档名称
     * @param file 需要上传的文件
     * @param rule 处理规则
     */
    public DocumentResult updateByFile(String name, File file, ProcessRule rule) throws IOException, InterruptedException {
        DifyRoute route = AppRoutes.DATASETS_DOCS_UPDATE_FILE.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = client.requestMultipart(route, null, new HashMap<>() {{
            put("name", name);
            put("file", file);
            put("process_rule", rule);
        }});

        DocumentResult document = JSON.parseObject(result, DocumentResult.class);
        document.getDocument().resetDifyClient(this.client);

        return document;
    }

    /**
     * 删除文档
     */
    public Boolean delete() throws IOException, InterruptedException {
        DifyRoute route = AppRoutes.DATASETS_DOCS_DELETE.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = client.requestJson(route);
        JSONObject json = JSON.parseObject(result);

        return "success".equals(json.getString("result"));
    }

    /**
     * 获取上传文件
     */
    public DocFileInfo queryFileInfo() throws IOException, InterruptedException {
        DifyRoute route = AppRoutes.DATASETS_DOCS_FILE_INFO.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = client.requestJson(route);
        return JSON.parseObject(result, DocFileInfo.class);
    }

    /**
     * 查询文档分段
     */
    public SegmentResult querySegments() throws IOException, InterruptedException {
        return querySegments(null, null);
    }

    /**
     * 查询文档分段
     * @param keyword 搜索关键词
     */
    public SegmentResult querySegments(String keyword) throws IOException, InterruptedException {
        return querySegments(keyword, null);
    }

    /**
     * 查询文档分段
     * @param keyword 搜索关键词
     * @param status 搜索状态
     */
    public SegmentResult querySegments(String keyword, String status) throws IOException, InterruptedException {
        DifyRoute route = AppRoutes.DATASETS_DOCS_SEGMENTS.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = client.requestJson(route, new HashMap<>(){{
            put("keyword", keyword);
            put("status", status);
        }}, null);

        return JSON.parseObject(result, SegmentResult.class);
    }

    /**
     * 新增分段
     *
     * @param segments  List<Segment>
     *      content  文本内容/问题内容
     *      keywords 关键字，非必填
     *      answer   答案内容，非必填，如果知识库的模式为 Q&A 模式则传值
     */
    public SegmentResult insertSegment(List<Segment> segments) throws IOException, InterruptedException {
        DifyRoute route = AppRoutes.DATASETS_DOCS_SEGMENTS_ADD.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = client.requestJson(route, null, new HashMap<>(){{
            put("segments", segments);
        }});

        return JSON.parseObject(result, SegmentResult.class);
    }

    /**
     * 删除文档分段
     * @param segmentId 文档分段ID
     */
    public Boolean deleteSegment(String segmentId) throws IOException, InterruptedException {
        DifyRoute route = AppRoutes.DATASETS_DOCS_SEGMENTS_DEL.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
            put("segmentId", segmentId);
        }});

        String result = client.requestJson(route);
        JSONObject json = JSON.parseObject(result);

        return "success".equals(json.getString("result"));
    }

    /**
     * 更新文档分段
     * @param segmentId 文档分段ID
     * @param content  文本内容/问题内容
     * @param regenerate 是否重新生成子分段
     */
    public SegmentResult updateSegment(String segmentId, String content, boolean regenerate) throws IOException, InterruptedException {
        return updateSegment(Segment.builder().id(segmentId).content(content).build(), regenerate);
    }

    /**
     * 更新文档分段
     * @param segment Segment
     * @param regenerate  是否重新生成子分段
     */
    public SegmentResult updateSegment(Segment segment, boolean regenerate) throws IOException, InterruptedException {
        DifyRoute route = AppRoutes.DATASETS_DOCS_SEGMENTS_UPDATE.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
            put("segmentId", segment.getId());
        }});

        String result = client.requestJson(route, null, new HashMap<>(){{
            put("segment", new HashMap<>() {{
                put("content", segment.getContent());
                put("answer", segment.getAnswer());
                put("keywords", segment.getKeywords());
                put("enabled", segment.getEnabled());
                put("regenerate_child_chunks", regenerate);
            }});
        }});

        return JSON.parseObject(result, SegmentResult.class);
    }

}
