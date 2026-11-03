package ejercicios.cuaderno0; 
 
import java.nio.file.Files; 
import java.nio.file.Path; 
 
public class Ej27 { 
    public static void main(String[] args) throws Exception { 
        Path contador = Path.of("contador.txt"); 
        for (int intento = 1; intento <= 3; intento++) { 
            Files.writeString(contador, "0"); 
            Process a = Lanzador.para("ejercicios.cuaderno0.SumaUnoBloqueo", "1000").start(); 
            Process b = Lanzador.para("ejercicios.cuaderno0.SumaUnoBloqueo", "1000").start(); 
            a.waitFor(); 
            b.waitFor(); 
            String fin = Files.readString(contador).strip(); 
            System.out.println("Intento " + intento 
                    + ": esperaba 2000, hay " + fin); 
        } 
    } 
} 