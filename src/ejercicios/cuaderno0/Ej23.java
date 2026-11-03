package ejercicios.cuaderno0;

import java.io.BufferedReader; 

public class Ej23 { 
    public static void main(String[] args) throws Exception { 
        System.out.println("--- Solo getInputStream, sin unir"); 
        Process a = Lanzador.para("ejercicios.cuaderno0.Mixto").start(); 
        try (BufferedReader r = a.inputReader()) { 
            r.lines().forEach(l -> System.out.println("leído: " + l)); 
            /*
            while ((l = r.readLine()) != null) {
                System.out.println("leído: " + l);
            }*/
        } 
        a.waitFor(); 
 
        System.out.println("--- Con redirectErrorStream(true)"); 
        ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Mixto"); 
        pb.redirectErrorStream(true); 
        Process b = pb.start(); 
        try (BufferedReader r = b.inputReader()) { 
            r.lines().forEach(l -> System.out.println("leído: " + l)); 
        } 
        b.waitFor(); 
    } 
} 
