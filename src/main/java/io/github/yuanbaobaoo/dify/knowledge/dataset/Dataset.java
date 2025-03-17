package io.github.yuanbaobaoo.dify.knowledge.dataset;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import io.github.yuanbaobaoo.dify.DifyHttpClient;
import io.github.yuanbaobaoo.dify.routes.DifyRoutes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class Dataset {
    private String id;
    private String name;
    private String description;
    private String provider;
    private String permission;
    private String dataSourceType;
    private DatasetConsts.IndexingTechnique indexingTechnique;
    private Integer appCount;
    private Long documentCount;
    private Long wordCount;
    private String createdBy;
    private Integer createdAt;
    private String updatedBy;
    private Integer updatedAt;
    private String embeddingModel;
    private String embeddingModelProvider;
    private String embeddingAvailable;
    private List<Object> tags;
    private Object docForm;
    private JSONObject retrievalModelDict;
    private JSONObject externalKnowledgeInfo;
    private JSONObject externalRetrievalModel;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Api apiClient;

    /**
     * Constructor
     */
    public Dataset() {

    }

    /**
     * Constructor
     *
     * @param id     dataset id
     * @param client DatasetClient
     */
    public Dataset(String id, DifyHttpClient client) {
        this.id = id;
        this.apiClient = new Api(client);
    }

    /**
     * Get Api Client
     */
    public Api api() {
        return apiClient;
    }

    /**
     * DifyHttpClient
     * @param client DifyHttpClient
     */
    public void resetDifyClient(DifyHttpClient client) {
        this.apiClient = new Api(client);
    }

    /**
     * Document Api
     */
    public class Api {
        /**
         * Dataset Client
         */
        private final DifyHttpClient client;

        /**
         * Api constructor
         * @param client DifyHttpClient
         */
        public Api(DifyHttpClient client) {
            this.client = client;
        }

        /**
         * 通过文本创建文档
         * @param doc ParamDocument
         */
        public DatasetDoc insertText(ParamDocument doc) throws IOException, InterruptedException {
            String result = client.requestJson(
                    DifyRoutes.DATASETS_DOC_CREATE_TEXT.format(new HashMap<>(){{
                        put("datasetId", id);
                    }}),
                    null,
                    doc
            );

            System.out.println(result);
            return JSON.parseObject(result, DatasetDoc.class);
        }

        /**
         * 通过文件创建文档
         */
        public void insertFile() {

        }

        /**
         * 获取知识库文档列表
         */
        public void documents() {

        }

        /**
         * 检索知识库
         */
        public void retrieve() {

        }
    }

}
