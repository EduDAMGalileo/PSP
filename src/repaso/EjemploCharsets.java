package repaso;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EjemploCharsets {
    public static void main(String[] args) {
        String original = "España: 50€";

        // Convertimos el texto a BYTES usando el estándar universal (UTF-8)
        byte[] bytesEnviados = original.getBytes(StandardCharsets.UTF_8);

        // CASO CORRECTO: El receptor usa el MISMO diccionario (UTF-8)
        String leidoCorrecto = new String(bytesEnviados, StandardCharsets.UTF_8);
        System.out.println("Lectura correcta:   " + leidoCorrecto);

        // CASO TÍPICO DE ERROR: El receptor usa Windows-1252
        // Intenta interpretar los bytes de UTF-8 con la tabla antigua de Windows
        String leidoIncorrecto = new String(bytesEnviados, Charset.forName("windows-1252"));
        System.out.println("Lectura con error:  " + leidoIncorrecto);

    }
}
