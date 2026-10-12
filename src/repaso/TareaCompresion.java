package repaso;

public class TareaCompresion extends Tarea {

	protected TareaCompresion(String nombre) {
		super(nombre);
	}

	@Override
	public void ejecutar() {
		System.out.println("Comprimiendo archivo " + getNombre());

	}

}
