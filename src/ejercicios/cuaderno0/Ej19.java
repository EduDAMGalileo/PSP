package ejercicios.cuaderno0;

import java.io.BufferedReader;

public class Ej19 {
    public static void main(String[] args) throws Exception {
        long t0 = System.currentTimeMillis();
        Process hijo = Lanzador.para("ejercicios.cuaderno0.Cuenta", "10000000").start();
        long lineas = 0;
        try (BufferedReader r = hijo.inputReader()) {
        	// leer vacía la tubería, esto es un Stram, sustituye al bucle típico
        	lineas = r.lines().count();        
        	/*
        	 La alternativa es esta: 
               while ((linea = r.readLine()) != null) {
                    lineas++;
                    // Al no guardar la variable 'linea' en ninguna lista, 
                    // el recolector de basura de Java la libera de la memoria RAM inmediatamente.
                }
            }*/
        }
        // ahora sí termina
        int codigo = hijo.waitFor();           
        long ms = System.currentTimeMillis() - t0;
        System.out.println("Líneas: " + lineas + ", código " + codigo
                + ", " + ms + " ms");
    }
}
