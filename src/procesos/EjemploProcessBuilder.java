package procesos;

import java.io.IOException;
import java.util.List;

public class EjemploProcessBuilder {
    public static void main(String[] args) throws IOException, InterruptedException {

        // =====================================================================
        // LAS DOS FORMAS EQUIVALENTES DE CREARLO
        // Da exactamente igual usar argumentos sueltos (varargs) o List.of()
        // =====================================================================
        
        // Forma A: Argumentos sueltos
        ProcessBuilder pb1 = new ProcessBuilder("hostname");

        // Forma B: Con una List<String>
        ProcessBuilder pb2 = new ProcessBuilder(List.of("hostname"));

        System.out.println("Comando pb1: " + pb1.command()); 
        System.out.println("Comando pb2: " + pb2.command()); 


        // =====================================================================
        // CONSULTAR Y CAMBIAR LA ORDEN DESPUÉS DE CREARLA
        // =====================================================================
        System.out.println("\n=== MODIFICAR EL COMANDO ===");
        
        ProcessBuilder pb = new ProcessBuilder("echo", "hola");
        
        // Consultamos la lista actual:
        System.out.println("Orden inicial:   " + pb.command());

        // Cambiamos la orden por completo pasándole una lista nueva:
        pb.command(List.of("echo", "adiós"));
        System.out.println("Orden modificada: " + pb.command());


        // =====================================================================
        // EJECUTAR EL PROCESO DE VERDAD
        // Usamos un comando que existe tanto en Windows como en Linux: 'whoami'
        // (inheritIO() hace que la salida del proceso salga directamente por tu consola)
        // =====================================================================
        System.out.println("\n=== EJECUTANDO UN COMANDO REAL ===");
        
        ProcessBuilder ejecutor = new ProcessBuilder("whoami");
        // Conecta la salida del proceso con la consola de Eclipse/IntelliJ
        ejecutor.inheritIO(); 
        
        // Arranca el proceso hijo
        Process proceso = ejecutor.start(); 
        // Espera a que termine
        proceso.waitFor();                   


        // =====================================================================
        // EL ERROR TÍPICO: Pasar toda la orden como un único String
        // =====================================================================
        System.out.println("\n=== DEMOSTRACIÓN DEL ERROR TÍPICO ===");
        try {
            // Intentamos ejecutarlo como una frase entera:
            ProcessBuilder pbError = new ProcessBuilder("whoami /all");
            pbError.start();
        } catch (IOException e) {
            System.err.println("¡ERROR!: Java no encuentra ningún ejecutable llamado 'whoami /all'.");
            System.err.println("La forma correcta era: new ProcessBuilder(\"whoami\", \"/all\");");
        }
    }
}