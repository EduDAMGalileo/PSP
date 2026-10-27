package ejercicios.cuaderno0;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ej12 {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Cuenta", "5");
        pb.redirectOutput(new File("salida.txt"));
        int codigo = pb.start().waitFor();
        System.out.println("Padre: el hijo terminó con código " + codigo);

        System.out.println("Padre: contenido de salida.txt:");
        for (String linea : Files.readAllLines(Path.of("salida.txt"))) {
            System.out.println("   " + linea);
        }
    }
}
