package com.programacion.taller3;


import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.ModelType;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.output.Response;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class EmbeddingTest {
    public static final String PATH = "01.embeddings/the-verdict.txt";

    static void main() throws Exception {

        String raw_text = Files.lines(Paths.get(PATH))
                .reduce(String::concat)
                .orElse("");

        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding tokenize = registry.getEncodingForModel(ModelType.TEXT_DAVINCI_003);

        //-- generacion de (input, target)
        List<DataSetItem> dataset = new ArrayList<>();

        List<Integer> tokensIds = tokenize.encode(raw_text).boxed();

        int maxLength = 4;

        IntStream.range(0, tokensIds.size() - maxLength)
                .forEach(i -> {
                    var inputChunck = tokensIds.subList(i, i + maxLength);
                    var targetChunck = tokensIds.subList(i + 1, i + maxLength + 1);

                    dataset.add(new DataSetItem(inputChunck, targetChunck));
                });

        //--embedding
        int vocabSize = 50257;
        int putputDim = 256;

        try (NDManager manager = NDManager.newBaseManager()) {
            NDArray wights = manager.randomUniform(
                    -1.0f,
                    1.0f,
                    new Shape(vocabSize, putputDim)
            );

            dataset.stream()
                    .limit(2)
                    .forEach(item -> {
/*
                            long tmp [] new long[maxLength];
                            int index = 0;
                            for(var index:item.input()){
                                tmp[index] = token;
                                index++;
                            }
                            */

                        var input = item.input().stream()
                                .mapToLong(Integer::longValue)
                                .toArray();

                        NDArray indices = manager.create(input);

                        NDArray embeddings = wights.get(indices);

                        System.out.println("Input indices: " + Arrays.toString(input));
                        System.out.println("Embeddings output shape: " + embeddings);
                    });
        }

        //------------------------
        EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();
        var text = "hello";

        Response<Embedding> response = embeddingModel.embed(text);

        float[] vector = response.content().vector();

        System.out.println("Embeding size: " + vector.length);
        System.out.println(Arrays.toString(vector));
    }
}

