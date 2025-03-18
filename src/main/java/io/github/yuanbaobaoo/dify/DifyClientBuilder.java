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
    public static DatasetBuilder<IDatasetClient, DatasetClientImpl> dataset() {
        return new DatasetBuilder<>(DatasetClientImpl.class);
    }

    /**
     * builder
     * @param <T>
     */
    public static class Builder<R, T extends R> {
        private final Class<T> type;

        protected String baseUrl = "http://localhost:5001";
        protected String apiKey;

        /**
         * constructor
         * @param type Class<T>
         */
        public Builder(Class<T> type) {
            this.type = type;
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
     * @param <T>
     */
    public static class DatasetBuilder<R, T extends R> extends Builder<R, T> {
        /**
         * constructor
         *
         * @param type Class<T>
         */
        public DatasetBuilder(Class<T> type) {
            super(type);
        }

        /**
         * 快捷构建一个Dataset对象
         * @param datasetId 知识库ID
         */
        public DatasetHero ofDataset(String datasetId) {
            return new DatasetHero(datasetId, new DifyHttpClient(baseUrl, apiKey));
        }

        /**
         * 快捷创建一个Document对象
         * @param documentId 文档ID
         */
        public DocumentHero ofDocument(String datasetId, String documentId) {
            return new DocumentHero(datasetId, documentId, new DifyHttpClient(baseUrl, apiKey));
        }
    }

}
