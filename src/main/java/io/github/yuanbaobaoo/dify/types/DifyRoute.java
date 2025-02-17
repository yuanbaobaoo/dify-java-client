package io.github.yuanbaobaoo.dify.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import io.github.yuanbaobaoo.dify.routes.HttpMethod;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DifyRoute {
    /**
     * API URL
     */
    private String url;

    /**
     * HTTP METHOD
     */
    private HttpMethod method;

    /**
     * build get route info
     * @param url API URL
     */
    public static DifyRoute buildGet(String url) {
        return DifyRoute.builder().url(url).method(HttpMethod.GET).build();
    }

    /**
     *  build post route info
     * @param url API URL
     */
    public static DifyRoute buildPost(String url) {
        return DifyRoute.builder().url(url).method(HttpMethod.POST).build();
    }

    /**
     * build delete route info
     * @param url API URL
     */
    public static DifyRoute buildDelete(String url) {
        return DifyRoute.builder().url(url).method(HttpMethod.DELETE).build();
    }

}
