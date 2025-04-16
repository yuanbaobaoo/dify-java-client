package io.github.yuanbaobaoo.dify.routes;

import io.github.yuanbaobaoo.dify.types.DifyRoute;
import io.github.yuanbaobaoo.dify.types.HttpMethod;

public class DatasetRoutes {
    public static final DifyRoute MODELS_TEXT_EMBEDDING = new DifyRoute(
            "/v1/workspaces/current/models/model-types/text-embedding",
            HttpMethod.GET
    );

    public static final DifyRoute DATASETS = new DifyRoute(
            "/v1/datasets",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_INFO = new DifyRoute(
            "/v1/datasets/${datasetId}",
            HttpMethod.GET
    );

    public static final DifyRoute DATASETS_UPDATE = new DifyRoute(
            "/v1/datasets/${datasetId}",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DELETE = new DifyRoute(
            "/v1/datasets/${datasetId}",
            HttpMethod.DELETE
    );

    public static final DifyRoute DATASETS_RETRIEVE = new DifyRoute(
            "/v1/datasets/${datasetId}/retrieve",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_CREATE_DOC_TEXT = new DifyRoute(
            "/v1/datasets/${datasetId}/document/create-by-text",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_CREATE_DOC_FILE = new DifyRoute(
            "/v1/datasets/${datasetId}/document/create-by-file",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS = new DifyRoute(
            "/v1/datasets/${datasetId}/documents",
            HttpMethod.GET
    );

    public static final DifyRoute DATASETS_INDEXING_STATUS = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${batch}/indexing-status",
            HttpMethod.GET
    );

    public static final DifyRoute DATASETS_DOCS_UPDATE_TEXT = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}/update-by-text",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS_UPDATE_FILE = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}/update-by-file",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS_DELETE = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}",
            HttpMethod.DELETE
    );

    public static final DifyRoute DATASETS_DOCS_FILE_INFO = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}/upload-file",
            HttpMethod.GET
    );


    public static final DifyRoute DATASETS_DOCS_SEGMENTS = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}/segments",
            HttpMethod.GET
    );

    public static final DifyRoute DATASETS_DOCS_SEGMENTS_ADD = new DifyRoute(
            DATASETS_DOCS_SEGMENTS.getUrl(),
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS_SEGMENTS_DEL = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}/segments/${segmentId}",
            HttpMethod.DELETE
    );

    public static final DifyRoute DATASETS_DOCS_SEGMENTS_UPDATE = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}/segments/${segmentId}",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS_SEGMENTS_CHILD_CHUNKS_GET = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}/segments/${segmentId}/child_chunks",
            HttpMethod.GET
    );

    public static final DifyRoute DATASETS_DOCS_SEGMENTS_CHILD_CHUNKS_ADD = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}/segments/${segmentId}/child_chunks",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS_SEGMENTS_CHILD_CHUNKS_DEL = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}/segments/${segmentId}/child_chunks/${childChunkId}",
            HttpMethod.POST
    );

    public static final DifyRoute DATASETS_DOCS_SEGMENTS_CHILD_CHUNKS_SET = new DifyRoute(
            "/v1/datasets/${datasetId}/documents/${documentId}/segments/${segmentId}/child_chunks/${childChunkId}",
            HttpMethod.PATCH
    );

}
