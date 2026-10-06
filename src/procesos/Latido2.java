package procesos;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Latido2 {
    public static void main(String[] args) throws InterruptedException {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Código que la JVM ejecuta al terminar ordenadamente, ya lo veremos en más detalle
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Recibida orden de parada. Cerrando.");
            }
        });

        long pid = ProcessHandle.current().pid();
        System.out.println("Latido arrancado con PID " + pid);
        while (true) {
            String hora = LocalTime.now().format(formato);
            System.out.println("Latido a las " + hora);
            Thread.sleep(5000);
        }
    }
}

