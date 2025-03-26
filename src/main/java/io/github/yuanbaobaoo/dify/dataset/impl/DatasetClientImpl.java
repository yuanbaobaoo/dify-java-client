package io.github.yuanbaobaoo.dify.dataset.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import io.github.yuanbaobaoo.dify.utils.DifyApiConfig;
import io.github.yuanbaobaoo.dify.dataset.IDatasetClient;
import io.github.yuanbaobaoo.dify.dataset.entity.*;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDocument;
import io.github.yuanbaobaoo.dify.dataset.types.*;
import io.github.yuanbaobaoo.dify.types.DifyPage;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDataset;
import io.github.yuanbaobaoo.dify.utils.DifyHttpClient;
import io.github.yuanbaobaoo.dify.routes.DatasetRoutes;
import io.github.yuanbaobaoo.dify.dataset.heros.DatasetHero;
import io.github.yuanbaobaoo.dify.dataset.heros.DocumentHero;
import io.github.yuanbaobaoo.dify.types.HttpMethod;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DatasetClientImpl implements IDatasetClient {
    private final DifyApiConfig server;

    private final Map<String, DatasetHero> datasetCache = new ConcurrentHashMap<>();
    private final Map<String, DocumentHero> documentCache = new ConcurrentHashMap<>();

    /**
     * build
     * @param server Dify Server Address
     * @param apiKey API KEY
     */
    public static DatasetClientImpl build(String server, String apiKey) {
        return new DatasetClientImpl(server, apiKey);
    }

    /**
     * constructor
     * @param server String
     * @param apiKey String
     */
    private DatasetClientImpl(String server, String apiKey) {
        this.server = DifyApiConfig.builder().server(server).apiKey(apiKey).build();
    }

    /**
     * ofDataset
     * @param formCache boolean
     * @param datasetId String
     */
    private DatasetHero ofDataset(boolean formCache, String datasetId) {
        if (!formCache) {
            return DatasetHero.of(datasetId, server);
        }

        return datasetCache.computeIfAbsent(datasetId, k -> DatasetHero.of(datasetId, server));
    }

    /**
     * ofDocument
     * @param formCache boolean
     * @param datasetId String
     * @param documentId String
     */
    private DocumentHero ofDocument(boolean formCache, String datasetId, String documentId) {
        if (!formCache) {
            return DocumentHero.of(datasetId, documentId, server);
        }

        return documentCache.computeIfAbsent(
                String.format("%s-%s", datasetId, documentId),
                k -> DocumentHero.of(datasetId, documentId, server)
        );
    }

    @Override
    public DatasetHero ofDataset(String datasetId) {
        return ofDataset(false, datasetId);
    }

    @Override
    public DocumentHero ofDocument(String datasetId, String documentId) {
        return ofDocument(false, datasetId, documentId);
    }

    @Override
    public DatasetHero create(ParamDataset data) {
        String result = DifyHttpClient.get(server).requestJson(DatasetRoutes.DATASETS, null, data);

        Dataset dataset = JSON.parseObject(result, Dataset.class);
        return DatasetHero.of(dataset, server);
    }

    @Override
    public DifyPage<Dataset> list(int page, int limit) {
        String result = DifyHttpClient.get(server).requestJson(DatasetRoutes.DATASETS.getUrl(), HttpMethod.GET, new HashMap<>() {{
            put("page", page);
            put("limit", limit);
        }}, null);

        return JSON.parseObject(result, new TypeReference<DifyPage<Dataset>>() {});
    }

    @Override
    public void delete(String datasetId) {
        ofDataset(true, datasetId).delete();
    }

    @Override
    public DocumentResult insertDocByText(String datasetId, ParamDocument doc) {
        return ofDataset(true, datasetId).insertText(doc);
    }

    @Override
    public DocumentResult insertDocByText(String datasetId, ParamDocument doc, RetrievalModel retrievalModel, String embeddingModel, String embeddingModelProvider) {
        return ofDataset(true, datasetId).insertText(doc, retrievalModel, embeddingModel, embeddingModelProvider);
    }

    @Override
    public DocumentResult insertDocByFile(String datasetId, File file, ParamDocument data) {
        return ofDataset(true, datasetId).insertFile(file, data);
    }

    @Override
    public DocumentResult insertDocByFile(String datasetId, File file, ParamDocument data, RetrievalModel retrievalModel, String embeddingModel, String embeddingModelProvider) {
        return ofDataset(true, datasetId).insertFile(file, data, retrievalModel, embeddingModel, embeddingModelProvider);
    }

    @Override
    public List<BatchStatus> queryBatchStatus(String datasetId, String batch) {
        return ofDataset(true, datasetId).queryBatchStatus(batch);
    }

    @Override
    public DifyPage<Document> documents(String datasetId) {
        return ofDataset(true, datasetId).documents();
    }

    @Override
    public DifyPage<Document> documents(String datasetId, Integer page, Integer limit, String keyword) {
        return ofDataset(true, datasetId).documents(page, limit, keyword);
    }

    @Override
    public RetrieveResult retrieve(String datasetId, String query, RetrievalModel retrievalModel) {
        return ofDataset(true, datasetId).retrieve(query, retrievalModel);
    }

    @Override
    public DocumentResult updateDocByText(String datasetId, String documentId, ParamDocument data) {
        return ofDocument(true, datasetId, documentId).updateByText(data);
    }

    @Override
    public DocumentResult updateDocByFile(String datasetId, String documentId, File file, ProcessRule rule) {
        return ofDocument(true, datasetId, documentId).updateByFile(file, rule);
    }

    @Override
    public DocumentResult updateDocByFile(String datasetId, String documentId, String name, File file, ProcessRule rule) {
        return ofDocument(true, datasetId, documentId).updateByFile(name, file, rule);
    }

    @Override
    public Boolean deleteDocument(String datasetId, String documentId) {
        return ofDocument(true, datasetId, documentId).delete();
    }

    @Override
    public DocFileInfo queryFileInfo(String datasetId, String documentId) {
        return ofDocument(true, datasetId, documentId).queryFileInfo();
    }

    @Override
    public SegmentResult querySegments(String datasetId, String documentId) {
        return ofDocument(true, datasetId, documentId).querySegments();
    }

    @Override
    public SegmentResult querySegments(String datasetId, String documentId, String keyword) {
        return ofDocument(true, datasetId, documentId).querySegments(keyword);
    }

    @Override
    public SegmentResult querySegments(String datasetId, String documentId, String keyword, String status) {
        return ofDocument(true, datasetId, documentId).querySegments(keyword, status);
    }

    @Override
    public SegmentResult insertSegment(String datasetId, String documentId, List<Segment> segments) {
        return ofDocument(true, datasetId, documentId).insertSegment(segments);
    }

    @Override
    public Boolean deleteSegment(String datasetId, String documentId, String segmentId) {
        return ofDocument(true, datasetId, documentId).deleteSegment(segmentId);
    }

    @Override
    public SegmentResult updateSegment(String datasetId, String documentId, String segmentId, String content, boolean regenerate) {
        return ofDocument(true, datasetId, documentId).updateSegment(segmentId, content, regenerate);
    }

    @Override
    public SegmentResult updateSegment(String datasetId, String documentId, Segment segment, boolean regenerate) {
        return ofDocument(true, datasetId, documentId).updateSegment(segment, regenerate);
    }

}
