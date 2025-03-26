package io.github.yuanbaobaoo.dify.routes;

import io.github.yuanbaobaoo.dify.types.DifyRoute;
import io.github.yuanbaobaoo.dify.types.HttpMethod;

public class ConsoleRoutes {
    // 登录
    public static final DifyRoute LOGIN = new DifyRoute("/console/api/login", HttpMethod.POST);


}
