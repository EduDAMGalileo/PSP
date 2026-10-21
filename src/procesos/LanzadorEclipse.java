package procesos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class LanzadorEclipse {

    private LanzadorEclipse() {
    }

    /**
     * Prepara la ejecución de otra clase del proyecto, solucionando 
     * el problema del classpath de Eclipse y permitiendo depuración opcional.
     * 
     * @param clase        Nombre cualificado (ej: "ejercicios.cuaderno0.Saludo")
     * @param depurar      Si es true, congela al hijo esperando conexión en el puerto 8000
     * @param argumentos   Argumentos para el programa hijo
     */
    public static ProcessBuilder para(String clase, boolean depurar, String... argumentos) {
        // Obtener la ruta del ejecutable java correctamente
        String java = Path.of(System.getProperty("java.home"), "bin", "java").toString();

        // 2. Resolver el Classpath de forma robusta para Eclipse (Windows / Linux)
        String cpCrudo = System.getProperty("java.class.path");
        Path rutaCp = Path.of(cpCrudo).toAbsolutePath();

        // Si estamos en Eclipse y existe la carpeta "bin", la forzamos en el classpath
        if (Files.isDirectory(rutaCp.resolve("bin"))) {
            rutaCp = rutaCp.resolve("bin");
        }
        String cp = rutaCp.toString();

        // 3. Construir la lista de órdenes para el ProcessBuilder
        List<String> orden = new ArrayList<>();
        orden.add(java);

        // Si queremos depurar al hijo, inyectamos el agente JDWP antes de la clase [cite: 11.1]
        if (depurar) {
            orden.add("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:8000");
            System.out.println("[DEBUG HABILITADO] El proceso hijo esperará conexión en el puerto 8000...");
        }

        orden.add("-cp");
        orden.add(cp);
        orden.add(clase);
        orden.addAll(List.of(argumentos));

        // Chivato en consola para ver la orden exacta generada
        System.out.println("[LanzadorEclipse CMD] " + String.join(" ", orden));

        return new ProcessBuilder(orden);
    }
}