package ejercicios.cuaderno0;

import java.io.BufferedReader;

public class Ej17 {
    public static void main(String[] args) throws Exception {
        Process hijo = Lanzador.para("ejercicios.cuaderno0.Cuenta", "4").start();
        int n = 0;
        try (BufferedReader r = hijo.inputReader()) {
            String linea;
            // null = fin
            while ((linea = r.readLine()) != null) {   
                n++;
                System.out.println("Padre recibe (" + n + "): " + linea);
            }
        }
        System.out.println("Código: " + hijo.waitFor());
    }
}
