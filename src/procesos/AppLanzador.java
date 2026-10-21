package procesos;

import java.io.File;
import java.io.IOException;

//No funciona en Windows, tengo que mirar por qué
public class AppLanzador {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("=== PRUEBA 1: Lanzando a Saludo con éxito ===");
        
        // Usamos nuestro Lanzador para preparar la orden: java -cp <path> procesos.Saludo Ana
        ProcessBuilder pb = Lanzador.para("procesos.Saludo", "Ana");
        
        // Modificamos el entorno y el directorio de trabajo del hijo
        pb.environment().put("MODO", "pruebas");
        
        // Dependiendo de tu S.O., ajusta el directorio temporal:
        // En Linux: "/tmp" | En Windows: "C:\\Windows\\Temp" o un directorio local existente
        File directorioTemporal = new File(System.getProperty("os.name").toLowerCase().contains("win") ? "C:\\Temp" : "/tmp");
        if (!directorioTemporal.exists()) {
            directorioTemporal.mkdirs(); // Creamos la carpeta si no existe
        }
        pb.directory(directorioTemporal);
        
        // inheritIO() hace que la salida (stdout) y error (stderr) del hijo 
        // se muestren directamente en nuestra propia consola.
        pb.inheritIO();

        Process p = pb.start();
        int codigo = p.waitFor();
        
        System.out.println("El proceso hijo terminó con código: " + codigo);


        System.out.println("\n=== PRUEBA 2: Lanzando a Saludo SIN argumentos (para ver el error) ===");
        
        // Llamamos al Lanzador pero sin pasarle ningún argumento ("Ana")
        ProcessBuilder pbError = Lanzador.para("procesos.Saludo");
        pbError.inheritIO(); // Para ver el mensaje de error por consola
        
        Process pError = pbError.start();
        int codigoError = pError.waitFor();
        
        System.out.println("El proceso hijo falló y devolvió el código: " + codigoError);
    }
}