package repaso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EjemploMap {
    public static void main(String[] args) {
        
        // Simulamos un registro de peticiones o accesos al servidor:
        List<String> registro = List.of("ana", "pepe", "ana", "lucia", "pepe", "ana");

        // CREACIÓN DEL MAPA
        // Clave (Key): String (nombre del usuario, debe ser único)
        // Valor (Value): Integer (contador de visitas)
        Map<String, Integer> visitas = new HashMap<>();

        //CONTEO DE VISITAS CON getOrDefault
        for (String usuario : registro) {
            // getOrDefault busca al usuario; si aún no existe en el mapa, devuelve 0.
            // Le sumamos 1 y guardamos con put.
            visitas.put(usuario, visitas.getOrDefault(usuario, 0) + 1);
        }

        // RECORRER EL MAPA CON entrySet()
        // Un 'Entry' representa cada pareja [clave -> valor]
        System.out.println("=== RECUENTO FINAL DE VISITAS ===");
        for (Map.Entry<String, Integer> e : visitas.entrySet()) {
            System.out.println("Usuario: " + e.getKey() + " | Visitas: " + e.getValue());
        }

        // MÉTODOS HABITUALES DE CONSULTA Y GESTIÓN
        System.out.println("\n=== OPERACIONES CON EL MAPA ===");

        // containsKey: comprueba si una clave ya está registrada (operación casi instantánea)
        System.out.println("¿Existe 'ana'?   " + visitas.containsKey("ana"));  
        System.out.println("¿Existe 'mario'? " + visitas.containsKey("mario")); 

        // keySet(): devuelve todas las claves
        System.out.println("Todas las claves (usuarios): " + visitas.keySet()); 

        // values(): devuelve todos los valores
        System.out.println("Todos los valores (visitas):  " + visitas.values()); 

        // remove(): elimina una pareja a partir de su clave
        System.out.println("\nEliminando a 'ana' del mapa...");
        visitas.remove("ana");

        System.out.println("Mapa resultante: " + visitas);
    }
}