package ejercicios.cuaderno0;

/** Escribe tantas líneas numeradas como se le indique. */
public class Cuenta {
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        for (int i = 1; i <= n; i++) {
            System.out.println("Línea " + i);
        }
    }
}
