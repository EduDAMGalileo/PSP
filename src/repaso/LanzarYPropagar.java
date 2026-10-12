package repaso;

//Archivo: EjemploLanzarYPropagar.java

//1. EXCEPCIÓN COMPROBADA (Checked Exception)
//Al heredar de 'Exception', Java OBLIGA a declararla con 'throws' y a capturarla con 'try-catch'
class SaldoInsuficienteException extends Exception {

	private static final long serialVersionUID = 1L;

	public SaldoInsuficienteException(double saldo, double cantidad) {
		super("Saldo insuficiente: tienes " + saldo + "€ pero intentas retirar " + cantidad + "€");
	}
}

//2. LA CLASE CUENTA
class CuentaBancaria {
	private double saldo;

	public CuentaBancaria(double saldoInicial) {
		this.saldo = saldoInicial;
	}

	// 'throws': Es el cartel de aviso en la puerta del método.
	// Avisa a quien llame: "Ojo, este método puede fallar con SaldoInsuficienteException".
	public void retirar(double cantidad) throws SaldoInsuficienteException {

		// Caso 1: Excepción NO comprobada (Unchecked / RuntimeException)
		// No hace falta ponerla en el 'throws'. Indica un error del programador.
		if (cantidad <= 0) {
			throw new IllegalArgumentException("Cantidad no válida: " + cantidad);
		}

		// Caso 2: Excepción COMPROBADA (Checked)
		// 'throw' (en singular) es la acción de tirar la granada / lanzar el error.
		if (cantidad > saldo) {
			throw new SaldoInsuficienteException(saldo, cantidad);
		}

		saldo -= cantidad;
		System.out.println("-> Retirada con éxito de " + cantidad + "€. Saldo restante: " + saldo + "€");
	}

	public double getSaldo() {
		return saldo;
	}
}

//3. CLASE PRINCIPAL EJECUTABLE
public class LanzarYPropagar {
	public static void main(String[] args) {
		CuentaBancaria miCuenta = new CuentaBancaria(100.0);

		// PRUEBA 1: Operación normal
		System.out.println("--- PRUEBA 1: Retirada correcta ---");
		try {
			miCuenta.retirar(40.0); // Quedan 60€
		} catch (SaldoInsuficienteException e) {
			System.err.println("Error: " + e.getMessage());
		}

		// PRUEBA 2: Intentar sacar más de lo que hay (Checked Exception)
		System.out.println("\n--- PRUEBA 2: Saldo insuficiente ---");
		try {
			// Java NO te deja escribir esta línea sin rodearla de try-catch o propagarla:
			miCuenta.retirar(80.0); 
		} catch (SaldoInsuficienteException e) {
			System.err.println("Capturada excepción controlada: " + e.getMessage());
		}

		// PRUEBA 3: Parámetro absurdo (Unchecked Exception)
		System.out.println("\n--- PRUEBA 3: Cantidad negativa ---");
		try {
			miCuenta.retirar(-20.0);
		} catch (IllegalArgumentException e) {
			System.err.println("Capturada excepción de argumento: " + e.getMessage());
		} catch (SaldoInsuficienteException e) {
			System.err.println("Error de saldo: " + e.getMessage());
		}
	}
}
