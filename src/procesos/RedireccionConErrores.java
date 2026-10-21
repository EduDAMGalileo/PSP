package procesos;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class RedireccionConErrores {
    public static void main(String[] args) throws IOException, InterruptedException {

        // 1. Preparamos un fichero de entrada con datos correctos y trampas (líneas en blanco y números)
        File fEntrada = new File("nombres_entrada.txt");
        List<String> contenidoInput = List.of(
            "Ana",
            "",              // Trampa 1: Línea en blanco (provocará error en stderr)
            "Carlos",
            "12345",         // Trampa 2: Un número (provocará error en stderr)
            "Elena"
        );
        // Escribimos el fichero de entrada uniendo con saltos de línea
        Files.writeString(fEntrada.toPath(), String.join("\n", contenidoInput) + "\n", StandardCharsets.UTF_8);

        // Ficheros donde se desviaremos la salida estándar y la de errores
        File fSalidaOk = new File("resultados_correctos.log");
        File fErroresLog = new File("registro_errores.log");

        // Preparamos el ProcessBuilder para ejecutar nuestra propia clase FiltroNombres
        // Usamos el Lanzador que ya conocemos para asegurar que coja bien el classpath
        ProcessBuilder pb = Lanzador.para("procesos.FiltroNombres");

        // =====================================================================
        // CONFIGURANDO LA REDIRECCIÓN
        // =====================================================================
        
        // <  La entrada estándar (System.in) la leerá automáticamente de este fichero
        pb.redirectInput(fEntrada);

        // >  La salida estándar (System.out) se guarda limpia en este fichero
        pb.redirectOutput(fSalidaOk);

        // 2> La salida de errores (System.err) se separa y se manda a su propio fichero de registro
        pb.redirectError(fErroresLog);

        System.out.println("=== Lanzando proceso y aplicando redirecciones a ficheros ===");
        Process proceso = pb.start();
        int codigoSalida = proceso.waitFor();

        System.out.println("El proceso hijo terminó con código: " + codigoSalida);

        // =====================================================================
        // INSPECCIONANDO QUÉ HA PINTADO CADA FICHERO
        // =====================================================================
        System.out.println("\n--------------------------------------------------");
        System.out.println(" CONTENIDO DE 'resultados_correctos.log' (Lo que fue bien):");
        System.out.println("--------------------------------------------------");
        System.out.println(Files.readString(fSalidaOk.toPath(), StandardCharsets.UTF_8));

        System.out.println("--------------------------------------------------");
        System.out.println(" CONTENIDO DE 'registro_errores.log' (Lo que falló):");
        System.out.println("--------------------------------------------------");
        System.out.println(Files.readString(fErroresLog.toPath(), StandardCharsets.UTF_8));

        // Limpieza de los archivos creados en el disco
        fEntrada.delete();
        fSalidaOk.delete();
        fErroresLog.delete();
        
        System.out.println("=== Limpieza completada ===");
    }
}