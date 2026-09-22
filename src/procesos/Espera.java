package procesos;

public class Espera {
    public static void main(String[] args) throws InterruptedException {
        // Obtenemos el PID del proceso actual
        long pid = ProcessHandle.current().pid();

        System.out.println("==================================================");
        System.out.println("Programa Java en ejecución.");
        System.out.println("PID del proceso: " + pid);
        System.out.println("Copia y pega este comando en otra terminal:");
        System.out.println("ps -o pid,ppid,stat,nlwp,cmd -p " + pid);
        System.out.println("==================================================");
        System.out.println("Esperando 60 segundos... (pulsa Ctrl+C para salir)");

        // Mantiene el programa vivo sin hacer nada
        Thread.sleep(60_000);

        System.out.println("Fin del programa.");
    }
}