package ejercicios.cuaderno0;

import java.util.ArrayList; 
import java.util.List; 

public class Ej28 { 
	public static void main(String[] args) throws Exception { 
		long t0 = System.currentTimeMillis(); 
		for (int i = 0; i < 3; i++) {                     // uno tras otro 
			Lanzador.para("ejercicios.cuaderno0.Duerme", "2").start().waitFor(); 
		} 
		System.out.println("Secuencial: " 
				+ (System.currentTimeMillis() - t0) + " ms"); 

		t0 = System.currentTimeMillis(); 
		List<Process> hijos = new ArrayList<>(); 
		for (int i = 0; i < 3; i++) {                     // lanzo los tres 
			hijos.add(Lanzador.para("ejercicios.cuaderno0.Duerme", "2").start()); 
		} 
		for (Process p : hijos) {                         // y luego espero 
			p.waitFor(); 
		} 
		System.out.println("Concurrente: " 
				+ (System.currentTimeMillis() - t0) + " ms"); 
	} 
}
