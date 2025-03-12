package io.github.yuanbaobaoo.dify.service;

import io.github.yuanbaobaoo.dify.types.KnowledgeArgs;
import io.github.yuanbaobaoo.dify.types.KnowledgeResult;

public interface IDifyKnowledgeService {
    /**
     * 知识检索
     * @param apiKey Dify传递的API KEY
     * @param args KnowledgeArgs
     */
    KnowledgeResult retrieval(String apiKey, KnowledgeArgs args);

}
