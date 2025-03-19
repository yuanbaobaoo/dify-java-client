package io.github.yuanbaobaoo.dify.dataset;

import io.github.yuanbaobaoo.dify.dataset.types.external.KnowledgeArgs;
import io.github.yuanbaobaoo.dify.dataset.types.external.KnowledgeResult;

public interface IKnowledgeService {
    /**
     * 知识检索
     * @param apiKey Dify传递的API KEY
     * @param args KnowledgeArgs
     */
    KnowledgeResult retrieval(String apiKey, KnowledgeArgs args);

}
