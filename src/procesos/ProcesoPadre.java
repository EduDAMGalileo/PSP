package procesos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ProcesoPadre {
    public static void main(String[] args) {
    	ProcessBuilder builder = new ProcessBuilder("java", "procesos.ProcesoHijo");

        try {
            //Arrancamos el proceso hijo
            Process p = builder.start();

            // --- ESCRIBIR AL HIJO Y CERRAR EL FLUJO ---
            // Usamos un try-with-resources solo para la escritura. 
            // Al salir de este bloque, 'aHijo' se cierra automáticamente (nuestro Ctrl+D).
            try (BufferedWriter aHijo = p.outputWriter(StandardCharsets.UTF_8)) {
                System.out.println("PADRE: Enviando datos al hijo...");
                aHijo.write("Hola hijo, aquí van datos con tildes: canción, lápiz.\n");
                aHijo.write("Esta es la segunda línea de texto.\n");
                aHijo.flush(); 
                System.out.println("PADRE: Datos enviados. Saliendo del bloque, se cerrará la entrada del hijo...");
            } // <- Aquí se cierra aHijo. El hijo recibe el final de entrada (null).

            // --- LEER LA RESPUESTA DEL HIJO ---
            // Como el flujo de escritura ya está cerrado, el hijo ya ha procesado todo y 
            // ahora podemos leer tranquilamente su salida sin bloquearnos.
            System.out.println("\nPADRE: Leyendo la respuesta del hijo...");
            
            // Abrimos los lectores ahora (fuera del bloque de escritura)
            try (BufferedReader deHijo = p.inputReader(StandardCharsets.UTF_8);
                 BufferedReader deErrores = p.errorReader(StandardCharsets.UTF_8)) {

                String respuesta;
                while ((respuesta = deHijo.readLine()) != null) {
                    System.out.println("  [De la salida del hijo] -> " + respuesta);
                }

                String errorLog;
                while ((errorLog = deErrores.readLine()) != null) {
                    System.out.println("  [Del canal de errores] -> " + errorLog);
                }
            }

            // 2. Esperar a que el proceso hijo termine por completo y recoger el código de salida
            int codigoSalida = p.waitFor();
            System.out.println("\nPADRE: El proceso hijo ha terminado con el código: " + codigoSalida);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}