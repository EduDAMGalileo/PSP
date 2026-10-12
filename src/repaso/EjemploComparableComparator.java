package repaso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CuentaComparable implements Comparable<CuentaComparable> {
	private final String titular;
	private final double saldo;

	public CuentaComparable(String titular, double saldo) {
		this.titular = titular;
		this.saldo = saldo;
	}

	public String getTitular() { 
		return titular; 
	}
	public double getSaldo() {
		return saldo; 
	}

	@Override
	public int compareTo(CuentaComparable otra) {
		return Double.compare(this.saldo, otra.saldo);
	}

	@Override
	public String toString() {
		return titular + " (" + saldo + "€)";
	}
}

public class EjemploComparableComparator {
	public static void main(String[] args) {
		List<CuentaComparable> cuentas = new ArrayList<>(List.of(
				new CuentaComparable("Carlos", 300.0),
				new CuentaComparable("Ana", 50.0),
				new CuentaComparable("Beatriz", 150.0)
				));

		System.out.println("1. Lista original sin ordenar:");
		System.out.println(cuentas);

		Collections.sort(cuentas);
		System.out.println("\n2. Orden natural (por saldo):");
		System.out.println(cuentas);

		// Comparator por titular
		cuentas.sort((c1, c2) -> c1.getTitular().compareTo(c2.getTitular()));
		System.out.println("\n3. Orden con Comparator (por titular):");
		System.out.println(cuentas);
	}
}