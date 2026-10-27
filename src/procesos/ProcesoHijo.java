package procesos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ProcesoHijo {
    public static void main(String[] args) {
        // Leemos desde nuestra entrada estándar (que viene del Padre) usando UTF-8
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String linea;
            int contador = 0;

            // Este bucle seguirá leyendo hasta que el padre cierre su flujo (readLine() devuelve null)
            while ((linea = lector.readLine()) != null) {
                contador++;
                // Hacemos algo con el texto y lo devolvemos a nuestra salida estándar
                System.out.println("HIJO procesó la línea " + contador + ": " + linea.toUpperCase());
            }
            
            // Mensaje final para indicar cuántas líneas total recibió
            System.err.println("HIJO: Fin de la transmisión. Total de líneas procesadas: " + contador);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}