package procesos;

public class Latido {
    public static void main(String[] args) {
        // Registramos el "código de cierre" (Shutdown Hook)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nRecibida orden de parada. Cerrando.");
        }));

        System.out.println("Latido iniciado (PID: " + ProcessHandle.current().pid() + ")...");

        // 2. Bucle infinito para simular el "latido" y mantener el proceso vivo
        try {
            while (true) {
                Thread.sleep(1000);
                System.out.println("Bump. ");
            }
        } catch (InterruptedException e) {
            // Ignorado
        }
    }
}