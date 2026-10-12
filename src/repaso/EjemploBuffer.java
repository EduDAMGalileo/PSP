package repaso;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EjemploBuffer {
    public static void main(String[] args) throws IOException {
        Path rutaLog = Path.of("transacciones.log");
        BufferedWriter log = Files.newBufferedWriter(rutaLog);

        //Ocurre una operación importante
        System.out.println("Procesando pago...");
        log.write("[10:00:01] Cobro de 500€ realizado al cliente #123.");
        log.newLine();

        // ¿Qué hay realmente en el archivo en este instante?
        System.out.println("Tamaño del archivo en disco: " + Files.size(rutaLog) + " bytes"); 
        // Imprime 0 bytes. El mensaje sigue atrapado en la RAM (en el búfer).

        // Ocurre una catástrofe antes de llegar al 'close()'
        System.out.println("¡Fallo crítico! Pérdida de energía / System.exit...");
        System.exit(1); // Simula una terminación forzada del proceso

        //Esta línea NUNCA se ejecutará:
        log.close();
    }
}