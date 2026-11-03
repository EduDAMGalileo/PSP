package procesos;

import java.io.*;
import java.util.Scanner;

public class ComunicacionPadre {

    // =========================================================================
    // FORMA 1: HERENCIA DE E/S (inheritIO)
    // El hijo comparte la consola del padre de manera transparente.
    // =========================================================================
    public static void probarInheritIO() {
        System.out.println("\n--- [MODO 1] Probando inheritIO() ---");
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "procesos.ComunicacionHijo");
            
            // Redirige todo al flujo estándar del padre automáticamente
            pb.inheritIO();
            
            Process p = pb.start();
            System.out.println("PADRE: El hijo está corriendo en tu consola. Escribe algo (escribe SALIR para terminar):");
            
            // Espera a que el usuario termine en la consola
            p.waitFor(); 
            System.out.println("PADRE: El proceso hijo por inheritIO ha terminado.\n");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // FORMA 2: REDIRECCIÓN A FICHEROS (redirectInput / redirectOutput)
    // El hijo lee de un archivo de texto y vuelca el resultado en otro.
    // =========================================================================
    public static void probarRedireccionFicheros() {
        System.out.println("\n--- [MODO 2] Probando Redirección a Ficheros ---");
        try {
            // Creamos un fichero de entrada simulado de prueba
            File fEntrada = new File("entrada_test.txt");
            try (PrintWriter pw = new PrintWriter(fEntrada)) {
                pw.println("Hola mundo de los procesos");
                pw.println("Programacion de Servicios y Procesos");
                pw.println("SALIR");
            }

            File fSalida = new File("salida_test.txt");

            ProcessBuilder pb = new ProcessBuilder("java", "procesos.ComunicacionHijo");
            
            // Configuramos los ficheros ANTES de arrancar 
            pb.redirectInput(fEntrada); 
            pb.redirectOutput(fSalida); 

            Process p = pb.start();
            p.waitFor(); // Espera a que lea todo el fichero y escriba el resultado

            System.out.println("PADRE: Fichero 'entrada_test.txt' procesado por el hijo.");
            System.out.println("PADRE: Revisa el fichero 'salida_test.txt' para ver el resultado.\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // FORMA 3: TUBERÍAS ACTIVAS (Pipes con getOutputStream / getInputStream)
    // El padre habla interactivamente con el hijo enviando y leyendo datos.
    // =========================================================================
    public static void probarTuberiasActivas() {
        System.out.println("\n--- [MODO 3] Probando Tuberías Activas (Pipes) ---");
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "procesos.ComunicacionHijo");
            Process p = pb.start(); // Arranca el proceso hijo

            // Abrimos los canales de comunicación DESPUÉS de arrancar
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            BufferedReader lector = new BufferedReader(new InputStreamReader(p.getInputStream()));

            // Mandamos datos de forma interactiva desde el código del padre
            String[] mensajes = {"Primera frase de prueba", "PSP mola mucho", "SALIR"};

            for (String msg : mensajes) {
                System.out.println("PADRE -> Enviando al hijo: " + msg);
                escritor.write(msg + "\n");
                escritor.flush(); // ¡Obligatorio para vaciar el búfer!

                // Leemos la respuesta inmediata del hijo
                String respuesta = lector.readLine();
                if (respuesta != null) {
                    System.out.println("  <-- [Respuesta recibida]: " + respuesta);
                }
            }

            p.waitFor();
            System.out.println("PADRE: Tuberías cerradas correctamente.\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================================
    // MÉTODO PRINCIPAL (MENÚ)
    // =========================================================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("==========================================");
            System.out.println("      MENÚ DE COMUNICACIÓN DE PROCESOS    ");
            System.out.println("==========================================");
            System.out.println("1. Probar Herencia de Consola (inheritIO)");
            System.out.println("2. Probar Redirección a Ficheros");
            System.out.println("3. Probar Tuberías Activas (Pipes Código)");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");

            while (!sc.hasNextInt()) {
                System.out.print("Introduce un número válido: ");
                sc.next();
            }
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    probarInheritIO();
                    break;
                case 2:
                    probarRedireccionFicheros();
                    break;
                case 3:
                    probarTuberiasActivas();
                    break;
                case 4:
                    System.out.println("Saliendo del programa principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);

        sc.close();
    }
}
