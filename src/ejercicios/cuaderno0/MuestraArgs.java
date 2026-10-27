package ejercicios.cuaderno0;

/** Muestra cada argumento recibido entre corchetes. */
public class MuestraArgs {
    public static void main(String[] args) {
        System.out.println("He recibido " + args.length + " argumentos:");
        for (String a : args) {
            System.out.println("  [" + a + "]");
        }
    }
}
