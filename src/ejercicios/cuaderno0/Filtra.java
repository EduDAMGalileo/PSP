package ejercicios.cuaderno0;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
/** Deja pasar solo las líneas que contienen el texto indicado. */
public class Filtra {
	public static void main(String[] args) throws IOException {
		BufferedReader entrada = new BufferedReader(
				new InputStreamReader(System.in));
		String linea;
		while ((linea = entrada.readLine()) != null) {
			if (linea.contains(args[0])) {
				System.out.println(linea);
			}
		}
	}
}
