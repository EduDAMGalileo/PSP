package ejercicios.cuaderno0;

import java.io.File;

public class Ej09 {
    public static void main(String[] args) throws Exception {
        // Creamos un archivo de prueba para asegurarnos de que exista un .txt
    	// Lo creo con el modo antiguo, con File de java.io
        File archivoPrueba = new File("prueba_ejercicio.txt");
        archivoPrueba.createNewFile();
        
        System.out.println("--- Sin intérprete de órdenes");
        ProcessBuilder a = new ProcessBuilder("ls", "*.txt");
        int ca = a.inheritIO().start().waitFor();
        System.out.println("Código: " + ca);

        System.out.println("--- Con sh -c");
        ProcessBuilder b = new ProcessBuilder("sh", "-c", "ls *.txt");
        int cb = b.inheritIO().start().waitFor();
        System.out.println("Código: " + cb);
        
        // Limpieza: borramos el archivo de prueba al terminar
        archivoPrueba.delete();
    }
}
