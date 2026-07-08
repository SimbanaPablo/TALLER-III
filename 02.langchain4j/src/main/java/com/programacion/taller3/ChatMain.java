package com.programacion.taller3;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class ChatMain {
    //Modelo llama-3.2
    public static ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .apiKey("llama-key.cpp")
//                .modelName("llama-3.2-1b-instruct-q8_0.gguf")
                .modelName("gemma-4-E4B-it-Q4_K_S.gguf")
                .baseUrl("http://localhost:8080")
                .logRequests(true)
                .logResponses(true)
                .build();
    }
    //Modelo Groq
    static String base_url="https://api.groq.com/openai/v1";
    static String key = "gsk_XikQdfGvePeIi6LdKxBtWGdyb3FYM1suFIhoO8EcfRfgwG1Inv4hgsk_XikQdfGvePeIi6LdKxBtWGdyb3FYM1suFIhoO8EcfRfgwG1Inv4h";
    static String model = "llama-3.1-8b-instant";

    public static ChatModel chatModelGroq() {
        return OpenAiChatModel.builder()
                .apiKey(key)
                .modelName(model)
                .baseUrl(base_url)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    static void main() {
        ChatModel model = ChatMain.chatModelGroq();

        var respuesta = model.chat("Que es SpringBoot?");

        System.out.println("*** [Respuesta] ***");
        System.out.println(respuesta);
    }
}
