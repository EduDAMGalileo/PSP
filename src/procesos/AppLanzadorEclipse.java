package procesos;

import java.io.IOException;

public class AppLanzadorEclipse {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("=== LANZANDO HIJO EN MODO DEPURACIÓN ===");

        // Llamamos a nuestro LanzadorEclipse pasando 'true' en el segundo parámetro
        ProcessBuilder pb = LanzadorEclipse.para("procesos.Saludo", false, "Ana");
        
        // Heredamos la E/S para ver sus mensajes por consola
        pb.inheritIO();

        // Arrancamos el proceso
        Process p = pb.start();

        System.out.println("[Padre] Hijo lanzado. Ve a Eclipse -> Debug Configurations -> Remote Java Application (Puerto 8000) para conectarte.");

        // Esperamos a que termine
        int codigo = p.waitFor();
        System.out.println("[Padre] El hijo terminó con código: " + codigo);
    }
}