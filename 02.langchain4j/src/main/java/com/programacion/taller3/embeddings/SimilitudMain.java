package com.programacion.taller3.embeddings;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;

public class SimilitudMain {
    static void main() {
        AllMiniLmL6V2EmbeddingModel model = new AllMiniLmL6V2EmbeddingModel();
        Embedding e1 = model.embed("Que hora es?").content();
        Embedding e2 = model.embed("Obtiene la fecha y hora actual").content();
        Embedding e3 = model.embed("Calcula el area de un cirulo dado su radio").content();

        //Calcula la similitud coseno
        double sim12 = CosineSimilarity.between(e1, e2);
        double sim13 = CosineSimilarity.between(e1, e3);

        System.out.println("perros vs caninos: "+sim12);
        System.out.println("perro vs clima: "+sim13);

    }
}
