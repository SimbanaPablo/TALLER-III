package com.programacion.taller3;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class ChatMain {
    public static ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .apiKey("llama-key.cpp")
                .modelName("llama-3.2-1b-instruct-q8_0.gguf")
                .baseUrl("http://localhost:8080")
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    static void main() {
        ChatModel model = ChatMain.chatModel();

        var respuesta = model.chat("Que es LangChaing4j?");

        System.out.println("*** [Respuesta] ***");
        System.out.println(respuesta);
    }
}
