package com.programacion.taller3.services;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransformerProcessor {

    public List<Document> procesar(List<Document> documents) {
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(350)
                .build();

        List<Document> splitted = splitter.split(documents);

        System.out.println("TransformerProcessor::documentos divididos creados: " + splitted.size());
//        System.out.println("Documenot 0");
//        System.out.println(splitted.getFirst());

        return splitted;
    }
}
