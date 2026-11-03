package ejercicios.cuaderno0;

import java.io.IOException; 
import java.nio.file.Files; 
import java.nio.file.Path; 
 
/** Calcula la suma de 1 a n y la deja en resultado.txt. */ 
public class Escribe { 
    public static void main(String[] args) throws IOException { 
        long n = Long.parseLong(args[0]); 
        long suma = 0; 
        for (long i = 1; i <= n; i++) { 
            suma += i; 
        } 
        Files.writeString(Path.of("resultado.txt"), String.valueOf(suma)); 
    } 
} 