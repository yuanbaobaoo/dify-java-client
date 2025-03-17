package io.github.yuanbaobaoo.dify.types;

import io.github.yuanbaobaoo.dify.routes.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

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
     * Replace url placeholder. 替换URL模板参数
     *
     * @param params Map<String, Object>
     */
    public DifyRoute format(Map<String, Object> params) {
        url = StringSubstitutor.replace(url, params);
        return this;
    }

    /**
     * build get route info
     *
     * @param url API URL
     */
    public static DifyRoute buildGet(String url) {
        return DifyRoute.builder().url(url).method(HttpMethod.GET).build();
    }

    /**
     * build post route info
     *
     * @param url API URL
     */
    public static DifyRoute buildPost(String url) {
        return DifyRoute.builder().url(url).method(HttpMethod.POST).build();
    }

    /**
     * build delete route info
     *
     * @param url API URL
     */
    public static DifyRoute buildDelete(String url) {
        return DifyRoute.builder().url(url).method(HttpMethod.DELETE).build();
    }

}
