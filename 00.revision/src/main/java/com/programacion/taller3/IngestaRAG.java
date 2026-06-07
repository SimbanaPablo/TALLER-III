package com.programacion.taller3;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.util.List;

public class IngestaRAG {
    static void main() {
        var model = new AllMiniLmL6V2EmbeddingModel();
        InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();

        var documentos = List.of(
                "Artículo 1: Toda persona tiene derecho a la educación... ",
                        "Artículo 2: Se prohíbe el fraude financiero... ",
                        "Artículo 3: Las penas por evasión fiscal superan los 5 años."
        );

        documentos.stream()
                .forEach(it -> {
                    String key = ("doc-" + (store.size() + 1));
                    var embedding = model.embed(it);
                    store.add(key, embedding.content(), TextSegment.from(it));
                });
    }
}
