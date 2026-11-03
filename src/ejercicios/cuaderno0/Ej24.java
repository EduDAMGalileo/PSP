package ejercicios.cuaderno0;

import java.io.BufferedReader; 
import java.util.List; 
 
public class Ej24 { 
    public static void main(String[] args) throws Exception { 
        ProcessBuilder cuenta = Lanzador.para("ejercicios.cuaderno0.Cuenta", "20"); 
        ProcessBuilder filtra = Lanzador.para("ejercicios.cuaderno0.Filtra", "1"); 
 
        List<Process> tuberia = ProcessBuilder.startPipeline( 
                List.of(cuenta, filtra)); 
        Process ultimo = tuberia.get(tuberia.size() - 1); 
 
        try (BufferedReader r = ultimo.inputReader()) { 
            r.lines().forEach(System.out::println); 
           /* while ((linea = r.readLine()) != null) {
                System.out.println(linea); }
                */
        } 
        for (Process p : tuberia) { 
            System.out.println("PID " + p.pid() + " -> código " + p.waitFor()); 
        } 
    } 
} 