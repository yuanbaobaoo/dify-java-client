package org.yuanbaobaoo.dify.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.yuanbaobaoo.dify.routes.HttpMethod;

@Getter
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

}
