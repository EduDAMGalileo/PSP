package ejercicios.cuaderno0;

import java.nio.charset.StandardCharsets;

public class Ej13 {
    public static void main(String[] args) throws Exception {
        Process hijo = Lanzador.para("ejercicios.cuaderno0.Saludo").start();
        byte[] bytes = hijo.getInputStream().readAllBytes();  // hasta el final
        String texto = new String(bytes, StandardCharsets.UTF_8).strip();
        int codigo = hijo.waitFor();
        System.out.println("Padre: el hijo dijo «" + texto + "»");
        System.out.println("Padre: y terminó con código " + codigo);
    }
}
