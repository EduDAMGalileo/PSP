package ejercicios.cuaderno0; 

import java.io.IOException; 
import java.io.RandomAccessFile; 
import java.nio.channels.FileChannel; 
import java.nio.channels.FileLock; 
import java.nio.charset.StandardCharsets; 

/** Como SumaUno, pero con un bloqueo de fichero en cada incremento. */ 
public class SumaUnoBloqueo { 
	public static void main(String[] args) throws IOException { 
		int veces = Integer.parseInt(args[0]); 

		try (RandomAccessFile f = new RandomAccessFile("contador.txt", "rw"); 
				FileChannel canal = f.getChannel()) { 
			for (int i = 0; i < veces; i++) { 
				// espera su turno
				FileLock cerrojo = canal.lock();       
				try { 
					f.seek(0); 
					String texto = f.readLine(); 
					int valor = (texto == null || texto.isBlank()) 
							? 0 : Integer.parseInt(texto.strip()); 
					f.setLength(0); 
					f.write(String.valueOf(valor + 1) 
							.getBytes(StandardCharsets.UTF_8)); 
				} finally { 
					// siempre se libera
					cerrojo.release();                 
				} 
			} 
		} 
	} 
}