package procesos;

import java.util.Scanner;

public class ComunicacionHijo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Lee mientras haya datos en su entrada estándar
        while (sc.hasNextLine()) {
            String linea = sc.nextLine().trim();
            
            if (linea.isEmpty()) {
                continue; 
            }

            if (linea.equalsIgnoreCase("SALIR")) {
                System.out.println("HIJO: ¡Apagando sistema a petición del padre!");
                break;
            }

            // Procesamos la línea según lo que reciba
            System.out.println("HIJO [Procesado] -> Texto: '" + linea + "' | Caracteres: " + linea.length());
        }
        sc.close();
    }
}