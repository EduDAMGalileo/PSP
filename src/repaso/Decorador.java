package repaso;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Decorador {

	public static void main(String[] args) throws FileNotFoundException {
		
		// lee bytes del fichero
		InputStream bytes = new FileInputStream("datos.txt");
		// convierte bytes en caracteres
		Reader texto = new InputStreamReader(bytes, StandardCharsets.UTF_8);
		// añade búfer y readLine()
		BufferedReader lector = new BufferedReader(texto);

	}

}
