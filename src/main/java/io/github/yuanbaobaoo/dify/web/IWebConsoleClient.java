package io.github.yuanbaobaoo.dify.web;

import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.SimpleHttpClient;
import io.github.yuanbaobaoo.dify.types.DifyPage;

public interface IWebConsoleClient {
    /**
     * 获取一个Simple http client对象
     */
    SimpleHttpClient httpClient();

    /**
     * 获取登录的Access token
     */
    String token();

    /**
     * 查询应用列表
     * @param page int
     * @param limit int
     * @param name 名称 (可选)
     */
    DifyPage<JSONObject> queryApps(int page, int limit, String name);

}
