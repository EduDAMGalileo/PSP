package repaso;

import java.util.Objects;

//'final' para que nadie pueda heredar de ella y romper la inmutabilidad
//Todo este código hace lo mismo que Puntorecord
public final class PuntoClase {
	// Atributos 'private final': una vez asignados, nadie los puede cambiar
	private final int x;
	private final int y;

	// 2. Constructor para inicializarlos obligatoriamente
	public PuntoClase(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// Métodos de lectura (en los records no llevan "get", se llaman como el campo)
	public int x() { 
		return x; 
	}

	public int y() { 
		return y; 
	}

	// 4. toString para que no imprima Punto@0x1a2b
	@Override
	public String toString() {
		return "Punto[x=" + x + ", y=" + y + "]";
	}

	// 5. equals y hashCode para comparar por valores y no por referencia de memoria
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PuntoClase otro)) {
			return false;
		}

		return this.x == otro.x && this.y == otro.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}