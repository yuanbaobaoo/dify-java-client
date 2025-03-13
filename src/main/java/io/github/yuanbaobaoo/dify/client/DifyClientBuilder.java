package io.github.yuanbaobaoo.dify.client;

import io.github.yuanbaobaoo.dify.client.impl.BaseClientImpl;
import io.github.yuanbaobaoo.dify.client.impl.ChatClientImpl;
import io.github.yuanbaobaoo.dify.client.impl.CompletionImpl;
import io.github.yuanbaobaoo.dify.client.impl.FlowClientImpl;

/**
 * Dify Client builder
 */
public class DifyClientBuilder {
    /**
     * create dify base client
     */
    public static Builder<IDifyBaseClient, BaseClientImpl> create() {
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
