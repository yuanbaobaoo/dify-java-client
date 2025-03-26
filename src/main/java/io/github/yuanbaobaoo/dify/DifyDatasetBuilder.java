package io.github.yuanbaobaoo.dify;

import io.github.yuanbaobaoo.dify.dataset.IDatasetClient;
import io.github.yuanbaobaoo.dify.dataset.heros.DatasetHero;
import io.github.yuanbaobaoo.dify.dataset.heros.DocumentHero;
import io.github.yuanbaobaoo.dify.dataset.impl.DatasetClientImpl;
import io.github.yuanbaobaoo.dify.utils.DifyApiConfig;

/**
 * Dify 知识库 builder
 */
public class DifyDatasetBuilder {
    private String apiKey;
    private String baseUrl;

    /**
     * builder
     */
    public static DifyDatasetBuilder builder() {
        return new DifyDatasetBuilder();
    }

    /**
     * builder
     * @param server Dify 服务地址
     * @param apiKey 知识库 Api Key
     */
    public static DifyDatasetBuilder builder(String server, String apiKey) {
        return new DifyDatasetBuilder(server, apiKey);
    }

    /**
     * constructor
     */
    private DifyDatasetBuilder() {

    }

    /**
     * constructor
     * @param server Dify 服务地址
     * @param apiKey 知识库 Api Key
     */
    private DifyDatasetBuilder(String server, String apiKey) {
        this.apiKey = apiKey;
        this.baseUrl = server;
    }

    /**
     * config
     * @param config DifyConfig
     */
    public DifyDatasetBuilder config(DifyApiConfig config) {
        this.baseUrl = config.getServer();
        this.apiKey = config.getApiKey();
        return this;
    }

    /**
     * dify server base url
     * @param baseUrl String
     */
    public DifyDatasetBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * dify app api key
     * @param apiKey String
     */
    public DifyDatasetBuilder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     * build
     */
    public IDatasetClient build() {
        if (baseUrl == null || apiKey == null) {
            throw new RuntimeException("Dify Dataset Client Build Error: params is not defined");
        }

        try {
            return DatasetClientImpl.build(baseUrl, apiKey);
        } catch (Exception e) {
            throw new RuntimeException("Dify Dataset Client Build Error: class is not defined", e);
        }
    }

    /**
     * 快捷构建一个Dataset对象
     * @param datasetId 知识库ID
     */
    public DatasetHero of(String datasetId) {
        return DatasetHero.of(datasetId, DifyApiConfig.builder().server(baseUrl).apiKey(apiKey).build());
    }

    /**
     * 快捷创建一个Document对象
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     */
    public DocumentHero ofDocument(String datasetId, String documentId) {
        return DocumentHero.of(datasetId, documentId, DifyApiConfig.builder().server(baseUrl).apiKey(apiKey).build());
    }

}
