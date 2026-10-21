package procesos;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Utilidades para lanzar otras JVM con la misma configuración. */
public final class Lanzador {

    private Lanzador() {
    }

    /** Ruta del ejecutable java de la JVM en ejecución. */
    public static String rutaJava() {
        return Path.of(System.getProperty("java.home"), "bin", "java")
                .toString();
    }

    /**
     * Prepara la ejecución de una clase con la misma JVM y el mismo
     * classpath, convertido a ruta absoluta.
     *
     * @param clase nombre cualificado de la clase con método main
     * @param argumentos argumentos para esa clase
     * @return el ProcessBuilder listo para configurar y lanzar
     */
    public static ProcessBuilder para(String clase,
            String... argumentos) {
        String cp = Path.of(System.getProperty("java.class.path"))
                .toAbsolutePath().toString();
        
        System.out.println("[DEBUG CLASSPATH CRUDO]: " + cp); // Para mirar qué valor exacto saca Eclipse
        
        List<String> orden = new ArrayList<>();
        orden.add(rutaJava());
        orden.add("-cp");
        orden.add(cp);
        orden.add(clase);
        orden.addAll(List.of(argumentos));
        
        System.out.println("[LANZADOR CMD] " + String.join(" ", orden));
        
        return new ProcessBuilder(orden);
    }
    
    /*
     * EJEMPLOS DE LA ORDEN COMPLETA EN UNA LÍNEA (lo que el ProcessBuilder ejecutará):
     * 
     * --- EJEMPLO 1 (Linux) con el argumento "Ana":
     * /usr/lib/jvm/java-25-openjdk/bin/java -cp /home/usuario/PSP-Practicas/bin procesos.Saludo Ana
     * 
     * --- EJEMPLO 2 (Windows) con el argumento "Ana":
     * C:\Program Files\Java\jdk-25\bin\java.exe -cp C:\Users\Eduardo\PSP-Practicas\bin procesos.Saludo Ana
     * 
     * --- EJEMPLO 3 (Linux) sin argumentos:
     * /usr/lib/jvm/java-25-openjdk/bin/java -cp /home/usuario/PSP-Practicas/bin procesos.Saludo
     */
}
