package repaso;


import java.io.*;
import java.nio.charset.StandardCharsets;

public class Mayusculas {
    public static void main(String[] args) {
        try (BufferedReader entrada = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = entrada.readLine()) != null) {
                System.out.println(linea.toUpperCase());
            }
        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
            System.exit(1);
        }
    }
}
