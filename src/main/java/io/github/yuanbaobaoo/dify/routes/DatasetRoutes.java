package io.github.yuanbaobaoo.dify.routes;

import io.github.yuanbaobaoo.dify.types.DifyRoute;
import io.github.yuanbaobaoo.dify.types.HttpMethod;

public class DatasetRoutes {
    /**
     * Desc: Datasets
     * Type: Dataset
     */
    public static final DifyRoute DATASETS = new DifyRoute("/v1/datasets", HttpMethod.POST);

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

}
