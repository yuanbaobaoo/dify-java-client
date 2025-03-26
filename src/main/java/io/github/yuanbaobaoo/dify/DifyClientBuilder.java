package io.github.yuanbaobaoo.dify;

import io.github.yuanbaobaoo.dify.utils.DifyApiConfig;
import io.github.yuanbaobaoo.dify.utils.DifyWebConfig;

/**
 * The Dify Api Client Builder.
 * @link <a href="https://github.com/yuanbaobaoo/dify-java-client">https://github.com/yuanbaobaoo/dify-java-client</a>
 */
public class DifyClientBuilder {
    /**
     * get a app client builder.
     * DifyAppClient主要用于调用 dify app 的 open api 接口
     */
    public static DifyAppClientBuilder app() {
        return DifyAppClientBuilder.builder();
    }

    /**
     * get a app client builder
     * @param config DifyApiConfig
     */
    public static DifyAppClientBuilder app(DifyApiConfig config) {
        return DifyAppClientBuilder.builder(config.getServer(), config.getApiKey());
    }

    /**
     * get a app client builder
     * @param server Dify 服务地址
     * @param apiKey App Api Key
     */
    public static DifyAppClientBuilder app(String server, String apiKey) {
        return DifyAppClientBuilder.builder(server, apiKey);
    }

    /**
     * get a dataset client builder
     * DifyDatasetClient主要用于调用 dify 内部知识库 的 open api 接口
     * @return DifyDatasetBuilder
     */
    public static DifyDatasetBuilder dataset() {
        return DifyDatasetBuilder.builder();
    }

    /**
     * get a dataset client builder
     * DifyDatasetClient主要用于调用 dify 内部知识库 的 open api 接口
     * @param server Dify 服务地址
     * @param apiKey 知识库API
     */
    public static DifyDatasetBuilder dataset(String server, String apiKey) {
        return DifyDatasetBuilder.builder(server, apiKey);
    }

    /**
     * get a dataset client builder
     * DifyDatasetClient主要用于调用 dify 内部知识库 的 open api 接口
     * @param apiConfig DifyApiConfig
     */
    public static DifyDatasetBuilder dataset(DifyApiConfig apiConfig) {
        return DifyDatasetBuilder.builder(apiConfig.getServer(), apiConfig.getApiKey());
    }

    /**
     * get a web client builder
     * DifyWebClient是一种以API形式管理 Dify 各种功能的尝试. 例如创建应用、编辑应用等等
     */
    public static DifyWebClientBuilder web() {
        return DifyWebClientBuilder.builder();
    }

    /**
     * get a web client builder
     * DifyWebClient是一种以API形式管理 Dify 各种功能的尝试. 例如创建应用、编辑应用等等
     * @param server Dify 服务地址
     * @param userName 用户名
     * @param password 密码
     */
    public static DifyWebClientBuilder web(String server, String userName, String password) {
        return DifyWebClientBuilder.builder(server, userName, password);
    }

    /**
     * get a web client builder.
     * DifyWebClient是一种以API形式管理 Dify 各种功能的尝试. 例如创建应用、编辑应用等等
     * @param config DifyWebConfig
     */
    public static DifyWebClientBuilder web(DifyWebConfig config) {
        return DifyWebClientBuilder.builder(config.getServer(), config.getUserName(), config.getPassword());
    }

}
