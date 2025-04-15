package io.github.yuanbaobaoo.dify.web;

import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.types.DifyPage;
import io.github.yuanbaobaoo.dify.types.DifyRoute;

import java.util.Map;

public interface IWebConsoleClient {
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

    /**
     * TODO 请求自定义地址
     * @param route DifyRoute
     * @param params query params
     * @param body body params
     */
    JSONObject request(DifyRoute route, Map<String, Object> params, Map<String, Object> body);
}
