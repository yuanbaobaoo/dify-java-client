package io.github.yuanbaobaoo.dify.client;

import io.github.yuanbaobaoo.dify.client.impl.DifyBaseClientImpl;
import io.github.yuanbaobaoo.dify.client.impl.DifyChatClientImpl;
import io.github.yuanbaobaoo.dify.client.impl.DifyCompletionImpl;
import io.github.yuanbaobaoo.dify.client.impl.DifyFlowClientImpl;

/**
 * Dify Client builder
 */
public class DifyClientBuilder {
    /**
     * create dify base client
     */
    public static Builder<IDifyBaseClient, DifyBaseClientImpl> create() {
        return new Builder<>(DifyBaseClientImpl.class);
    }

    /**
     * create dify chat client
     */
    public static Builder<IDifyChatClient, DifyChatClientImpl> chat() {
        return new Builder<>(DifyChatClientImpl.class);
    }

    /**
     * create dify flow client
     */
    public static Builder<IDifyFlowClient, DifyFlowClientImpl> flow() {
        return new Builder<>(DifyFlowClientImpl.class);
    }

    /**
     * create dify completion client
     */
    public static Builder<IDifyCompletion, DifyCompletionImpl> completion() {
        return new Builder<>(DifyCompletionImpl.class);
    }

    /**
     * builder
     * @param <T>
     */
    public static class Builder<R, T extends R> {
        private String baseUrl = "http://localhost:5001";
        private String apiKey;
        private final Class<T> type;

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

}
