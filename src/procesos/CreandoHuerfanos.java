package procesos;

import java.io.IOException;

public class CreandoHuerfanos {

    public static void main(String[] args) throws IOException {
    	//Probar en windows, en Linux no se va a ver, por como tenemos montado el entorno, matamos todos los procesos al acabar...
    	//Si lo ejecutamos desde el terminal en linux sí se ve java proceosos.CreandoHuerfanos
        boolean esWindows = System.getProperty("os.name").toLowerCase().contains("win");
        
        // 1. Obtenemos el PID de este programa Java (el padre)
        long pidPadre = ProcessHandle.current().pid();
        System.out.println("[PADRE] Hola, soy el proceso Java padre. Mi PID es: " + pidPadre);

        // 2. Preparamos un proceso hijo que estará vivo 120 segundos
        ProcessBuilder pb;
        if (esWindows) {
            // En Windows lanzamos un powershell durmiendo
            pb = new ProcessBuilder("powershell", "-NoProfile", "-Command", "Start-Sleep -Seconds 120");
        } else {
            // En Linux / macOS lanzamos el sleep estándar
            pb = new ProcessBuilder("sleep", "120");
        }

        // 3. Lanzamos el proceso hijo
        Process hijo = pb.start();
        long pidHijo = hijo.pid();

        System.out.println("[PADRE] Acabo de crear al hijo con PID: " + pidHijo);
        System.out.println("[PADRE] NO voy a hacer waitFor().");
        System.out.println("[PADRE] NO voy a hacer destroy().");
        System.out.println("[PADRE] ¡Me muero ahora mismo!");
        System.out.println("===============================================================");
        System.out.println(" COMPROBACIÓN: Abre tu terminal y busca al huérfano:");
        if (esWindows) {
            System.out.println("   PowerShell: Get-Process -Id " + pidHijo);
            System.out.println("   CMD:        tasklist | findstr " + pidHijo);
        } else {
            System.out.println("   Linux:      ps -ef | grep " + pidHijo);
            System.out.println("   Linux:      pgrep -a sleep");
        }
        System.out.println("===============================================================");

        // El main termina aquí de forma inmediata.
        // La JVM se apaga, pero el hijo sigue en marcha en el sistema operativo.
    }
}