package io.github.yuanbaobaoo.dify;

import io.github.yuanbaobaoo.dify.web.IWebConsoleClient;
import io.github.yuanbaobaoo.dify.web.impl.WebClientImpl;
import io.github.yuanbaobaoo.dify.web.types.DifyLoginResult;

/**
 * Dify Web Client Builder
 */
public class DifyWebClientBuilder {
    private String server;
    private String userName;
    private String password;

    /**
     * builder
     */
    public static DifyWebClientBuilder builder() {
        return new DifyWebClientBuilder();
    }

    /**
     * builder
     * @param server Dify 服务地址
     * @param userName 用户名
     * @param password 账号密码
     */
    public static DifyWebClientBuilder builder(String server, String userName, String password) {
        return new DifyWebClientBuilder(server, userName, password);
    }

    /**
     * constructor
     */
    private DifyWebClientBuilder() {

    }

    /**
     * constructor
     * @param server Dify 服务地址
     * @param userName 用户名
     * @param password 账号密码
     */
    private DifyWebClientBuilder(String server, String userName, String password) {
        this.server = server;
        this.userName = userName;
        this.password = password;
    }

    /**
     * dify server base url
     * @param baseUrl String
     */
    public DifyWebClientBuilder baseUrl(String baseUrl) {
        this.server = baseUrl;
        return this;
    }

    /**
     * dify login user name
     * @param userName String
     */
    public DifyWebClientBuilder user(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * dify login password
     * @param password String
     */
    public DifyWebClientBuilder password(String password) {
        this.password = password;
        return this;
    }

    /**
     * build.
     * 创建IWebClient对象时，会默认执行登录
     */
    public IWebConsoleClient build() {
        if (server == null || userName == null || password == null) {
            throw new RuntimeException("Dify Web Client Build Error: params is not defined");
        }

        try {
            return new WebClientImpl(server, DifyLoginResult.builder().build());
        } catch (Exception e) {
            throw new RuntimeException("Dify Web Client Build Error: class is not defined", e);
        }
    }


}
