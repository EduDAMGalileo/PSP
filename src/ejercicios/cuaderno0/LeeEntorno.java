package ejercicios.cuaderno0;

public class LeeEntorno {
    public static void main(String[] args) {
        System.out.println("SALUDO = " + System.getenv("SALUDO"));
        System.out.println("MODO   = " + System.getenv("MODO"));
       // System.getenv().put("X", "1");
    }
}
