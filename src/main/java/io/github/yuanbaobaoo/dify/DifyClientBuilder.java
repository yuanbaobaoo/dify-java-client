package io.github.yuanbaobaoo.dify;

import io.github.yuanbaobaoo.dify.app.IDifyBaseClient;
import io.github.yuanbaobaoo.dify.app.IDifyChatClient;
import io.github.yuanbaobaoo.dify.app.IDifyCompletion;
import io.github.yuanbaobaoo.dify.app.IDifyFlowClient;
import io.github.yuanbaobaoo.dify.app.impl.BaseClientImpl;
import io.github.yuanbaobaoo.dify.app.impl.ChatClientImpl;
import io.github.yuanbaobaoo.dify.app.impl.CompletionImpl;
import io.github.yuanbaobaoo.dify.app.impl.FlowClientImpl;
import io.github.yuanbaobaoo.dify.dataset.IDatasetClient;
import io.github.yuanbaobaoo.dify.dataset.heros.DatasetHero;
import io.github.yuanbaobaoo.dify.dataset.heros.DocumentHero;
import io.github.yuanbaobaoo.dify.dataset.impl.DatasetClientImpl;

/**
 * Dify Client builder
 */
public class DifyClientBuilder {
    /**
     * create dify base client
     */
    public static Builder<IDifyBaseClient, BaseClientImpl> base() {
        return new Builder<>(BaseClientImpl.class);
    }

    /**
     * create dify chat client
     */
    public static Builder<IDifyChatClient, ChatClientImpl> chat() {
        return new Builder<>(ChatClientImpl.class);
    }

    /**
     * create dify flow client
     */
    public static Builder<IDifyFlowClient, FlowClientImpl> flow() {
        return new Builder<>(FlowClientImpl.class);
    }

    /**
     * create dify completion client
     */
    public static Builder<IDifyCompletion, CompletionImpl> completion() {
        return new Builder<>(CompletionImpl.class);
    }

    /**
     * create dify dataset client
     */
    public static DatasetBuilder dataset() {
        return new DatasetBuilder();
    }

    /**
     * builder
     * @param <T>
     */
    public static class Builder<R, T extends R> {
        private final Class<T> type;

        private String baseUrl = "http://localhost:5001";
        private String apiKey;

        /**
         * constructor
         * @param type Class<T>
         */
        public Builder(Class<T> type) {
            this.type = type;
        }

        /**
         * config
         * @param config DifyConfig
         */
        public Builder<R, T> config(DifyConfig config) {
            this.baseUrl = config.getServer();
            this.apiKey = config.getApiKey();
            return this;
        }

        /**
         * dify server base url
         * @param baseUrl String
         */
        public Builder<R, T> baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * dify app api key
         * @param apiKey String
         */
        public Builder<R, T> apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        /**
         * build
         */
        public R build() {
            if (baseUrl == null || apiKey == null) {
                throw new RuntimeException("Dify Client Build Error: params is not defined");
            }

            try {
                return type.getConstructor(String.class, String.class).newInstance(baseUrl, apiKey);
            } catch (Exception e) {
                throw new RuntimeException("Dify Client Build Error: class is not defined", e);
            }
        }
    }

    /**
     * DatasetBuilder
     */
    public static class DatasetBuilder {
        private String baseUrl = "http://localhost:5001";
        private String apiKey;

        /**
         * config
         * @param config DifyConfig
         */
        public DatasetBuilder config(DifyConfig config) {
            this.baseUrl = config.getServer();
            this.apiKey = config.getApiKey();
            return this;
        }

        /**
         * dify server base url
         * @param baseUrl String
         */
        public DatasetBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * dify app api key
         * @param apiKey String
         */
        public DatasetBuilder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        /**
         * build
         */
        public IDatasetClient build() {
            if (baseUrl == null || apiKey == null) {
                throw new RuntimeException("Dify Client Build Error: params is not defined");
            }

            try {
                return DatasetClientImpl.build(baseUrl, apiKey);
            } catch (Exception e) {
                throw new RuntimeException("Dify Client Build Error: class is not defined", e);
            }
        }

        /**
         * 快捷构建一个Dataset对象
         * @param datasetId 知识库ID
         */
        public DatasetHero of(String datasetId) {
            return DatasetHero.of(datasetId, DifyConfig.builder().server(baseUrl).apiKey(apiKey).build());
        }

        /**
         * 快捷创建一个Document对象
         * @param datasetId 知识库ID
         * @param documentId 文档ID
         */
        public DocumentHero ofDocument(String datasetId, String documentId) {
            return DocumentHero.of(datasetId, documentId, DifyConfig.builder().server(baseUrl).apiKey(apiKey).build());
        }
    }

}
