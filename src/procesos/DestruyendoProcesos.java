package procesos;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DestruyendoProcesos {

    // Lista global para el Shutdown Hook (Apartado 7.2 de los apuntes)
    private static final List<Process> procesosLanzados = new ArrayList<>();

    // Detectamos si estamos en Windows o Linux/macOS y lo metemos en una constante
    private static final boolean ES_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws Exception {
        // Registramos el gancho de cierre antes de hacer nada
        configurarShutdownHook();

        System.out.println("=== 1. PROTOCOLO DE TERMINACIÓN (destroy vs destroyForcibly) ===");
        demostrarTerminacionCorrecta();

        System.out.println("\n=== 2. TERMINAR DESCENDIENTES (children y descendants) ===");
        demostrarTerminacionDescendientes();

        System.out.println("\n=== Fin del programa principal ===");
    }

    /**
     * 7.1. Secuencia recomendada:
	     * 1º Intentar por las buenas (destroy) -> da oportunidad de cerrar ficheros/sockets.
	     * 2º Esperar con timeout razonable.
	     * 3º Si no responde, recurrir a la fuerza (destroyForcibly).
     */
    private static void demostrarTerminacionCorrecta() throws IOException, InterruptedException {
        // Lanzamos un proceso que tardará 30 segundos
        Process p = crearProcesoDormilon(30).start();
        procesosLanzados.add(p); // Lo registramos por seguridad

        System.out.println("[Padre] Proceso lanzado con PID: " + p.pid());
        System.out.println("[Padre] ¿Soporta terminación ordenada?: " + p.supportsNormalTermination());
        if (!p.supportsNormalTermination()) {
            System.out.println("       -> Nota: En Windows, destroy() siempre es forzado (no hay SIGTERM para consola).");
        }

        // 1. Petición ordenada ("por las buenas" / SIGTERM en Linux)
        System.out.println("[Padre] Enviando destroy()...");
        p.destroy();

        // 2. Esperamos hasta 2 segundos a que termine de verdad
        boolean murio = p.waitFor(Duration.ofSeconds(2));

        if (!murio) {
            // 3. Si no ha respondido en el plazo, mano dura ("por las malas" / SIGKILL)
            System.err.println("[Padre] No responde en el tiempo fijado. Forzando con destroyForcibly()...");
            p.destroyForcibly();
            p.waitFor(); // Espera obligatoria para recoger el código de salida
        }

        System.out.println("[Padre] Proceso muerto.");
        System.out.println("[Padre] Código de salida: " + p.exitValue());
        System.out.println("       -> (En Linux: 143 = 128+15 SIGTERM, 137 = 128+9 SIGKILL)");

        procesosLanzados.remove(p); // Ya no hace falta vigilarlo
    }

    /**
     * 7.2. Evitar procesos huérfanos / olvidados mediante un Shutdown Hook.
     * Si la JVM muere de forma natural, por System.exit() o por pulsar Stop en el IDE,
     * este hilo se ejecutará automáticamente para limpiar los hijos vivos.
     * Nota: Esto está creadno hilos, que aun no se han visto
     */
    private static void configurarShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[ShutdownHook] Limpiando procesos vivos antes de cerrar la JVM...");
            for (Process p : procesosLanzados) {
                if (p.isAlive()) {
                    System.out.println("[ShutdownHook] Matando proceso huérfano PID: " + p.pid());
                    p.destroyForcibly();
                }
            }
        }));
    }

    /**
     * 7.3. Matar hijos y nietos (árbol de procesos).
     * Si matas al proceso padre (ej. 'sh' o 'cmd'), sus hijos quedan huérfanos en el sistema.
     * Hay que matar de abajo hacia arriba: primero los descendientes, luego el padre.
     */
    private static void demostrarTerminacionDescendientes() throws IOException, InterruptedException {
        ProcessBuilder pb;

        if (ES_WINDOWS) {
            // En Windows lanzamos un cmd que arranca subprocesos en segundo plano
            pb = new ProcessBuilder("cmd.exe", "/c", "start /b ping 127.0.0.1 -n 60 > nul & ping 127.0.0.1 -n 60 > nul");
        } else {
            // En Linux / macOS el comando exacto de los apuntes:
            pb = new ProcessBuilder("sh", "-c", "sleep 60 & sleep 60");
        }

        Process p = pb.start();
        procesosLanzados.add(p);

        // Dejamos 500ms para que al intérprete le dé tiempo a engendrar a sus hijos (los nietos)
        Thread.sleep(500);

        System.out.println("[Árbol] PID del proceso raíz: " + p.pid());
        System.out.println("[Árbol] Hijos directos (children): " + p.children().count());
        System.out.println("[Árbol] Total descendientes (descendants): " + p.descendants().count());

        System.out.println("[Árbol] Destruyendo descendencia de abajo hacia arriba...");

        // 1. Matamos a todos los nietos/descendientes usando ProcessHandle
        // (Nota: ProcessHandle::destroy equivale a: handle -> handle.destroy())
        p.descendants().forEach(ProcessHandle::destroy);

        // 2. Matamos al proceso padre
        p.destroy();

        // 3. Esperamos a que el proceso raíz muera por completo
        p.waitFor();

        System.out.println("[Árbol] Árbol de procesos eliminado correctamente.");
        procesosLanzados.remove(p);
    }

    // Método auxiliar para lanzar comandos compatibles
    private static ProcessBuilder crearProcesoDormilon(int segundos) {
        if (ES_WINDOWS) {
            return new ProcessBuilder("powershell", "-Command", "Start-Sleep -Seconds " + segundos);
        } else {
            return new ProcessBuilder("sleep", String.valueOf(segundos));
        }
    }
}