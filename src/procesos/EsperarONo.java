package procesos;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class EsperarONo {

    // Método para lanzar un proceso que "duerma" según el sistema operativo
    private static ProcessBuilder crearProcesoDormilon(int segundos) {
        boolean esWindows = System.getProperty("os.name").toLowerCase().contains("win");
        if (esWindows) {
            // En Windows usamos powershell Start-Sleep (o timeout /t)
            return new ProcessBuilder("powershell", "-Command", "Start-Sleep -Seconds " + segundos);
        } else {
            // En Linux / macOS usamos el comando estándar 'sleep'
            return new ProcessBuilder("sleep", String.valueOf(segundos));
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. WAITFOR() INDEFINIDO (BLOQUEANTE) ===");
        ejemploWaitForBloqueante();

        System.out.println("\n=== 2. WAITFOR() CON TIMEOUT (SEGURO) ===");
        ejemploWaitForConTimeout();

        System.out.println("\n=== 3. CONSULTAR SIN ESPERAR (isAlive y exitValue) ===");
        ejemploConsultarSinEsperar();

        System.out.println("\n=== 4. onExit() - REACCIÓN ASÍNCRONA (SIN BLOQUEAR) ===");
        ejemploOnExitAsincrono();
    }

    /**
     * 6.1. waitFor() bloquea el hilo actual hasta que el hijo termina.
     */
    private static void ejemploWaitForBloqueante() throws IOException, InterruptedException {
        System.out.println("[Padre] Lanzando proceso de 10 segundos...");
        Process p = crearProcesoDormilon(10).start();

        System.out.println("[Padre] Esperando a que termine (el hilo se bloquea)...");
        int codigo = p.waitFor(); // Bloqueo real: no consume CPU

        if (codigo == 0) {
            System.out.println("[Padre] El proceso terminó correctamente (código: " + codigo + ")");
        } else {
            System.err.println("[Padre] El proceso falló con código: " + codigo);
        }
    }

    /**
     * 6.2. waitFor(Duration) evita bloqueos eternos si el proceso hijo falla o se cuelga.
     */
    private static void ejemploWaitForConTimeout() throws IOException, InterruptedException {
        System.out.println("[Padre] Lanzando proceso de 15 segundos (simulando tarea pesada)...");
        Process p = crearProcesoDormilon(15).start();

        System.out.println("[Padre] Solo estamos dispuestos a esperar 5 segundos...");
        boolean termino = p.waitFor(Duration.ofSeconds(5));

        if (!termino) {
            System.err.println("[Padre] ¡Demasiado lento! Procedemos a cancelarlo con destroy().");
            p.destroy();    // Pide al SO que lo termine
            p.waitFor();    // Esperamos a que muera de verdad para evitar procesos zombi
            System.out.println("[Padre] Proceso destruido con éxito. Código de salida: " + p.exitValue());
        } else {
            System.out.println("[Padre] Terminó a tiempo.");
        }
    }

    /**
     * 6.3. isAlive() y exitValue(). Cómo inspeccionar el estado sin bloquear.
     */
    private static void ejemploConsultarSinEsperar() throws IOException, InterruptedException {
        Process p = crearProcesoDormilon(5).start();

        System.out.println("[Padre] Proceso hijo iniciado con PID: " + p.pid());
        System.out.println("[Padre] ¿Está vivo según isAlive()?: " + p.isAlive());

        // Intentar leer el exitValue() mientras sigue vivo lanza excepción:
        try {
            int codigo = p.exitValue();
            System.out.println("[Padre] Código: " + codigo);
        } catch (IllegalThreadStateException e) {
            System.out.println("[Padre] ERROR esperado: No podemos leer exitValue() porque aún no ha terminado.");
        }

        // ADVERTENCIA IMPORTANTE: ¡No hacer esto!
        // while(p.isAlive()) { } // <-- ESPERA ACTIVA (quema CPU al 100%)

        // Dejamos que acabe de forma limpia
        p.waitFor();
        System.out.println("[Padre] Ahora que terminó, exitValue() es: " + p.exitValue());
    }

    /**
     * 6.4. onExit(). Reaccionar usando CompletableFuture sin pausar el hilo principal.
     */
    private static void ejemploOnExitAsincrono() throws IOException, InterruptedException, ExecutionException {
        System.out.println("[Padre] Lanzando proceso hijo de 3 segundos...");
        Process p = crearProcesoDormilon(3).start();

        
        // Registramos un Callback que se ejecutará en OTRO hilo en cuanto el hijo muera
        ///Código complejo!! Expresion Lambda
        p.onExit().thenAccept(pr -> {
            System.out.println("\n>>> [Callback asíncrono] ¡Terminó el proceso PID: " 
                    + pr.pid() + " con código: " + pr.exitValue() + "! <<<");
        });
            

        // El hilo main no se detiene, continúa trabajando:
        System.out.println("[Padre] Sigo haciendo mis cosas mientras el hijo trabaja...");
        for (int i = 1; i <= 6; i++) {
            Thread.sleep(1000);
            System.out.println("[Padre] Haciendo tarea pesada (" + i + "s)...");
        }

        // OJO: Si el hilo main hubiera terminado antes de los 3s, la JVM se cerraría
        // y el callback de onExit no llegaría a verse.
        System.out.println("[Padre] Fin del programa.");
    }
}