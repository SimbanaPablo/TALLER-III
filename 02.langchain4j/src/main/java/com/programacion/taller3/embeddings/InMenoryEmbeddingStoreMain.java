package com.programacion.taller3.embeddings;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.util.List;
import java.util.stream.Stream;

public class InMenoryEmbeddingStoreMain {
    static void main() {
        var documentos = List.of(
                "Java is an object-oriented programming language",
                "Python is a popular for artificial intelligence",
                "The coffee machine is in the kitchen",
                "Embeddings represent text as vectors",
                "Minecraft is the best game of the world"
        );

        var model = new AllMiniLmL6V2EmbeddingModel();

        //Crear store en memoria
        InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();

        documentos.stream()
                .forEach(it -> {
                    String key = ("doc-" + (store.size() + 1));
                    var embedding = model.embed(it);
                    store.add(key, embedding.content(), TextSegment.from(it));
                });

        String consulta = "object oriented programming";

        var SearchQuery = EmbeddingSearchRequest.builder()
                .queryEmbedding(model.embed(consulta).content())
                .maxResults(2)
                .build();

        EmbeddingSearchResult<TextSegment> searchResult = store.search(SearchQuery);
        for (var it : searchResult.matches()) {
//            System.out.println(it);
            System.out.printf("Score: %.3f - %s\n", it.score(), it.embedded().text());
        }
    }
}
