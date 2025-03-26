package io.github.yuanbaobaoo.dify.dataset.types;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.yuanbaobaoo.dify.types.ApiConfig;
import io.github.yuanbaobaoo.dify.dataset.entity.Document;
import io.github.yuanbaobaoo.dify.dataset.heros.DocumentHero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResult {
    private String batch;
    private DocumentHero document;

    /**
     * 使用JSON字符串创建一个 DocumentResult 对象
     * @param result String
     */
    public static DocumentResult parse(String datasetId, String result, ApiConfig config) {
        JSONObject jsonObject = JSON.parseObject(result);

        DocumentResult document = new DocumentResult();
        document.setBatch(jsonObject.getString("batch"));
        document.setDocument(DocumentHero.of(
                datasetId,
                JSON.parseObject(jsonObject.getString("document"), Document.class),
                config
        ));

        return document;
    }

}
