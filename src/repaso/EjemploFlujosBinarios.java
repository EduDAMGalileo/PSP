package repaso;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class EjemploFlujosBinarios {

    public static void main(String[] args) {
        ejemploBytesEnBruto();
        System.out.println("\n--------------------------------------------------\n");
        ejemploDatosPrimitivos();
    }

    // =========================================================================
    // BYTES EN BRUTO (Copiar ficheros no-texto en bloques de 8 KB)
    // =========================================================================
    public static void ejemploBytesEnBruto() {
        System.out.println("=== COPIA CON BÚFER DE BYTES EN BRUTO ===");
        
        Path origen = Path.of("origen.bin");
        Path destino = Path.of("copia_destino.bin");

        try {
            // Creamos un fichero simulado de 20.000 bytes para probar:
            byte[] datosSimulados = new byte[20_000];
            Files.write(origen, datosSimulados);

            // Patrón clásico de copia mediante InputStream y OutputStream:
            try (InputStream in = Files.newInputStream(origen);
                 OutputStream out = Files.newOutputStream(destino)) {

                byte[] bufer = new byte[8192]; // Búfer de 8 KB
                int leidos;
                int vueltas = 0;

                // in.read(bufer) devuelve cuántos bytes ha podido leer, o -1 al llegar al final
                while ((leidos = in.read(bufer)) != -1) {
                    vueltas++;
                    System.out.println("  Vuelta " + vueltas + ": leídos " + leidos + " bytes.");

                    // CLAVE: Escribir desde 0 hasta 'leidos', NUNCA el array entero:
                    out.write(bufer, 0, leidos);
                }
            }

            System.out.println("Copia completada con éxito. Tamaño: " + Files.size(destino) + " bytes.");

        } catch (IOException e) {
            System.err.println("Error en 5.1: " + e.getMessage());
        }
    }

    // =========================================================================
    // DATOS PRIMITIVOS (DataOutputStream y DataInputStream)
    // =========================================================================
    public static void ejemploDatosPrimitivos() {
        System.out.println("===ESCRITURA Y LECTURA BINARIA DE PRIMITIVOS ===");
        
        Path ruta = Path.of("alumnos.dat");

        // --- ESCRITURA BINARIA ---
        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(Files.newOutputStream(ruta)))) {

            System.out.println("Escribiendo alumnos en formato binario...");
            // Registro 1: int (4 bytes) + String UTF (2 bytes longitud + texto) + double (8 bytes)
            out.writeInt(1);
            out.writeUTF("Ana");
            out.writeDouble(8.5);

            // Registro 2:
            out.writeInt(2);
            out.writeUTF("Íñigo");
            out.writeDouble(6.25);

        } catch (IOException e) {
            System.err.println("Error al escribir: " + e.getMessage());
        }

        // --- LECTURA BINARIA ---
        // Debemos leer EXACTAMENTE en el mismo orden de tipos que escribimos
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(Files.newInputStream(ruta)))) {

            System.out.println("Leyendo alumnos desde 'alumnos.dat':");

            // Bucle infinito que se detiene cuando salta la EOFException
            while (true) {
                int id = in.readInt();
                String nombre = in.readUTF();
                double nota = in.readDouble();

                System.out.println("  ID: " + id + " | Nombre: " + nombre + " | Nota: " + nota);
            }

        } catch (EOFException fin) {
            // EOF = End Of File. No es un error grave: es la señal normal de que no hay más datos.
            System.out.println("-> Fin del fichero alcanzado con éxito (EOFException).");
        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
        }
    }
}