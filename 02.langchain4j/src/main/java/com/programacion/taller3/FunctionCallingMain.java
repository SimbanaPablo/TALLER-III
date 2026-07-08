package com.programacion.taller3;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.time.LocalDateTime;

class Herramientas {
    @Tool("Obtiene la fecha y hora actual")
    String obtenerFechaHora(){
        return LocalDateTime.now().toString();
    }
    @Tool("Cual es el usuario actual")
    String obtenerNomnreUsuario(){
        return String.format("El nombre del usuario de este equipo es ADMIN");
    }
    @Tool("Calcula el radio de un circulo dado su radio")
    double calcularAreaCirculo(@P("El radio del circulo") double radio){
        return Math.PI * radio * radio;
    }
}
interface AsistenteConTools{
    @SystemMessage("Eres un asistente útil. Usa las herramientas disponibles para responder a las preguntas del usuario.")
    String chat(String mensaje);
}
public class FunctionCallingMain {
    static void main() {
      var model =  ChatMain.chatModel();
      var service = AiServices.builder(AsistenteConTools.class)
              .chatModel(model)
              .tools(new Herramientas())
              .build();

      var respuesta = service.chat("Que hora es?");
      System.out.println("RESPUESTA: "+respuesta);
        System.out.println("/////////////////////////////////////");
      var respuestaUsuario = service.chat("Cual es el usuario actual");
        System.out.println("RESPUESTA: "+respuestaUsuario);

    }
}
