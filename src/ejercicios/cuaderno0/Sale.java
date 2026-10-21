package ejercicios.cuaderno0;

/** Termina con el código de salida que recibe como argumento. */
public class Sale {
    public static void main(String[] args) {
        int codigo = Integer.parseInt(args[0]);
        System.out.println("Voy a terminar con código " + codigo);
        System.exit(codigo);
    }
}
