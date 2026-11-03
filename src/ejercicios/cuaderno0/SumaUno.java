package ejercicios.cuaderno0;

import java.io.IOException; 
import java.nio.file.Files; 
import java.nio.file.Path; 
/** Suma 1 al número de contador.txt, tantas veces como se indique. */ 

public class SumaUno { 
	public static void main(String[] args) throws IOException { 
		int veces = Integer.parseInt(args[0]); 
		Path fichero = Path.of("contador.txt"); 
		for (int i = 0; i < veces; i++) { 
			String texto = Files.readString(fichero).strip(); 
			int valor = texto.isEmpty() ? 0 : Integer.parseInt(texto); 
			Files.writeString(fichero, String.valueOf(valor + 1)); 
		} 
	} 
}