package com.programacion.taller3;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.*;

public class TokkitTokenizer {
    static void main() {
        //Generar bocabulario con una libreria
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding tokenize = registry.getEncodingForModel(ModelType.TEXT_DAVINCI_003);
        //var text = "Hello, do you like tea?"
        var text = "Hello, do you like tea? In the sunlit terraces of someunknowPlace";
        //var text = "<|emdpftext|>"

        IntArrayList ids = tokenize.encodeOrdinary(text);

        System.out.println("Tokens: "+ids.size());
        System.out.println(ids);
        System.out.println("Decoded: "+tokenize.decode(ids));
    }
}
