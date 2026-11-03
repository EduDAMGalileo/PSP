package ejercicios.cuaderno0;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.io.PrintWriter; 
import java.nio.charset.StandardCharsets; 
 
/** Responde a cada línea en cuanto la recibe. FIN termina. */ 
public class Eco { 
    public static void main(String[] args) throws IOException { 
        BufferedReader entrada = new BufferedReader( 
                new InputStreamReader(System.in, StandardCharsets.UTF_8)); 
        PrintWriter salida = new PrintWriter(System.out, true, 
                StandardCharsets.UTF_8);          // true: vacía en cada println 
        String linea; 
        while ((linea = entrada.readLine()) != null) { 
            if (linea.equals("FIN")) { 
                salida.println("ADIOS"); 
                return; 
            } 
            salida.println("Has dicho: " + linea); 
        } 
    } 
} 