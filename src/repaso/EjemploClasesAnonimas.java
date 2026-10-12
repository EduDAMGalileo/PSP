package repaso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EjemploClasesAnonimas {
    public static void main(String[] args) {

        // =========================================================================
        // CASO 1: Runnable con clase anónima
        // (Crear e implementar una interfaz de un solo uso sin hacer un .java aparte)
        // =========================================================================
        
        Runnable tarea = new Runnable() {
            @Override
            public void run() {
                System.out.println("-> Ejecutando la tarea desde la clase anónima.");
            }
        };

        // Ejecutamos el método directamente:
        tarea.run();



        // =========================================================================
        // CASO 2: Comparator con clase anónima
        // (Definir un criterio de ordenación puntual para una lista)
        // =========================================================================

        Comparator<String> porLongitud = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                // Compara los tamaños de las palabras:
                // negativo si 'a' es más corta, 0 si son iguales, positivo si 'a' es más larga
                return Integer.compare(a.length(), b.length());
            }
        };

        // Lista mutable con nombres de distintas longitudes:
        List<String> nombres = new ArrayList<>(List.of("Lucía", "Ana", "Bernardo"));

        System.out.println("\nLista antes de ordenar: " + nombres);

        // Le pasamos nuestro comparador anónimo al método sort:
        nombres.sort(porLongitud);

        System.out.println("Lista ordenada por longitud: " + nombres);
    }
}