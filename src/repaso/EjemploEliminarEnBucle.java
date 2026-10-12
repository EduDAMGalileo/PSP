package repaso;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class EjemploEliminarEnBucle {
	public static void main(String[] args) {

		// =====================================================================
		// CASO 1: El error que explota (for-each)
		// El for-each usa un Iterator oculto; si modificas la lista directamente,
		// detecta la incoherencia y salta por los aires.
		// =====================================================================
		System.out.println("=== CASO 1: for-each con modificación directa ===");
		List<Integer> notas1 = new ArrayList<>(List.of(3, 7, 4, 9));

		try {
			for (Integer n : notas1) {
				if (n < 5) {
					//Lanza ConcurrentModificationException
					notas1.remove(n); 
				}
			}
		} catch (ConcurrentModificationException e) {
			System.err.println("¡Excepción atrapada!: ConcurrentModificationException");
			System.err.println("No se puede modificar una lista directamente dentro de un for-each.");
		}



		// =====================================================================
		// CASO 2: El error silencioso (bucle for clásico por índices)
		// NO da error, pero el resultado es INCORRECTO.
		// =====================================================================
		System.out.println("\n=== CASO 2: Bucle for por índices (el fallo silencioso) ===");
		// Ponemos dos suspensos seguidos a propósito: 3 y 4
		List<Integer> notas2 = new ArrayList<>(List.of(3, 4, 7, 9));
		System.out.println("Lista inicial: " + notas2);

		for (int i = 0; i < notas2.size(); i++) {
			if (notas2.get(i) < 5) {
				notas2.remove(i);
			}
		}

		// El '4' se ha librado porque al borrar el '3' en la posición 0,
		// el '4' se movió a la posición 0, pero el bucle avanzó a i = 1 (el 7).
		System.out.println("Lista tras borrar: " + notas2); 
		System.out.println("¡El 4 sigue ahí! El bucle se lo saltó al desplazar índices.");



		// =====================================================================
		// CASO 3: La solución correcta con Iterator explícito
		// El método it.remove() sí actualiza el puntero interno del recorrido.
		// =====================================================================
		System.out.println("\n=== CASO 3: La forma correcta con Iterator ===");
		List<Integer> notas3 = new ArrayList<>(List.of(3, 4, 7, 9));
		System.out.println("Lista inicial: " + notas3);

		Iterator<Integer> it = notas3.iterator();
		while (it.hasNext()) {
			if (it.next() < 5) {
				// Elimina de forma segura y coherente
				it.remove(); 
			}
		}

		System.out.println("Lista final limpia: " + notas3); 



		// =====================================================================
		// EXTRA (Java 8+): La forma moderna en una sola línea (removeIf)
		// Por debajo usa exactamente el Iterator del Caso 3.
		// =====================================================================
		System.out.println("\n=== EXTRA: La forma moderna con removeIf() ===");
		List<Integer> notas4 = new ArrayList<>(List.of(3, 4, 7, 9));

		// Expresión lambda
		notas4.removeIf(n -> n < 5); 

		System.out.println("Lista con removeIf: " + notas4);
	}
}
