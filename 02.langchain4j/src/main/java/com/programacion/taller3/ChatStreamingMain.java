package com.programacion.taller3;

import com.programacion.taller3.utils.MyStreamingChatResponseHadler;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

import java.util.concurrent.atomic.AtomicInteger;

public class ChatStreamingMain{
    static void main() {
        //ChatModel chatModel = ChatMain.chatModel();
        //chatModel.chat("Que es LangChaing4j?");
        AtomicInteger count = new AtomicInteger(1);

        var chatModel = OpenAiStreamingChatModel.builder()
                .apiKey("llama-key.cpp")
                .modelName("llama-3.2-1b-instruct-q8_0.gguf")
                .baseUrl("http://localhost:8080")
                .logRequests(true)
                .logResponses(true)
                .build();

        chatModel.chat("Que es LangChaing4j?", new MyStreamingChatResponseHadler(count));

        while(true){

        }

    }
}
