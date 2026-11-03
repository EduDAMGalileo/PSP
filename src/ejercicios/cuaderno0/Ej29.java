package ejercicios.cuaderno0;

import java.util.LinkedHashMap; 
import java.util.Map; 
 
public class Ej29 { 
    public static void main(String[] args) throws Exception { 
        String[] codigos = {"0", "2", "0", "1"}; 
        //Necesitamos un mapa enlazado, para mantener el orden de inserción
        Map<String, Process> tareas = new LinkedHashMap<>(); 
        for (int i = 0; i < codigos.length; i++) { 
            ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Sale", codigos[i]); 
            //Nos da igual lo que salga, pasamos de las salidas
            //No sale en la consola del padre igualmente, pero se queda por ahí en Buffers
            pb.redirectOutput(ProcessBuilder.Redirect.DISCARD); 
            tareas.put("tarea-" + (i + 1), pb.start()); 
        } 
        int fallos = 0; 
        //Recorremos el mapa
        for (Map.Entry<String, Process> e : tareas.entrySet()) { 
            int c = e.getValue().waitFor(); 
            if (c == 0) { 
                System.out.println(e.getKey() + ": correcta"); 
            } else { 
                System.out.println(e.getKey() + ": FALLÓ con código " + c); 
                fallos++; 
            } 
        } 
        System.out.println("Resumen: " + (tareas.size() - fallos) + " bien, " 
                + fallos + " mal"); 
        System.exit(fallos == 0 ? 0 : 1); 
    } 
} 