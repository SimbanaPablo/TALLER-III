package com.programacion.taller3;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.util.List;

class HerramientasLegales{
    @Tool("""
        Verifica si un ciudadano posee antecedentes penales registrados 
        en la Republica del Ecuador usando su nuero de identificacion o nombre.
        """)
    public String verificarAntecedentesPenales(String nombreContador){
        System.out.println("\n[SISTEMA - TOOL EXECUTED]: Ejecutando verificación de antecedentes para: " + nombreContador);
        if(nombreContador.toLowerCase().contains("contador")){
            return "El ciudadano presenta una investigación previa abierta por desvío de fondos en 2024.";
        }
        return "Sin antecedentes penales vigentes";
    }
}

record DictamenLegal2(
        String veredicto,
        List<String> articulosViolados,
        double penalizacion
) { }

interface AsistenteLegal    {
    @SystemMessage(
            """
            Eres un abogado experto de la República del Ecuador.
            Analiza el caso combinado con el contexto legal proporcionado.
            Si el caso menciona nombres de personas, utiliza las herramientas disponibles para verificar sus antecedentes.
            Debes obligatoriamente estructurar tu respuesta final en el formato requerido.
            """
    )
//    @UserMessage("Analiza el siguiente caso de estudio: {{caso}}")
    @UserMessage("Analiza el siguiente caso de estudio: {{caso}}")
    DictamenLegal2 analizarCaso(@V("caso") String caso);
}

public class ChatAiServiceMain {
    public static ChatModel chatModel() {
        return OpenAiChatModel.builder()
                .apiKey("llama-key.cpp")
                .modelName("llama-3.2-1b-instruct-q8_0.gguf")
                .baseUrl("http://127.0.0.1:8080")
                .logRequests(true)
                .logResponses(true)
                .build();
    }
    static void main() {

        List<String> leyes = List.of(
                "Artículo 100: El fraude financiero mediante alteración de balances contables será sancionado con una penalización equivalente al doble del monto desviado.",
                "Artículo 101: La evasión de impuestos en el ejercicio fiscal actual conlleva auditorías obligatorias.",
                "Artículo 205: Las deudas civiles prescriben en un plazo de 5 años."
        );

        String casoComplejo = """
            El día de ayer se descubrió que el contador de la empresa ABC alteró 
            los balances financieros del año pasado para desviar $50,000 hacia una 
            cuenta personal, cometiendo un claro fraude financiero y evadiendo 
            los impuestos correspondientes del ejercicio fiscal actual.
            """;

        //// Elcccion del contexto semantico
        var modelEmbedding = new AllMiniLmL6V2EmbeddingModel();

        List<Embedding> embeddingsLeyes = leyes.stream()
                .map(modelEmbedding::embed)
                .map(Response::content)
                .toList();

        Embedding queryEmbedding = modelEmbedding.embed(casoComplejo).content();

        int mejorIndice = 0;
        double mejorScore = 0;
        for (int i = 0; i < embeddingsLeyes.size(); i++) {
            double score = CosineSimilarity.between(queryEmbedding, embeddingsLeyes.get(i));
            if (score > mejorScore) {
                mejorScore = score;
                mejorIndice = i;
            }
        }
        String leyContextual = leyes.get(mejorIndice);
        System.out.println("-> Artículo seleccionado por similitud: " + leyContextual);

        ChatModel model = ChatAiServiceMain.chatModel();
        AsistenteLegal asistente = AiServices.builder(AsistenteLegal.class)
                .chatModel(model)
                .tools(new HerramientasLegales())
                .build();

        String promptFinal = "CONTEXTO INYECTADO:\n" + leyContextual + "\n\nDETALLES DEL CASO:\n" + casoComplejo;

        var dictamen = asistente.analizarCaso(promptFinal);

        System.out.println(dictamen);
// --- PARTE C: RESULTADOS FINALES ENCAPSULADOS ---
        System.out.println("\n=== DICTAMEN FINAL OBTENIDO ===");
        System.out.println("Veredicto: " + dictamen.veredicto());
        System.out.println("Artículos Aplicados: " + dictamen.articulosViolados());
        System.out.println("Penalización final (calculada con el doble del fraude): $" + dictamen.penalizacion());
    }
}
