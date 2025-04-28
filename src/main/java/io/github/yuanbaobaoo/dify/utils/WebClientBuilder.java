package io.github.yuanbaobaoo.dify.utils;

import io.github.yuanbaobaoo.dify.types.DifyClientException;
import io.github.yuanbaobaoo.dify.types.DifyException;
import io.github.yuanbaobaoo.dify.types.WebConfig;
import io.github.yuanbaobaoo.dify.web.WebAuthService;
import io.github.yuanbaobaoo.dify.web.IWebConsoleClient;
import io.github.yuanbaobaoo.dify.web.entity.TokenList;
import io.github.yuanbaobaoo.dify.web.impl.WebClientImpl;

/**
 * Dify Web Client Builder
 */
public class WebClientBuilder {
    private String server;
    private String userName;
    private String password;

    /**
     * builder
     */
    public static WebClientBuilder builder() {
        return new WebClientBuilder();
    }

    /**
     * builder
     * @param server Dify 服务地址
     * @param userName 用户名
     * @param password 账号密码
     */
    public static WebClientBuilder builder(String server, String userName, String password) {
        return new WebClientBuilder(server, userName, password);
    }

    /**
     * constructor
     */
    private WebClientBuilder() {

    }

    /**
     * constructor
     * @param server Dify 服务地址
     * @param userName 用户名
     * @param password 账号密码
     */
    private WebClientBuilder(String server, String userName, String password) {
        this.server = server;
        this.userName = userName;
        this.password = password;
    }

    /**
     * dify server base url
     * @param baseUrl String
     */
    public WebClientBuilder baseUrl(String baseUrl) {
        this.server = baseUrl;
        return this;
    }

    /**
     * dify login user name
     * @param userName String
     */
    public WebClientBuilder user(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * dify login password
     * @param password String
     */
    public WebClientBuilder password(String password) {
        this.password = password;
        return this;
    }

    /**
     * build.
     * 创建IWebClient对象时，会默认执行登录
     */
    public IWebConsoleClient connect() {
        if (server == null || userName == null || password == null) {
            throw new RuntimeException("Dify Web Client Connect Error: params is not defined");
        }

        try {
            WebConfig config = WebConfig.builder().server(server).userName(userName).password(password).build();
            TokenList tokenList = WebAuthService.login(config);
            return new WebClientImpl(server, tokenList);
        } catch (DifyClientException | DifyException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Dify Web Client Connect Error: ", e);
        }
    }


}
