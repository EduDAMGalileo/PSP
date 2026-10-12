package repaso;


import java.nio.file.Path;

public class EjemploRutasPath {
    public static void main(String[] args) {

        // =====================================================================
        // CREACIÓN INDEPENDIENTE DEL SISTEMA OPERATIVO
        // Al pasar cada carpeta como un parámetro separado, Java pone automáticamente:
        //   - En Windows: datos\entrada.txt  (barra invertida \)
        //   - En Linux/Mac: datos/entrada.txt (barra inclinada /)
        // =====================================================================
        Path ruta = Path.of("datos", "entrada.txt");

        System.out.println("=== PARTES DE LA RUTA ===");
        System.out.println("Ruta relativa: " + ruta);
        System.out.println("Nombre del fichero: " + ruta.getFileName()); 
        System.out.println("Carpeta padre: " + ruta.getParent());   

        // toAbsolutePath() calcula la ruta real completa desde la raíz de tu disco
        // tomando como base la carpeta donde tienes abierto el proyecto:
        System.out.println("Ruta absoluta completa: " + ruta.toAbsolutePath());



        // =====================================================================
        // CONSTRUIR RUTAS DINÁMICAMENTE CON resolve()
        // resolve() equivale a concatenar rutas asegurando que las barras queden bien.
        // Es como hacer un 'cd' entrando en subcarpetas.
        // =====================================================================
        System.out.println("\n=== NAVEGACIÓN Y CONSTRUCCIÓN CON resolve() ===");

        Path baseProyecto = Path.of("proyecto");
        // Añadimos subdirectorios encadenados:
        Path archivoFuente = baseProyecto.resolve("src").resolve("App.java");

        System.out.println("Ruta generada: " + archivoFuente);
        System.out.println("Nombre final: " + archivoFuente.getFileName()); 
        System.out.println("Carpeta contenedora: " + archivoFuente.getParent());   
    }
}
