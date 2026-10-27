package ejercicios.cuaderno0;

/** Duerme los segundos indicados y termina. */
public class Duerme {
    public static void main(String[] args) throws InterruptedException {
        int segundos = Integer.parseInt(args[0]);
        System.out.println("Duermo " + segundos + " s");
        Thread.sleep(segundos * 1000L);
        System.out.println("Me despierto");
    }
}
