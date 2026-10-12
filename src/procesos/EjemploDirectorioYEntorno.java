package procesos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class EjemploDirectorioYEntorno {
    public static void main(String[] args) throws IOException, InterruptedException {

        boolean esWindows = System.getProperty("os.name").toLowerCase().contains("win");

        // =====================================================================
        // PREPARACIÓN PREVIA: Creamos una carpeta de pruebas real
        // Así aseguramos que exista tanto en Windows como en Linux
        // =====================================================================
        Path rutaPruebas = Path.of("carpeta_trabajo_temporal");
        Files.createDirectories(rutaPruebas);


        // =====================================================================
        // PARTE 1: Configurar variables de entorno y cambiar directorio
        // =====================================================================
        System.out.println("=== PARTE 1: Variables de entorno y directorio ===");

        ProcessBuilder pb;
        if (esWindows) {
            // En Windows: %MI_MODO% y %cd%
            pb = new ProcessBuilder("cmd", "/c", "echo Variable=[%MI_MODO%] en carpeta: %cd%");
        } else {
            // En Linux: $MI_MODO y $(pwd)
            pb = new ProcessBuilder("sh", "-c", "echo Variable=[$MI_MODO] en carpeta: $(pwd)");
        }

        // 1. MANIPULAR EL ENTORNO (Map<String, String>)
        Map<String, String> entorno = pb.environment();

        // Consulta segura: en Windows suele llamarse "Path" y en Linux "PATH"
        String pathHeredado = entorno.get("PATH");
        if (pathHeredado == null) {
        	// Alternativa de Windows
            pathHeredado = entorno.get("Path");
        }

        if (pathHeredado != null) {
            int longitud = Math.min(35, pathHeredado.length());
            System.out.println("Path del sistema (resumen): " + pathHeredado.substring(0, longitud) + "...");
        }

        // Añadimos una variable propia que el proceso hijo heredará:
        entorno.put("MI_MODO", "ENTORNO_ACTIVO");

        // CAMBIAR EL DIRECTORIO DE TRABAJO (Usando la carpeta que creamos)
        pb.directory(rutaPruebas.toFile());

        // EJECUTAR EL PROCESO
        pb.inheritIO();
        Process p = pb.start();
        p.waitFor();



        // =====================================================================
        // PARTE 2: La trampa del directorio inexistente
        // Comprobar que el error salta en start(), no en directory()
        // =====================================================================
        System.out.println("\n=== PARTE 2: Carpeta inexistente ===");

        ProcessBuilder pbError = new ProcessBuilder(
                esWindows ? new String[]{"cmd", "/c", "echo ok"} 
                          : new String[]{"sh", "-c", "echo ok"}
        );

        // Asignamos una carpeta que sabemos seguro que no existe:
        File carpetaFantasma = new File("esta_carpeta_no_existe_seguro_123");
        pbError.directory(carpetaFantasma); 
        System.out.println("1. pb.directory() configurado sin errores.");

        try {
            System.out.println("2. Llamando a start()...");
           // Aquí es donde salta la IOException
            pbError.start(); 
        } catch (IOException e) {
            System.err.println("¡Capturada IOException esperada al arrancar!");
            System.err.println("Mensaje del SO: " + e.getMessage());
        }

        // Limpieza: borramos la carpeta temporal creada al inicio
        Files.deleteIfExists(rutaPruebas);
    }
}