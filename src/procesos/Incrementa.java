package procesos;

import java.io.IOException; 
import java.nio.file.Files; 
import java.nio.file.Path; 

import static java.nio.charset.StandardCharsets.UTF_8; 

public class Incrementa { 
    public static void main(String[] args) throws IOException { 
        Path fichero = Path.of("contador.txt"); 
        if (!Files.exists(fichero)) {
            Files.writeString(fichero, "0", UTF_8);
        }
        
        int veces = Integer.parseInt(args[0]);

        for (int i = 0; i < veces; i++) { 
            String texto = Files.readString(fichero, UTF_8).strip(); 
            int valor = texto.isEmpty() ? 0 : Integer.parseInt(texto); 
            Files.writeString(fichero, String.valueOf(valor + 1), UTF_8); 
        } 
    } 
} 
