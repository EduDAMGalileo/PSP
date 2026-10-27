package ejercicios.cuaderno0;

import java.util.Map;

public class Ej11 {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.LeeEntorno");
        // copia del padre
        Map<String, String> entorno = pb.environment();  
        entorno.put("SALUDO", "Buenos días");
        entorno.put("MODO", "pruebas");
        pb.inheritIO().start().waitFor();

        System.out.println("Padre: mi SALUDO = " + System.getenv("SALUDO"));
    }
}
