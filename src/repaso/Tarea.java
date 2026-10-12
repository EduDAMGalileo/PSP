package repaso;

public abstract class Tarea {
	private final String nombre;

	protected Tarea(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() { 
		return nombre; 
	}
	
	// cada subclase decide cómo
	public abstract void ejecutar();      

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + nombre + "]";
	}
}


