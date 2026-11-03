package procesos;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class EjemploFileChannelLock {
    public static void main(String[] args) {
        File fichero = new File("recurso_compartido.txt");

        // Usamos RandomAccessFile en modo lectura/escritura ("rw") para poder obtener el canal
        try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
             FileChannel canal = raf.getChannel()) {

            System.out.println("1. Intentando adquirir el cerrojo (FileLock) sobre el fichero...");

            // .lock() bloquea el hilo/proceso hasta conseguir un cerrojo exclusivo sobre TODO el fichero
            // (Si otro proceso ya lo tuviera lockeado, este programa se pausaría aquí esperando)
            try (FileLock cerrojo = canal.lock()) { 
                
                System.out.println("¡CERROJO ADQUIRIDO! Ningún otro proceso puede tocar este fichero ahora.");
                
                // Simulamos una operación de escritura rápida usando un Buffer
                String mensaje = "Datos críticos escritos por el proceso principal.\n";
                ByteBuffer buffer = ByteBuffer.wrap(mensaje.getBytes());
                
                canal.write(buffer); // Escribimos a través del FileChannel
                System.out.println("2. Datos escritos correctamente en el fichero.");

                // Simulamos que estamos haciendo un trabajo largo de 5 segundos con el fichero bloqueado
                System.out.println("3. Trabajando con el fichero bloqueado durante 5 segundos...");
                Thread.sleep(5000); 

                // El cerrojo se libera automáticamente al salir del bloque try-with-resources o al llamar a cerrojo.release()
                System.out.println("4. Soltando el cerrojo...");
            } 

            System.out.println("¡Cerrojo liberado! El fichero vuelve a estar libre para otros procesos.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}