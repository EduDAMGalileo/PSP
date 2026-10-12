package procesos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EjemploInterpreteOS {
    public static void main(String[] args) throws IOException, InterruptedException {

        // CREAMOS UN ARCHIVO DE PRUEBA RÁPIDO
        // Para asegurar que el comodín *.java encuentre algo tanto en Windows como en Linux:
        Path archivoPrueba = Path.of("PruebaTemporal.java");
        if (!Files.exists(archivoPrueba)) {
            Files.writeString(archivoPrueba, "// Archivo de prueba para comodines\n");
        }

        //DETECCIÓN DEL SISTEMA OPERATIVO
        String nombreSO = System.getProperty("os.name").toLowerCase();
        boolean esWindows = nombreSO.contains("win");

        ProcessBuilder pb;

        if (esWindows) {
            System.out.println("-> Sistema detectado: WINDOWS");
            System.out.println("-> Invocando a 'cmd.exe' para expandir el comodín *.java:");
            
            // En Windows llamamos a cmd.exe
            // El parámetro '/c' significa: "ejecuta esta orden y ciérrate"
            // '/b' en dir muestra solo los nombres limpios
            pb = new ProcessBuilder("cmd", "/c", "dir /b *.java");

        } else {
            System.out.println("-> Sistema detectado: LINUX / macOS");
            System.out.println("-> Invocando a 'sh' para usar comodines y tuberías (|):");

            // En Linux llamamos a /bin/sh (o bash)
            // El parámetro '-c' significa: "lee la orden desde la cadena de texto siguiente"
            // 'wc -l' cuenta cuántos archivos .java hay mediante una tubería |
            pb = new ProcessBuilder("sh", "-c", "ls *.java | wc -l");
        }

        // 4. CONECTAMOS LA SALIDA CON TU CONSOLA Y ARRANCAMOS
        pb.inheritIO();
        Process proceso = pb.start();

        int codigoSalida = proceso.waitFor();
        System.out.println("\nProceso terminado con código de salida: " + codigoSalida);

        // Limpieza: borramos el archivo de prueba
        Files.deleteIfExists(archivoPrueba);
    }
}