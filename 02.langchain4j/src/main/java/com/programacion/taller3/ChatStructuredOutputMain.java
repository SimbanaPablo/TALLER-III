package com.programacion.taller3;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

record Persona(String nombre, int edad, String ciudad){}

interface Extrator{
    @UserMessage("""
        Extrae la información del siguiente texto. 
        Debes responder ÚNICAMENTE con un objeto JSON válido que contenga los campos 'nombre', 'edad' y 'ciudad'. 
        No agregues saludos, explicaciones ni introducciones. Solo el JSON.
        
        Texto: {{texto}}
        """)
    Persona extraerPersona(@V("texto") String texto);
}
public class ChatStructuredOutputMain {
    static void main() {
        var model = ChatMain.chatModel();

        Extrator extrator = AiServices.builder(Extrator.class)
                .chatModel(model)
                .build();

        Persona p = extrator.extraerPersona(
                "Soy Maria, tengo 30 años y vivo en Quito"
        );
        System.out.println(p);
    }
}
