package io.github.yuanbaobaoo.dify;

import io.github.yuanbaobaoo.dify.types.ApiConfig;
import io.github.yuanbaobaoo.dify.types.WebConfig;
import io.github.yuanbaobaoo.dify.utils.AppClientBuilder;
import io.github.yuanbaobaoo.dify.utils.DatasetBuilder;
import io.github.yuanbaobaoo.dify.utils.WebClientBuilder;

/**
 * The Dify Api Client Builder.
 * @link <a href="https://github.com/yuanbaobaoo/dify-java-client">https://github.com/yuanbaobaoo/dify-java-client</a>
 */
public class DifyClientBuilder {
    /**
     * get a app client builder.
     * DifyAppClient主要用于调用 dify app 的 open api 接口
     */
    public static AppClientBuilder app() {
        return AppClientBuilder.builder();
    }

    /**
     * get a app client builder
     * @param config DifyApiConfig
     */
    public static AppClientBuilder app(ApiConfig config) {
        return AppClientBuilder.builder(config.getServer(), config.getApiKey());
    }

    /**
     * get a app client builder
     * @param server Dify 服务地址
     * @param apiKey App Api Key
     */
    public static AppClientBuilder app(String server, String apiKey) {
        return AppClientBuilder.builder(server, apiKey);
    }

    /**
     * get a dataset client builder
     * DifyDatasetClient主要用于调用 dify 内部知识库 的 open api 接口
     * @return DifyDatasetBuilder
     */
    public static DatasetBuilder dataset() {
        return DatasetBuilder.builder();
    }

    /**
     * get a dataset client builder
     * DifyDatasetClient主要用于调用 dify 内部知识库 的 open api 接口
     * @param server Dify 服务地址
     * @param apiKey 知识库API
     */
    public static DatasetBuilder dataset(String server, String apiKey) {
        return DatasetBuilder.builder(server, apiKey);
    }

    /**
     * get a dataset client builder
     * DifyDatasetClient主要用于调用 dify 内部知识库 的 open api 接口
     * @param apiConfig DifyApiConfig
     */
    public static DatasetBuilder dataset(ApiConfig apiConfig) {
        return DatasetBuilder.builder(apiConfig.getServer(), apiConfig.getApiKey());
    }

    /**
     * get a web client builder
     * DifyWebClient是一种以API形式管理 Dify 各种功能的尝试. 例如创建应用、编辑应用等等
     */
    public static WebClientBuilder web() {
        return WebClientBuilder.builder();
    }

    /**
     * get a web client builder
     * DifyWebClient是一种以API形式管理 Dify 各种功能的尝试. 例如创建应用、编辑应用等等
     * @param server Dify 服务地址
     * @param userName 用户名
     * @param password 密码
     */
    public static WebClientBuilder web(String server, String userName, String password) {
        return WebClientBuilder.builder(server, userName, password);
    }

    /**
     * get a web client builder.
     * DifyWebClient是一种以API形式管理 Dify 各种功能的尝试. 例如创建应用、编辑应用等等
     * @param config DifyWebConfig
     */
    public static WebClientBuilder web(WebConfig config) {
        return WebClientBuilder.builder(config.getServer(), config.getUserName(), config.getPassword());
    }

}
