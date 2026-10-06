package repaso;

public class Suma {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java es.dam.psp.Suma <a> <b>");
            System.exit(2);          // 2: uso incorrecto
        }
        try {
            int a = Integer.parseInt(args[0]);
            int b = Integer.parseInt(args[1]);
            System.out.println(a + b);
        } catch (NumberFormatException e) {
            System.err.println("Los argumentos deben ser enteros");
            System.exit(1);          // 1: datos no válidos
        }
    }
}                                     // si main termina normalmente: 0
