package io.github.yuanbaobaoo.dify.dataset.heros;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.DifyConfig;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.routes.DatasetRoutes;
import io.github.yuanbaobaoo.dify.dataset.entity.DocFileInfo;
import io.github.yuanbaobaoo.dify.dataset.entity.Document;
import io.github.yuanbaobaoo.dify.dataset.entity.Segment;
import io.github.yuanbaobaoo.dify.dataset.types.SegmentResult;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDocument;
import io.github.yuanbaobaoo.dify.dataset.types.DocumentResult;
import io.github.yuanbaobaoo.dify.dataset.types.ProcessRule;
import io.github.yuanbaobaoo.dify.types.DifyRoute;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class DocumentHero extends Document {
    private final String datasetId;
    private final DifyConfig config;

    /**
     * new DocumentHero
     * @param datasetId dataset id
     * @param id document id
     * @param config DifyConfig
     */
    public static DocumentHero of(String datasetId, String id, DifyConfig config) {
        return new DocumentHero(datasetId, id, config);
    }

    /**new DocumentHero
     *
     * @param datasetId dataset id
     * @param doc Document
     * @param config DifyConfig
     */
    public static DocumentHero of(String datasetId, Document doc, DifyConfig config) {
        return new DocumentHero(datasetId, doc, config);
    }

    /**
     * Constructor
     * @param datasetId Dataset Id
     * @param id Document id
     * @param config DifyConfig
     */
    private DocumentHero(String datasetId, String id, DifyConfig config) {
        this.config = config;
        this.datasetId = datasetId;

        this.setId(id);
    }

    /**
     * Constructor
     * @param datasetId Dataset Id
     * @param doc Document
     * @param config DifyConfig
     */
    private DocumentHero(String datasetId, Document doc, DifyConfig config) {
        this.config = config;
        this.datasetId = datasetId;

        this.setId(doc.getId());
        this.setName(doc.getName());
        this.setDocForm(doc.getDocForm());
        this.setDatasetProcessRuleId(doc.getDatasetProcessRuleId());
        this.setDataSourceType(doc.getDataSourceType());
        this.setDataSourceInfo(doc.getDataSourceInfo());
        this.setDataSourceDetailDict(doc.getDataSourceDetailDict());
        this.setCreatedAt(doc.getCreatedAt());
        this.setCreatedBy(doc.getCreatedBy());
        this.setDisabledAt(doc.getDisabledAt());
        this.setDisabledBy(doc.getDisabledBy());
        this.setEnabled(doc.getEnabled());
        this.setError(doc.getError());
        this.setHitCount(doc.getHitCount());
        this.setWordCount(doc.getWordCount());
        this.setDisplayStatus(doc.getDisplayStatus());
        this.setIndexingStatus(doc.getIndexingStatus());
        this.setTokens(doc.getTokens());
        this.setArchived(doc.getArchived());
        this.setCreatedFrom(doc.getCreatedFrom());
        this.setPosition(doc.getPosition());
    }

    /**
     * 通过文本更新文档
     */
    public DocumentResult updateByText(ParamDocument data) {
        DifyRoute route = DatasetRoutes.DATASETS_DOCS_UPDATE_TEXT.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = DifyHttpClient.get(config).requestJson(route, null, data);
        return DocumentResult.parse(datasetId, result, config);
    }

    /**
     * 通过文件更新文档
     * @param file File
     * @param rule ProcessRule
     */
    public DocumentResult updateByFile(File file, ProcessRule rule) {
        return updateByFile(null, file, rule);
    }

    /**
     * 通过文件更新文档
     *
     * @param name 文档名称
     * @param file 需要上传的文件
     * @param rule 处理规则
     */
    public DocumentResult updateByFile(String name, File file, ProcessRule rule) {
        DifyRoute route = DatasetRoutes.DATASETS_DOCS_UPDATE_FILE.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = DifyHttpClient.get(config).requestMultipart(route, null, new HashMap<>() {{
            put("name", name);
            put("file", file);
            put("process_rule", rule);
        }});

        return DocumentResult.parse(datasetId, result, config);
    }

    /**
     * 删除文档
     */
    public Boolean delete() {
        DifyRoute route = DatasetRoutes.DATASETS_DOCS_DELETE.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = DifyHttpClient.get(config).requestJson(route);
        JSONObject json = JSON.parseObject(result);

        return "success".equals(json.getString("result"));
    }

    /**
     * 获取上传文件
     */
    public DocFileInfo queryFileInfo() {
        DifyRoute route = DatasetRoutes.DATASETS_DOCS_FILE_INFO.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = DifyHttpClient.get(config).requestJson(route);
        return JSON.parseObject(result, DocFileInfo.class);
    }

    /**
     * 查询文档分段
     */
    public SegmentResult querySegments() {
        return querySegments(null, null);
    }

    /**
     * 查询文档分段
     * @param keyword 搜索关键词
     */
    public SegmentResult querySegments(String keyword) {
        return querySegments(keyword, null);
    }

    /**
     * 查询文档分段
     * @param keyword 搜索关键词
     * @param status 搜索状态
     */
    public SegmentResult querySegments(String keyword, String status) {
        DifyRoute route = DatasetRoutes.DATASETS_DOCS_SEGMENTS.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = DifyHttpClient.get(config).requestJson(route, new HashMap<>(){{
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
    public SegmentResult insertSegment(List<Segment> segments) {
        DifyRoute route = DatasetRoutes.DATASETS_DOCS_SEGMENTS_ADD.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
        }});

        String result = DifyHttpClient.get(config).requestJson(route, null, new HashMap<>(){{
            put("segments", segments);
        }});

        return JSON.parseObject(result, SegmentResult.class);
    }

    /**
     * 删除文档分段
     * @param segmentId 文档分段ID
     */
    public Boolean deleteSegment(String segmentId) {
        DifyRoute route = DatasetRoutes.DATASETS_DOCS_SEGMENTS_DEL.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
            put("segmentId", segmentId);
        }});

        String result = DifyHttpClient.get(config).requestJson(route);
        JSONObject json = JSON.parseObject(result);

        return "success".equals(json.getString("result"));
    }

    /**
     * 更新文档分段
     * @param segmentId 文档分段ID
     * @param content  文本内容/问题内容
     * @param regenerate 是否重新生成子分段
     */
    public SegmentResult updateSegment(String segmentId, String content, boolean regenerate) {
        return updateSegment(Segment.builder().id(segmentId).content(content).build(), regenerate);
    }

    /**
     * 更新文档分段
     * @param segment Segment
     * @param regenerate  是否重新生成子分段
     */
    public SegmentResult updateSegment(Segment segment, boolean regenerate) {
        DifyRoute route = DatasetRoutes.DATASETS_DOCS_SEGMENTS_UPDATE.format(new HashMap<>() {{
            put("datasetId", datasetId);
            put("documentId", getId());
            put("segmentId", segment.getId());
        }});

        String result = DifyHttpClient.get(config).requestJson(route, null, new HashMap<>(){{
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
