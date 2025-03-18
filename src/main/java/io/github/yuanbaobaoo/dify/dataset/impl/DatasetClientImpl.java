package io.github.yuanbaobaoo.dify.dataset.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import io.github.yuanbaobaoo.dify.dataset.IDatasetClient;
import io.github.yuanbaobaoo.dify.dataset.entity.*;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDocument;
import io.github.yuanbaobaoo.dify.dataset.types.BatchStatus;
import io.github.yuanbaobaoo.dify.dataset.types.DocumentResult;
import io.github.yuanbaobaoo.dify.dataset.types.ProcessRule;
import io.github.yuanbaobaoo.dify.dataset.types.RetrievalModel;
import io.github.yuanbaobaoo.dify.types.DifyPage;
import io.github.yuanbaobaoo.dify.dataset.params.ParamDataset;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.app.routes.AppRoutes;
import io.github.yuanbaobaoo.dify.dataset.heros.DatasetHero;
import io.github.yuanbaobaoo.dify.dataset.heros.DocumentHero;
import io.github.yuanbaobaoo.dify.types.HttpMethod;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class DatasetClientImpl implements IDatasetClient {
    private final DifyHttpClient client;

    /**
     * constructor
     * @param server Dify Server Address
     * @param apiKey API KEY
     */
    public DatasetClientImpl(String server, String apiKey) {
        client = new DifyHttpClient(server, apiKey);
    }

    @Override
    public DatasetHero ofDataset(String datasetId) {
        return new DatasetHero(datasetId, this.client);
    }

    @Override
    public DocumentHero ofDocument(String datasetId, String documentId) {
        return new DocumentHero(datasetId, documentId, this.client);
    }

    @Override
    public DatasetHero create(ParamDataset data) {
        String result = client.requestJson(AppRoutes.DATASETS, null, data);

        DatasetHero dataset = JSON.parseObject(result, DatasetHero.class);
        dataset.resetDifyClient(this.client);

        return dataset;
    }

    @Override
    public DifyPage<Dataset> list(int page, int limit) {
        String result = client.requestJson(AppRoutes.DATASETS.getUrl(), HttpMethod.GET, new HashMap<>() {{
            put("page", page);
            put("limit", limit);
        }}, null);

        return JSON.parseObject(result, new TypeReference<DifyPage<Dataset>>() {});
    }

    @Override
    public void delete(String datasetId) {
        ofDataset(datasetId).delete();
    }

    @Override
    public DocumentResult insertDocByText(String datasetId, ParamDocument doc) {
        return ofDataset(datasetId).insertText(doc);
    }

    @Override
    public DocumentResult insertDocByText(String datasetId, ParamDocument doc, RetrievalModel retrievalModel, String embeddingModel, String embeddingModelProvider) {
        return ofDataset(datasetId).insertText(doc, retrievalModel, embeddingModel, embeddingModelProvider);
    }

    @Override
    public DocumentResult insertDocByFile(String datasetId, File file, ParamDocument data) {
        return ofDataset(datasetId).insertFile(file, data);
    }

    @Override
    public DocumentResult insertDocByFile(String datasetId, File file, ParamDocument data, RetrievalModel retrievalModel, String embeddingModel, String embeddingModelProvider) {
        return ofDataset(datasetId).insertFile(file, data, retrievalModel, embeddingModel, embeddingModelProvider);
    }

    @Override
    public List<BatchStatus> queryBatchStatus(String datasetId, String batch) {
        return ofDataset(datasetId).queryBatchStatus(batch);
    }

    @Override
    public DifyPage<Document> documents(String datasetId) {
        return ofDataset(datasetId).documents();
    }

    @Override
    public DifyPage<Document> documents(String datasetId, Integer page, Integer limit, String keyword) {
        return ofDataset(datasetId).documents(page, limit, keyword);
    }

    @Override
    public RetrieveResult retrieve(String datasetId, String query, RetrievalModel retrievalModel) {
        return ofDataset(datasetId).retrieve(query, retrievalModel);
    }

    @Override
    public DocumentResult updateDocByText(String datasetId, String documentId, ParamDocument data) {
        return ofDocument(datasetId, documentId).updateByText(data);
    }

    @Override
    public DocumentResult updateDocByFile(String datasetId, String documentId, File file, ProcessRule rule) {
        return ofDocument(datasetId, documentId).updateByFile(file, rule);
    }

    @Override
    public DocumentResult updateDocByFile(String datasetId, String documentId, String name, File file, ProcessRule rule) {
        return ofDocument(datasetId, documentId).updateByFile(name, file, rule);
    }

    @Override
    public Boolean deleteDocument(String datasetId, String documentId) {
        return ofDocument(datasetId, documentId).delete();
    }

    @Override
    public DocFileInfo queryFileInfo(String datasetId, String documentId) {
        return ofDocument(datasetId, documentId).queryFileInfo();
    }

    @Override
    public SegmentResult querySegments(String datasetId, String documentId) {
        return ofDocument(datasetId, documentId).querySegments();
    }

    @Override
    public SegmentResult querySegments(String datasetId, String documentId, String keyword) {
        return ofDocument(datasetId, documentId).querySegments(keyword);
    }

    @Override
    public SegmentResult querySegments(String datasetId, String documentId, String keyword, String status) {
        return ofDocument(datasetId, documentId).querySegments(keyword, status);
    }

    @Override
    public SegmentResult insertSegment(String datasetId, String documentId, List<Segment> segments) {
        return ofDocument(datasetId, documentId).insertSegment(segments);
    }

    @Override
    public Boolean deleteSegment(String datasetId, String documentId, String segmentId) {
        return ofDocument(datasetId, documentId).deleteSegment(segmentId);
    }

    @Override
    public SegmentResult updateSegment(String datasetId, String documentId, String segmentId, String content, boolean regenerate) {
        return ofDocument(datasetId, documentId).updateSegment(segmentId, content, regenerate);
    }

    @Override
    public SegmentResult updateSegment(String datasetId, String documentId, Segment segment, boolean regenerate) {
        return ofDocument(datasetId, documentId).updateSegment(segment, regenerate);
    }

}
