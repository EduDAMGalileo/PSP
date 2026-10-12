package repaso;

public class  TareaCopia extends Tarea {
	private final String origen, destino;

	public TareaCopia(String origen, String destino) {
		// llama al constructor de Tarea
		super("copia " + origen);          
		this.origen = origen;
		this.destino = destino;
	}

	public String getDestino() { 
		return destino; 
	}

	@Override
	public void ejecutar() {
		System.out.println("Copiando " + origen + " en " + destino);
	}
}

