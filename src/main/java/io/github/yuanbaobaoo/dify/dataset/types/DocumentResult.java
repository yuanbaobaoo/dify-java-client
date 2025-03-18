package io.github.yuanbaobaoo.dify.dataset.types;

import io.github.yuanbaobaoo.dify.dataset.heros.DocumentHero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResult {
    private String batch;
    private DocumentHero document;
}
