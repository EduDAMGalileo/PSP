package ejercicios.cuaderno0;

import java.nio.file.Files; 
import java.nio.file.Path; 
 
public class Ej25 { 
    public static void main(String[] args) throws Exception { 
        Files.deleteIfExists(Path.of("resultado.txt")); 
        int codigo = Lanzador.para("ejercicios.cuaderno0.Escribe", "100") 
                .inheritIO().start().waitFor(); 
        if (codigo != 0) { 
            System.out.println("El hijo falló; no leo nada"); 
            return; 
        } 
        long suma = Long.parseLong(Files.readString(Path.of("resultado.txt"))); 
        System.out.println("Padre: el hijo calculó " + suma); 
        System.out.println("Padre: el doble es " + suma * 2); 
    } 
} 