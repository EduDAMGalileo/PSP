package ejercicios.cuaderno0;

import java.io.File; 
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.util.ArrayList; 
import java.util.List; 

public class Ej30 { 
	public static void main(String[] args) throws Exception { 
		int[] limites = {1_000_000, 2_000_000, 3_000_000, 4_000_000}; 
		Files.createDirectories(Path.of("res")); 

		long t0 = System.currentTimeMillis(); 
		List<Process> hijos = new ArrayList<>(); 

		for (int n : limites) { 
			ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Trabajador", 
					String.valueOf(n)); 
			//Cada uno en su fichero
			pb.redirectOutput(new File("res", "t" + n + ".txt")); 
			pb.redirectError(new File("res", "t" + n + ".err")); 
			hijos.add(pb.start()); 
		} 
		for (Process p : hijos) { 
			p.waitFor(); 
		} 

		int total = 0; 
		for (int n : limites) { 
			String linea = Files.readString(Path.of("res", "t" + n + ".txt")) 
					.strip(); 
			//Reconstruimos el fichero
			String[] partes = linea.split(";"); 
			int primos = Integer.parseInt(partes[1]); 
			System.out.println("Primos menores que " + partes[0] + ": " + primos); 
			total += primos; 
		} 
		System.out.println("Suma de los recuentos: " + total); 
		System.out.println("Tiempo total: " 
				+ (System.currentTimeMillis() - t0) + " ms"); 
	} 
}
