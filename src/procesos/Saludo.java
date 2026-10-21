package procesos;

import java.io.File;

public class Saludo {
    public static void main(String[] args) {
        // Si no recibe argumentos, termina con código de error 2
        if (args.length == 0) {
            System.err.println("Error: No se proporcionó ningún nombre.");
            System.exit(2);
        }

        // Imprime el saludo con el primer argumento
        System.out.println("Hola, " + args[0]);

        // Muestra el directorio de trabajo actual
        System.out.println("Directorio: " + new File(".").getAbsolutePath());

        // Lee y muestra una variable de entorno si el padre se la pasó
        String modo = System.getenv("MODO");
        if (modo != null) {
            System.out.println("MODO=" + modo);
        }
    }
}