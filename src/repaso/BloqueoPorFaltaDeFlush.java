package repaso;

import java.io.*;
import java.net.*;

public class BloqueoPorFaltaDeFlush {
    public static void main(String[] args) throws Exception {
        int puerto = 5000;

        // 1. INICIAMOS EL SERVIDOR EN UN HILO
        Thread servidor = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(puerto);
                 Socket socket = serverSocket.accept();
                 BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter salida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                System.out.println("[SERVIDOR] Cliente conectado. Esperando su saludo...");
                
                // El servidor se queda bloqueado aquí esperando a leer una línea:
                String mensaje = entrada.readLine(); 
                
                System.out.println("[SERVIDOR] Mensaje recibido: " + mensaje);
                salida.write("HOLA DESDE EL SERVIDOR\n");
                salida.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        servidor.start();

        // 2. EL CLIENTE INTENTA COMUNICARSE
        Thread.sleep(200); // Pequeña pausa para asegurar que el servidor arrancó

        try (Socket socket = new Socket("localhost", puerto);
             BufferedWriter salidaCliente = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("[CLIENTE] Escribiendo 'HOLA' en el flujo...");
            salidaCliente.write("HOLA");
            salidaCliente.newLine();
            
            // ⚠️ EL ERROR: No hacemos salidaCliente.flush() ni cerramos el socket.
            // El texto "HOLA\n" sigue atrapado en la memoria RAM del cliente.

            System.out.println("[CLIENTE] Esperando respuesta del servidor...");
            // El cliente se queda bloqueado aquí esperando al servidor:
            String respuesta = entradaCliente.readLine(); 
            
            System.out.println("[CLIENTE] Respuesta recibida: " + respuesta);
        }
    }
}
