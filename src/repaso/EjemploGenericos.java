package repaso;

import java.util.List;

public class EjemploGenericos {

    // CASO 3 MÉTODO GENÉRICO
    // El <T> antes del tipo de retorno avisa: "este método funciona con listas de cualquier tipo"
    public static <T> T primero(List<T> lista) {
        if (lista == null || lista.isEmpty()) {
            return null;
        }
       // Devuelve el primer elemento respetando su tipo
        return lista.get(0); 
    }

    public static void main(String[] args) {

        // =====================================================================
        //  CASO 1: Reutilizar la misma clase con tipos completamente distintos
        // =====================================================================
        
        // Caja especializada en Strings:
        Caja<String> cajaDeTexto = new Caja<>();
        cajaDeTexto.guardar("Mensaje secreto");
        // No hace falta hacer cast (String), Java ya sabe que sale un String:
        String texto = cajaDeTexto.sacar(); 
        System.out.println("En la caja de texto hay: " + texto);

        // Caja especializada en Enteros:
        Caja<Integer> cajaDeNumeros = new Caja<>();
        cajaDeNumeros.guardar(42);
        int numero = cajaDeNumeros.sacar(); 
        System.out.println("En la caja de números hay: " + numero);


        // =====================================================================
        // CASO 2: Inferencia de tipos con el método genérico
        // =====================================================================
        System.out.println("\n--- Probando el método genérico primero() ---");

        List<String> nombres = List.of("Eduardo", "Carlos", "Beatriz");
        // Java deduce automáticamente que T es String:
        String primerNombre = primero(nombres);
        System.out.println("Primer nombre: " + primerNombre); // Ana

        List<Double> temperaturas = List.of(21.5, 23.0, 19.8);
        // El MISMO método ahora deduce que T es Double:
        Double primeraTemp = primero(temperaturas);
        System.out.println("Primera temperatura: " + primeraTemp); // 21.5
    }
}
