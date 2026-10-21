package ejercicios;

import java.io.IOException;

public class Ejecuta {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		ProcessBuilder pb = new ProcessBuilder(args);
		System.out.println(pb.command());

		pb.inheritIO();

		Process p = pb.start();  
		int codigo = p.waitFor();

		System.out.println("PID " + p.pid() + ", código " + codigo);
		
	}


}
