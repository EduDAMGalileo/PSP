package procesos;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LeerSalidaOrden {
    public static void main(String[] args) {
        
        // Usamos "git", un para que funcione en Windows, pero no en Linux (Allí no tenemos repositorio como tal)
        ProcessBuilder pb = new ProcessBuilder("git", "status", "--short");

        // Unificamos stdout y stderr:
        // Si el comando da error, el mensaje saldrá por el mismo InputStream
        // Si no lo ponemos no vemos el error de Linux
        pb.redirectErrorStream(true);

        try {
            // Arrancamos el proceso
            Process p = pb.start();

            // LEER TODO antes de esperar (waitFor):
            // readAllBytes() (desde Java 9) lee todo el flujo hasta que el hijo lo cierra
            // Es una alternativa a heredar la consola pb.inheritIO()
            String salida;
            try (InputStream in = p.getInputStream()) {
                salida = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }

            // Esperar a que el proceso termine definitivamente
            int codigoRetorno = p.waitFor();

            // Procesar el resultado
            System.out.println("El proceso terminó con código: " + codigoRetorno);
            System.out.println("Salida capturada:\n" + salida.trim());

        } catch (IOException e) {
            System.err.println("No se pudo ejecutar el comando: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido: " + e.getMessage());
        }
    }
}
