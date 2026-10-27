package ejercicios.cuaderno0;

/** Tarda mucho en terminar, pero avisa si le piden que se cierre. */
public class Terco {
    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Me piden que termine: recojo y salgo");
            }
        });
        System.out.println("Trabajando...");
        Thread.sleep(60_000);
    }
}
