package repaso;

import java.util.ArrayList;
import java.util.List;

public class AppTareas {

	public static void main(String[] args) {
		List<Tarea> pendientes = new ArrayList<>();
		pendientes.add(new TareaCopia("a.txt", "copia/a.txt"));
		pendientes.add(new TareaCompresion("informe.pdf"));

		for (Tarea t : pendientes) {
			// cada objeto ejecuta SU versión
		    t.ejecutar();    
		    
		    // copia ya es TareaCopia
		    if (t instanceof TareaCopia copia) {
		        System.out.println(copia.getDestino());  
		    }

		}


	}

}
