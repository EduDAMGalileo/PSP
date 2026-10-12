package procesos;

import static java.lang.ProcessBuilder.Redirect;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class RedireccionFicheros {
    public static void main(String[] args) throws IOException, InterruptedException {

        boolean esWindows = System.getProperty("os.name").toLowerCase().contains("win");

        // =====================================================================
        // PREPARACIÓN: Creamos el fichero de entrada con datos de prueba
        // =====================================================================
        File entrada = new File("datos.txt");
        Files.writeString(entrada.toPath(), "Zanahoria\nManzana\nPlatano\n", StandardCharsets.UTF_8);

        File log = new File("registro.log");
        File errores = new File("errores.log");

        // En Linux usamos 'wc -l' 
        // En Windows usamos 'sort' (comando nativo que lee de entrada y saca por salida)
        ProcessBuilder pb;
        if (esWindows) {
            pb = new ProcessBuilder("sort");
        } else {
            pb = new ProcessBuilder("wc", "-l");
        }


        // =====================================================================
        // 1. REDIRECCIÓN BÁSICA: < datos.txt  > registro.log  2> errores.log
        // =====================================================================
        System.out.println("=== 1. Ejecutando con redirección a ficheros ===");

        pb.redirectInput(entrada);  // <  Entrada estándar: lee desde datos.txt
        pb.redirectOutput(log);     // >  Salida estándar: escribe en registro.log (sobrescribe)
        pb.redirectError(errores);  // 2> Salida de error: desvía los fallos a errores.log

        Process p1 = pb.start();
        p1.waitFor();

        System.out.println("Contenido de 'registro.log' tras la primera ejecución:");
        System.out.println(Files.readString(log.toPath()));


        // =====================================================================
        // 2. AÑADIR AL FINAL (APPEND): >> registro.log
        // =====================================================================
        System.out.println("=== 2. Segunda ejecución añadiendo al final (appendTo) ===");

        // Cambiamos la redirección para que NO sobrescriba, sino que añada al final:
        pb.redirectOutput(Redirect.appendTo(log)); // >> registro.log

        Process p2 = pb.start();
        p2.waitFor();

        System.out.println("Contenido de 'registro.log' tras añadir al final:");
        System.out.println(Files.readString(log.toPath()));


        // =====================================================================
        // 3. DESCARTAR ERRORES: 2> /dev/null (Linux) o 2> NUL (Windows)
        // =====================================================================
        System.out.println("=== 3. Descartando la salida de errores (DISCARD) ===");

        // Si no te interesan los mensajes de error, los mandas a la basura directamente:
        pb.redirectError(Redirect.DISCARD);

        Process p3 = pb.start();
        p3.waitFor();
        System.out.println("Proceso finalizado. Errores descartados silenciosamente.");

        // Limpieza de los archivos creados
        entrada.delete();
        log.delete();
        errores.delete();
    }
}