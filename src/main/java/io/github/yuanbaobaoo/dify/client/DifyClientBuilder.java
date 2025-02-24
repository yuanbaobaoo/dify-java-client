package io.github.yuanbaobaoo.dify.client;

import io.github.yuanbaobaoo.dify.client.impl.DifyBaseClientImpl;
import io.github.yuanbaobaoo.dify.client.impl.DifyChatClientImpl;
import io.github.yuanbaobaoo.dify.client.impl.DifyWorkFlowClientImpl;

/**
 * Dify Client builder
 */
public class DifyClientBuilder {
    private String baseUrl = "http://localhost:5001";
    private String apiKey;

    /**
     * create
     * @return DifyClientBuilder
     */
    public static DifyClientBuilder create() {
        return new DifyClientBuilder();
    }

    /**
     * dify server base url
     * @param baseUrl String
     */
    public DifyClientBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * dify app api key
     * @param apiKey String
     */
    public DifyClientBuilder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     * build DifyBaseClient
     * @return IDifyClient
     */
    public IDifyClient build() {
        if (baseUrl == null || apiKey == null) {
            throw new RuntimeException("Dify Client Build Error: params is not defined");
        }

        return new DifyBaseClientImpl(baseUrl, apiKey);
    }

    /**
     * build DifyChatClient
     * @return IDifyChatClient
     */
    public IDifyChatClient buildChat() {
        if (baseUrl == null || apiKey == null) {
            throw new RuntimeException("Dify Client Build Error: params is not defined");
        }

        return new DifyChatClientImpl(baseUrl, apiKey);
    }

    /**
     * build DifyWorkFlowClient
     * @return IDifyWorkFlowClient
     */
    public IDifyWorkFlowClient buildWorkFlow() {
        if (baseUrl == null || apiKey == null) {
            throw new RuntimeException("Dify Client Build Error: params is not defined");
        }

        return new DifyWorkFlowClientImpl(baseUrl, apiKey);
    }


}
