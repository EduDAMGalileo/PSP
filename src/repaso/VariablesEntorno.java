package repaso;


import java.util.Map;

public class VariablesEntorno {

    public static void main(String[] args) {
        // Detectar automáticamente el Sistema Operativo
        String osName = System.getProperty("os.name");
        boolean esLinux = osName.toLowerCase().contains("linux");

        imprimirCabecera(osName, esLinux);
        imprimirPropiedadesJava();
        imprimirVariablesSistema(esLinux);
    }

    /**
     * Muestra la información básica del entorno detectado
     */
    private static void imprimirCabecera(String osName, boolean esLinux) {
        System.out.println("=== DETECCIÓN DE ENTORNO ===");
        System.out.println("Sistema Operativo:           " + osName);
        System.out.println("Versión de la JVM:           " + System.getProperty("java.version"));
        System.out.println("¿Es entorno Linux?:          " + (esLinux ? "SÍ" : "NO"));
        System.out.println("============================\n");
    }

    /**
     * Lee las propiedades internas de la Máquina Virtual de Java (JVM)
     */
    private static void imprimirPropiedadesJava() {
        System.out.println("--- PROPIEDADES DE JAVA (JVM) ---");
        System.out.println("Java Home:            " + System.getProperty("java.home"));
        System.out.println("Directorio de Trabajo:" + System.getProperty("user.dir"));
        System.out.println("Usuario JVM:          " + System.getProperty("user.name"));
        System.out.println();
    }

    /**
     * Detecta y extrae las variables del Sistema Operativo anfitrión
     */
    private static void imprimirVariablesSistema(boolean esLinux) {
        System.out.println("--- VARIABLES DE ENTORNO DEL SISTEMA ---");
        
        if (esLinux) {
            System.out.println("[INFO] Extrayendo variables nativas de Linux/Unix...");
            
            String shell = System.getenv("SHELL");
            String user = System.getenv("USER");
            String home = System.getenv("HOME");
            
            System.out.println("Shell activa ($SHELL): " + (shell != null ? shell : "No definida"));
            System.out.println("Usuario ($USER):       " + (user != null ? user : "No definido"));
            System.out.println("Carpeta raíz ($HOME):  " + (home != null ? home : "No definida"));
        } else {
            System.out.println("[INFO] Entorno Windows o no-Linux detectado. Extrayendo variables comunes...");
            
            String username = System.getenv("USERNAME");
            String computerName = System.getenv("COMPUTERNAME");
            
            System.out.println("Usuario Windows:       " + username);
            System.out.println("Nombre del Equipo:     " + computerName);
        }
        
        // Muestra de forma genérica el PATH independientemente del SO
        String path = System.getenv("PATH");
        if (path != null) {
            // Cortamos el PATH para que no sature la consola
            String pathCorto = path.substring(0, Math.min(path.length(), 60));
            System.out.println("Ruta del sistema (PATH): " + pathCorto + "...");
        }
    }
    
    /**
     * Método utilitario opcional por si necesitas volcar absolutamente todas 
     * las variables de entorno activas en el sistema.
     */
    public static void volcarTodoElEntorno() {
        Map<String, String> variables = System.getenv();
        for (Map.Entry<String, String> entrada : variables.entrySet()) {
            System.out.println(entrada.getKey() + " = " + entrada.getValue());
        }
    }
}