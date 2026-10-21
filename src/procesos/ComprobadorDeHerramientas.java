package procesos;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

public class ComprobadorDeHerramientas {

    // El Record (Estructura de datos simple para la herramienta y su orden)
    record Herramienta(String nombre, List<String> orden) { }

    public static void main(String[] args) {
        // Definimos la lista de herramientas que queremos comprobar en el sistema
        List<Herramienta> herramientas = List.of(
                new Herramienta("Java", List.of("java", "-version")),
                new Herramienta("Compilador", List.of("javac", "-version")),
                new Herramienta("Maven", List.of("mvn", "-v")),
                new Herramienta("Git", List.of("git", "--version")));

        // Recorremos la lista y formateamos la salida por pantalla
        for (Herramienta h : herramientas) {
            // %-12s alinea el texto a la izquierda dejando un ancho de 12 caracteres
            // %s imprime el resultado de la función comprobar()
            System.out.printf("%-12s %s%n", h.nombre(), comprobar(h.orden()));
        }
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
        pb.redirectErrorStream(true);      // Une la salida estándar y de error
        Process p = null;
        try {
            p = pb.start();
            String salida;
            
            // Leemos toda la salida del proceso
            try (InputStream in = p.getInputStream()) {
                salida = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }
            
            // Damos un límite de tiempo de 10 segundos
            if (!p.waitFor(Duration.ofSeconds(10))) {
                return "no responde";
            }
            
            // Si el código de salida no es 0, la herramienta falló
            if (p.exitValue() != 0) {
                return "error (código " + p.exitValue() + ")";
            }
            
            return primeraLinea(salida);
            
        } catch (IOException e) {
            // Si el ejecutable no se encuentra en el PATH del sistema
            return "no instalado";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "interrumpido";
        } finally {
            // Garantía de limpieza para que no queden procesos zombis o colgados
            if (p != null && p.isAlive()) {
                p.destroyForcibly();
            }
        }
    }
}