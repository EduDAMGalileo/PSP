package procesos;

import java.io.IOException;
import java.util.Map;

public class EjemploVariablesEntorno {
    public static void main(String[] args) throws IOException, InterruptedException {

        // =====================================================================
        // CONSULTAR VARIABLES EN EL PROPIO PROGRAMA (PROCESO PADRE)
        // =====================================================================
        System.out.println("=== 1. Consultar variables desde Java ===");
        
        // System.getenv(nombre) devuelve null si la variable no existe
        
        String usuario = System.getenv("USERNAME"); // Típica de Windows
        if (usuario == null) {
            usuario = System.getenv("USER");       // Típica de Linux / macOS
        }
        System.out.println("Usuario del sistema: " + usuario);


        // =====================================================================
        // CREAR Y PASAR VARIABLES A UN PROCESO HIJO
        // =====================================================================
        System.out.println("\n=== 2. Pasar una variable propia al proceso hijo ===");

        boolean esWindows = System.getProperty("os.name").toLowerCase().contains("win");

        // Preparamos el comando que imprimirá nuestra variable personalizada:
        // En Windows se consulta con %MI_VARIABLE% y en Linux con $MI_VARIABLE
        ProcessBuilder pb;
        if (esWindows) {
            pb = new ProcessBuilder("cmd.exe", "/c", "echo Variable recibida por el hijo: %PSP_MENSAJE%");
        } else {
            pb = new ProcessBuilder("sh", "-c", "echo Variable recibida por el hijo: $PSP_MENSAJE");
        }

        // pb.environment() nos da un Map<String, String> editable con las variables del hijo
        Map<String, String> entornoHijo = pb.environment();

        // A) CONSULTAR una variable que el hijo heredará del sistema
        // (por ejemplo, el PATH)
        System.out.println("¿El hijo hereda variables del padre?: " + entornoHijo.containsKey("PATH"));

        // B) CREAR una variable nueva exclusivamente para ese proceso hijo
        entornoHijo.put("PSP_MENSAJE", "¡Hola desde el proceso padre en Java!");

        // Mostramos la salida del comando directamente en la consola de Eclipse/IntelliJ
        pb.inheritIO();

        // Arrancamos el hijo y esperamos a que termine
        Process p = pb.start();
        p.waitFor();

        System.out.println("\nProceso hijo finalizado con código: " + p.exitValue());
    }
}