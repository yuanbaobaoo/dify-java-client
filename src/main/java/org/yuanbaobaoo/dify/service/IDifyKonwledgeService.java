package org.yuanbaobaoo.dify.service;

import org.yuanbaobaoo.dify.types.KnowledgeArgs;
import org.yuanbaobaoo.dify.types.KnowledgeResult;

public interface IDifyKonwledgeService {
    /**
     * 知识检索
     * @param apiKey Dify传递的API KEY
     * @param args KownledgeArgs
     */
    KnowledgeResult retrieval(String apiKey, KnowledgeArgs args);

}
