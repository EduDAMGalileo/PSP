package repaso;


//CREAMOS UN RECURSO PROPIO (Para no tener 6000 ficheros)
//Para poder usarse en el paréntesis del try(...), solo necesita 'implements AutoCloseable'
class ConexionRemota implements AutoCloseable {
	private final String nombre;

	public ConexionRemota(String nombre) {
		this.nombre = nombre;
		System.out.println("  [+] ABIERTO: " + nombre);
	}

	public void transferir(String datos) {
		System.out.println("  -> Transfiriendo '" + datos + "' mediante " + nombre);
	}

	@Override
	public void close() {
		// Este mensaje demostrará cuándo se ejecuta el cierre
		System.out.println("  [-] CERRADO: " + nombre + " liberado correctamente.");
	}
}

//CLASE PRINCIPAL
public class EjemploTryWithResources {
	public static void main(String[] args) {

		// =====================================================================
		// CASO 1: Terminación normal (demuestra el cierre en ORDEN INVERSO)
		// =====================================================================
		System.out.println("=== CASO 1: Todo funciona bien ===");

		//Simulamos unas conexiones
		try (ConexionRemota conexion1 = new ConexionRemota("Conexión-1 (BD)");
				ConexionRemota conexion2 = new ConexionRemota("Conexión-2 (Servidor Web)")) {

			conexion1.transferir("SELECT * FROM usuarios");
			conexion2.transferir("POST /api/login");

			System.out.println("  [!] Fin del cuerpo del try.");

		} // ¡AQUÍ Java llama automáticamente a close() para conexion2 y luego conexion1!

		System.out.println("=== Fuera del try-with-resources ===\n");



		// =====================================================================
		// CASO 2: Ocurre un fallo en mitad del try (demuestra que NO hay fugas)
		// =====================================================================
		System.out.println("=== CASO 2: Salta una excepción a mitad del bloque ===");

		try (ConexionRemota conexionCritica = new ConexionRemota("Canal Seguro")) {

			conexionCritica.transferir("Primer paquete...");

			// Provocamos un fallo inesperado (por ejemplo, corte de luz o error de red)
			throw new RuntimeException("¡Fallo catastrófico de red!");

			// Esta línea nunca se alcanzará:
			// conexionCritica.transferir("Segundo paquete...");

		} catch (RuntimeException e) {
			// Fíjate en la consola: el recurso ya estará CERRADO antes de entrar a este catch
			System.err.println("  [ERROR ATRAPADO]: " + e.getMessage());
		}

		System.out.println("=== Fin del programa de forma segura ===");
	}
}