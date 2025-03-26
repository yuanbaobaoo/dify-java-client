package io.github.yuanbaobaoo.dify.web.impl;

import io.github.yuanbaobaoo.dify.web.IWebConsoleClient;
import io.github.yuanbaobaoo.dify.web.types.DifyLoginResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebClientImpl implements IWebConsoleClient {
    private final String server;
    private final DifyLoginResult loginResult;

    /**
     * constructor
     * @param server Dify 服务地址
     * @param result DifyLoginResult
     */
    public WebClientImpl(String server, DifyLoginResult result) {
        this.server = server;
        this.loginResult = result;
    }
}
