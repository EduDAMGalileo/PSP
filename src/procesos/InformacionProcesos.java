package procesos;

import java.io.IOException;
import java.time.Duration;
import java.util.function.Consumer;

public class InformacionProcesos {

    private static final boolean ES_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("=== 1. INFORMACIÓN DEL PROCESO PROPIO (JVM) == / ProcessHandle.current()");
        ProcessHandle propio = ProcessHandle.current();
        System.out.println("Mi propio PID es: " + propio.pid());
        informe(propio);

        System.out.println("\n=== 2. INFORMACIÓN DE UN PROCESO HIJO RECIÉN CREADO == / p.toHandle()");
        // Lanzamos un proceso que dure 15 segundos para que nos dé tiempo a inspeccionarlo
        Process p = crearProcesoDormilon(15).start();
        
        // Obtenemos su ProcessHandle a partir del objeto Process con toHandle()
        ProcessHandle handleHijo = p.toHandle();
        
        System.out.println("--- Informe inicial del hijo ---");
        informe(handleHijo);

        System.out.println("\n=== 3. BUSCAR UN PROCESO POR SU PID == / ProcessHandle.of(pid)");
        long pidBuscado = p.pid();
        ProcessHandle.of(pidBuscado).ifPresent(new Consumer<ProcessHandle>() {
            @Override
            public void accept(ProcessHandle h) {
                System.out.println("¡Encontrado por PID " + pidBuscado + "! ¿Sigue vivo?: " + h.isAlive());
            }
        });

        System.out.println("\n=== 4. RELACIÓN FAMILIAR (Padre e hijos) ===");
        // Comprobamos quién es el padre del hijo (debería ser nuestra JVM)
        handleHijo.parent().ifPresent(new Consumer<ProcessHandle>() {
            @Override
            public void accept(ProcessHandle padre) {
                System.out.println("El padre del proceso hijo tiene el PID: " + padre.pid());
                System.out.println("¿Es nuestra propia JVM?: " + (padre.pid() == ProcessHandle.current().pid()));
            }
        });

        // Dejamos pasar un par de segundos y volvemos a consultar el informe para ver si la CPU cambia
        System.out.println("\n[Esperando 3 segundos para que acumule algo de uso de CPU...]");
        Thread.sleep(3000);
        
        System.out.println("--- Informe actualizado del hijo ---");
        informe(handleHijo);

        // Limpieza final
        p.destroyForcibly();
        p.waitFor();
        System.out.println("\nProceso finalizado y limpiado.");
    }

    /**
     * Método adaptado exactamente de tus apuntes para imprimir la información
     * de cualquier ProcessHandle gestionando los Optional y usando clases anónimas.
     */
    private static void informe(ProcessHandle ph) {
        ProcessHandle.Info info = ph.info();
        
        System.out.println("----------------------------------------");
        System.out.println("PID:     " + ph.pid());
        System.out.println("Vivo:    " + ph.isAlive());
        System.out.println("Orden:   " + info.command().orElse("?"));
        System.out.println("Usuario: " + info.user().orElse("desconocido"));
        
        // startInstant devuelve un Optional<Instant>
        if (info.startInstant().isPresent()) {
            System.out.println("Inicio:  " + info.startInstant().get());
        } else {
            System.out.println("Inicio:  (No disponible en este S.O.)");
        }
        
        // totalCpuDuration devuelve un Optional<Duration>
        info.totalCpuDuration().ifPresent(new Consumer<Duration>() {
            @Override
            public void accept(Duration d) {
                System.out.println("CPU:     " + d.toMillis() + " ms");
            }
        });
        System.out.println("----------------------------------------");
    }

    // Sobrecarga por comodidad para aceptar también un Process tradicional
    private static void informe(Process p) {
        informe(p.toHandle());
    }

    // Auxiliar para lanzar un proceso dormilón compatible con Windows/Linux
    private static ProcessBuilder crearProcesoDormilon(int segundos) {
        if (ES_WINDOWS) {
            return new ProcessBuilder("powershell", "-Command", "Start-Sleep -Seconds " + segundos);
        } else {
            return new ProcessBuilder("sleep", String.valueOf(segundos));
        }
    }
}