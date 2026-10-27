package ejercicios.cuaderno0;

import java.nio.file.Files;
import java.nio.file.Path;

public class DondeEstoy {
    public static void main(String[] args) {
        System.out.println("Mi directorio de trabajo: "
                + System.getProperty("user.dir"));
        System.out.println("¿Veo datos.txt? "
                + Files.exists(Path.of("datos.txt")));
    }
}
