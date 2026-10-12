package repaso;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EjemploExcepciones {

    // Método simulado que declara que puede lanzar IOException o TimeoutException
    public static void procesar(int n) throws IOException, TimeoutException {
        if (n < 0) {
            throw new TimeoutException("Tiempo de espera agotado al conectar");
        } else if (n == 0) {
            throw new IOException("Fallo en la lectura de datos");
        } else {
            System.out.println("-> Número " + n + " procesado con éxito.");
        }
    }

    public static void ejecutarPrueba(String texto) {
        System.out.println("\n==============================================");
        System.out.println("Probando con: \"" + texto + "\"");

        try {
            int n = Integer.parseInt(texto);
            procesar(n);
        } catch (NumberFormatException e) {
            System.err.println("No es un número: " + texto);
        } catch (IOException | TimeoutException e) {  
            System.err.println("Fallo al procesar: " + e.getMessage());
        } finally {
            System.out.println("Esto se ejecuta siempre");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // CASO 1: El texto no es numérico -> Salta NumberFormatException
        ejecutarPrueba("hola");
        // Pequeña pausa para que System.err no se mezcle en la consola
        Thread.sleep(100); 

        // CASO 2: El número es válido pero procesar() falla -> Salta el catch múltiple
        ejecutarPrueba("-5");
        Thread.sleep(100);

        // CASO 3: Todo es correcto -> No salta ningún catch
        ejecutarPrueba("42");
    }
}