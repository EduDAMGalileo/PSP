package procesos;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

public class ComprobadorDeOrdenes {

    public static void main(String[] args) {
        // Detectamos el sistema operativo para probar comandos acordes
        boolean esWindows = System.getProperty("os.name").toLowerCase().contains("win");

        // Probamos un comando que sabemos que existe y funciona bien
        List<String> ordenValida = esWindows 
            ? List.of("cmd.exe", "/c", "java", "-version") 
            : List.of("java", "-version");

        System.out.println("Probando orden válida...");
        String resultado1 = comprobar(ordenValida);
        System.out.println("Resultado: " + resultado1); 

        // Probamos un comando que NO existe (debe devolver "no instalado")
        List<String> ordenInexistente = List.of("comando-inventado-que-no-existe-12345");

        System.out.println("\nProbando orden inexistente...");
        String resultado2 = comprobar(ordenInexistente);
        System.out.println("Resultado: " + resultado2); 

        // Probamos un comando que falle con error (ej: pedir ayuda a un comando con argumento inválido)
        List<String> ordenConError = esWindows
            ? List.of("cmd.exe", "/c", "exit 5")
            : List.of("ls", "--argumento-que-no-existe");

        System.out.println("\nProbando orden con error de ejecución...");
        String resultado3 = comprobar(ordenConError);
        System.out.println("Resultado: " + resultado3); 
    }

    /** Devuelve la primera línea de un texto, sin espacios sobrantes. */
    static String primeraLinea(String texto) {
        int fin = texto.indexOf('\n');
        String linea = (fin == -1) ? texto : texto.substring(0, fin);
        return linea.strip();
    }

    /**
     * Ejecuta la orden indicada y devuelve su primera línea de salida,
     * o un texto que describe el problema encontrado.
     */
    static String comprobar(List<String> orden) {
        ProcessBuilder pb = new ProcessBuilder(orden);
        //	Une stdout y stderr en un solo flujo
        pb.redirectErrorStream(true);      
        Process p = null;
        try {
            p = pb.start();
            String salida;
            
            // Leemos toda la salida de golpe asegurando la codificación UTF-8
            try (InputStream in = p.getInputStream()) {
                salida = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }
            
            // Esperamos un máximo de 10 segundos a que termine de morir/cerrarse
            if (!p.waitFor(Duration.ofSeconds(10))) {
                return "no responde";
            }
            
            // Si el código de salida no es 0, hubo un error lógico en la herramienta
            if (p.exitValue() != 0) {
                return "error (código " + p.exitValue() + ")";
            }
            
            // Si todo fue bien, devolvemos solo la primera línea limpia
            return primeraLinea(salida);
            
        } catch (IOException e) {
            // Si el ejecutable no se encuentra en el sistema, lanza IOException (FileNotFound, etc.)
            return "no instalado";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "interrumpido";
        } finally {
            // Garantía absoluta: si el proceso se quedó colgado o vivo por lo que sea, lo matamos
            if (p != null && p.isAlive()) {
                p.destroyForcibly();
            }
        }
    }
}