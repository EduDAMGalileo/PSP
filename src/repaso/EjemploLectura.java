package repaso;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class EjemploLectura {

	private static void procesar(int numLinea, String linea) {
		System.out.println("  [Línea " + numLinea + "] Procesando cliente: " + linea.toUpperCase());
	}

	public static void main(String[] args) {
		Path ruta = Path.of("clientes.txt");

		try {
			// Creamos datos de prueba si el archivo no existe aún.
			// Metemos una línea vacía en medio para comprobar que el 'isBlank()' funciona.
			if (!Files.exists(ruta)) {
				String contenidoPrueba = "Edu Gómez\n\nCarlos Ruiz\nBeatriz López\n";
				Files.writeString(ruta, contenidoPrueba, StandardCharsets.UTF_8);
				System.out.println("(Se ha generado 'clientes.txt' con datos de ejemplo)\n");
			}

			// =================================================================
			// ENFOQUE 1: Files.readAllLines
			// Carga TODO el fichero de golpe en la memoria RAM en una List<String>.
			// Ideal para ficheros pequeños de configuración.
			// =================================================================
			List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
			System.out.println("=== ENFOQUE 1: readAllLines ===");
			System.out.println("Líneas totales cargadas en memoria: " + lineas.size());
			System.out.println("Contenido de la lista: " + lineas);


			// =================================================================
			// ENFOQUE 2: BufferedReader (Lectura bajo demanda)
			// Solo carga en memoria una línea a la vez. Es la forma obligatoria
			// si el fichero tiene millones de registros o proviene de la red.
			// =================================================================
			System.out.println("\n=== ENFOQUE 2: BufferedReader línea a línea ===");
			try (BufferedReader lector =
					Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {

				String linea;
				int n = 0;

				// readLine() devuelve la siguiente línea, o null cuando llega al final del archivo (EOF)
				while ((linea = lector.readLine()) != null) {
					n++;

					// Si la línea está vacía o solo contiene espacios, la saltamos:
					if (linea.isBlank()) {
						System.out.println("  [Línea " + n + "] (Línea vacía ignorada)");
						continue;
					}

					procesar(n, linea);
				}
			}

		} catch (IOException e) {
			System.err.println("Error al acceder al fichero: " + e.getMessage());
		}
	}
}



