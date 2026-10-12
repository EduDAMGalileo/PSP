package repaso;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

// Usamos el record que vimos en el apartado 1.7:
record Cliente(String nombre) {}

public class EscrituraFicheros {
    public static void main(String[] args) {
        Path ruta = Path.of("salida_clientes.txt");

        try {
            // =================================================================
            // Files.writeString (Comportamiento por defecto: SOBRESCRIBIR)
            // Crea el fichero si no existe, o BORRA todo lo que había si ya existía.
            // =================================================================
            System.out.println("=== Forma 1: Escribir primera línea (Sobrescritura) ===");
            
            // Usamos System.lineSeparator() para usar el salto de línea del SO local
            Files.writeString(ruta, "Primera línea (Cabecera)" + System.lineSeparator(),
                    StandardCharsets.UTF_8);

            mostrarContenidoFichero(ruta);


            // =================================================================
            // writeString con StandardOpenOption.APPEND (AÑADIR AL FINAL)
            // No borra el archivo: sitúa el cursor al final y escribe.
            // =================================================================
            System.out.println("\n=== Forma 2: Añadir al final (APPEND) ===");
            
            // Aquí usamos "\n" fijo, muy habitual en protocolos de red
            Files.writeString(ruta, "Segunda línea añadida después\n", StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            mostrarContenidoFichero(ruta);


            // =================================================================
            // BufferedWriter (Para escribir muchos registros en bucle)
            // Al no poner APPEND, vuelve a vaciar el fichero para escribir la lista.
            // =================================================================
            System.out.println("\n=== Forma 3: Escribir lista de objetos con BufferedWriter ===");
            
            List<Cliente> clientes = List.of(
                new Cliente("Edu Gómez"),
                new Cliente("Carlos Ruiz"),
                new Cliente("Beatriz López")
            );

            // También podemos pasarle StandardOpenOption.APPEND si quisiéramos añadir
            try (BufferedWriter escritor = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {
                for (Cliente c : clientes) {
                    escritor.write(c.nombre());
                    escritor.newLine(); // Inserta el salto de línea del sistema
                }
            } // El try-with-resources llama automáticamente a flush() y close()

            mostrarContenidoFichero(ruta);

        } catch (IOException e) {
            System.err.println("Error de escritura en el fichero: " + e.getMessage());
        }
    }

    // Método auxiliar para inspeccionar el fichero sin tener que abrirlo a mano
    private static void mostrarContenidoFichero(Path ruta) throws IOException {
        System.out.println("--- Contenido actual de " + ruta.getFileName() + " ---");
        String texto = Files.readString(ruta, StandardCharsets.UTF_8);
        System.out.print(texto);
        System.out.println("----------------------------------------------");
    }
}