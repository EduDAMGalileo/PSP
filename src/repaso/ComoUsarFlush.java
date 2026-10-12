package repaso;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ComoUsarFlush {
    public static void main(String[] args) throws IOException {
        Path ruta = Path.of("prueba.txt");
        
        // Abrimos el flujo
        BufferedWriter writer = new BufferedWriter(new FileWriter(ruta.toFile()));

        // Escribimos algo
        writer.write("Primer mensaje");
        writer.newLine();

        // ¿Ha llegado al archivo? NO. Sigue en el búfer.
        System.out.println("Bytes en disco: " + Files.size(ruta));

        //FORZAMOS EL ENVÍO CON FLUSH (pero el flujo sigue abierto)
        writer.flush();

        // ¿Ha llegado ahora? SÍ.
        System.out.println("Bytes en disco tras flush: " + Files.size(ruta)); 

        //Podemos seguir escribiendo normalmente porque NO hemos cerrado el flujo
        writer.write("Segundo mensaje");
        writer.newLine();
        writer.flush();

        //Solo al final de todo, cerramos
        writer.close();
    }
}
