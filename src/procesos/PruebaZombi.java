package procesos;

import java.io.IOException;

public class PruebaZombi {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Java (Padre) PID: " + ProcessHandle.current().pid());

        // 1. Lanzamos un hijo corto (sleep de 10 segundos)
        Process hijo = new ProcessBuilder("sleep", "10").start();
        long pidHijo = hijo.pid();

        System.out.println("Hijo lanzado con PID: " + pidHijo);
        System.out.println("-------------------------------------------------------");
        System.out.println("Comprueba en otra terminal con:");
        System.out.println("ps -o pid,ppid,stat,cmd -p " + pidHijo);
        System.out.println("-------------------------------------------------------");

        Thread.sleep(5000);
        // 2. NUNCA llamamos a hijo.waitFor()
        // Dejamos pasar el tiempo para que el hijo muera mientras Java sigue despierto
        for (int i = 1; i <= 16; i++) {
            Thread.sleep(1000);
            System.out.println("Segundo " + i + " | ¿Hijo vivo?: " + hijo.isAlive());
        }

        System.out.println("\nComprueba de nuevo en la otra terminal.");
        System.out.println("Verás que el hijo no está en estado 'Z' (<defunct>): ha desaparecido por completo.");
        System.out.println("La JVM lo recogió de forma transparente.");
    }
}