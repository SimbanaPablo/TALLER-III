package com.programacion.taller3.embeddings;



import dev.langchain4j.data.embedding.Embedding;

import dev.langchain4j.model.embedding.onnx.OnnxEmbeddingModel;
import dev.langchain4j.model.embedding.onnx.PoolingMode;
import dev.langchain4j.model.output.Response;

import java.nio.file.Paths;

public class EmbeddingModel {
    static void main() {
        var pathModel = Paths.get("c:/tools/onnx/model.onnx");
        var pathToTokenizer = Paths.get("c:/tools/onnx/tokenizer.json");
        OnnxEmbeddingModel model = new OnnxEmbeddingModel(pathModel,pathToTokenizer, PoolingMode.MEAN);

        //Generar un embedding
        Response<Embedding> response = model.embed("Hola, como estas");

        Embedding embedding = response.content();

        System.out.println("///////////////");
        System.out.println("Dimension: "+embedding.vector().length);
        System.out.println(embedding.vectorAsList());

    }
}
