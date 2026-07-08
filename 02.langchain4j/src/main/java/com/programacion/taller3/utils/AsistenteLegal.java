package com.programacion.taller3.utils;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

//@SystemMessage("Eres un asistente legal experto en leyes del Ecuador.")
public interface AsistenteLegal {

    String responder(String pregunta);
//    @SystemMessage("Eres un asistente legal experto en leyes del Ecuador.")
//    @UserMessage(" Responde a la pregunta: {{pregunta}}}")
    @SystemMessage("Eres un asistente lagal en temas de educación superior")
    @UserMessage(
            """
            Responde a la pregunta: {{pregunta}}}
            Si no tienes la respuesta, utiliza la sección DATOS que se incluye a continuación:
            
            DATOS
            -----
                    interface AsistenteLegal {
                        String responder(String pregunta);
                        @SystemMessage("Eres un asistente legal experto en leyes del Ecuador.")
                        @UserMessage(" Responde a la pregunta: {{pregunta}}}")
                        String consultar(@V("pregunta") String pregunta);
                    }
                    OpenAiChatModel.builder()
                        .apiKey("llama-key.cpp")
                        .modelName("llama-3.2-1b-instruct-q8_0.gguf")
                        //.modelName("gemma-4-E4B-it-Q4_K_S.gguf")
                        .baseUrl("http://localhost:8080")
                        .build();
                    var asistente = AiServices.create(AsistenteLegal.class, model);
                    var respuesta = asistente.consultar("Cuál es el plazo de prescripción para deudas civiles?");
                    System.out.println(respuesta);
            -----
            """)
    String consultar(@V("pregunta") String pregunta);
}
