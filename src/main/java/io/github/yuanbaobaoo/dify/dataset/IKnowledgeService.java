package io.github.yuanbaobaoo.dify.dataset;

import io.github.yuanbaobaoo.dify.dataset.external.KnowledgeArgs;
import io.github.yuanbaobaoo.dify.dataset.external.KnowledgeResult;

public interface IKnowledgeService {
    /**
     * 知识检索
     * @param apiKey Dify传递的API KEY
     * @param args KnowledgeArgs
     */
    KnowledgeResult retrieval(String apiKey, KnowledgeArgs args);

}
