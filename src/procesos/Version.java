package procesos;

import java.io.IOException;

public class Version {
	public static void main(String[] args) throws IOException, InterruptedException {

		ProcessBuilder pb = new ProcessBuilder("java", "-version");
		// el hijo usa la consola del padre
		pb.inheritIO();          

		// aquí nace el proceso
		Process p = pb.start();  
		int codigo = p.waitFor();

		System.out.println("PID " + p.pid() + ", código " + codigo);
	
	}
}

