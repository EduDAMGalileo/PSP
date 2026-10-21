package procesos;

import java.util.Scanner;

public class FiltroNombres {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numeroLinea = 0;

        while (scanner.hasNextLine()) {
            numeroLinea++;
            String linea = scanner.nextLine().trim();

            // Simulamos un fallo si la línea está vacía o es un número
            if (linea.isEmpty()) {
                System.err.println("[ERROR] Línea " + numeroLinea + ": ¡Se encontró una línea en blanco!");
            } else if (linea.matches("\\d+")) {
                System.err.println("[ERROR] Línea " + numeroLinea + ": El valor '" + linea + "' no es un nombre válido.");
            } else {
                // Todo correcto, va a la salida estándar
                System.out.println("OK -> Hola " + linea);
            }
        }
        scanner.close();
    }
}