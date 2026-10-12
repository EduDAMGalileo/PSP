package procesos;

import java.io.File;
import java.io.IOException;

public class EjemploSinInterprete {
    public static void main(String[] args) throws IOException, InterruptedException {

        // =====================================================================
        // CASO 1: EL ERROR (Creer que Java entiende los símbolos de la terminal)
        // Queremos hacer un ping y guardar el resultado en un archivo de texto.
        // En la terminal escribiríamos: ping localhost > salida.txt
        // =====================================================================
        System.out.println("=== CASO 1: Usando '>' como argumento ===");
        
        // Pasamos el símbolo '>' como un String más:
        ProcessBuilder pbError = new ProcessBuilder("ping", "localhost", ">", "salida.txt");
        pbError.inheritIO();
        
        Process pError = pbError.start();
        pError.waitFor(); 

        // RESULTADO: El comando ping intentará hacerle un ping a un ordenador
        // llamado ">" y a otro ordenador llamado "salida.txt". 
        // Obviamente fallará y NO se creará ningún archivo.


        // =====================================================================
        // CASO 2: LA SOLUCIÓN MODO JAVA (Hacerlo a través de los métodos de ProcessBuilder)
        // Como no hay intérprete, tenemos que pedirle a Java que gestione la redirección.
        // =====================================================================
        System.out.println("\n=== CASO 2: La forma correcta en Java ===");
        
        ProcessBuilder pbCorrecto = new ProcessBuilder("ping", "localhost");
        
        // Sustituimos el símbolo '>' por el método redirectOutput de Java:
        pbCorrecto.redirectOutput(new File("salida_ping.txt"));
        
        Process pCorrecto = pbCorrecto.start();
        pCorrecto.waitFor();
        
        System.out.println("Comando ejecutado. Revisa tu carpeta, se ha creado 'salida_ping.txt'.");
    }
}