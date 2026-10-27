package ejercicios.cuaderno0;

import java.util.concurrent.TimeUnit;

public class Ej14 {
    public static void main(String[] args) throws Exception {
        Process hijo = Lanzador.para("ejercicios.cuaderno0.Duerme", "10")
                .inheritIO().start();
        boolean termino = hijo.waitFor(2, TimeUnit.SECONDS);
        if (termino) {
            System.out.println("Padre: terminó a tiempo");
        } else {
            System.out.println("Padre: tarda demasiado, lo detengo");
            hijo.destroy();
            hijo.waitFor();
            System.out.println("Padre: hijo vivo = " + hijo.isAlive());
        }
    }
}
