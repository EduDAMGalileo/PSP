package ejercicios.cuaderno0;

import java.util.Optional;

public class MiPadre {
    public static void main(String[] args) {
        ProcessHandle yo = ProcessHandle.current();
        System.out.printf("Mi PID: %6d %n",  yo.pid());

        Optional<ProcessHandle> padre = yo.parent();
        if (padre.isPresent()) {
            ProcessHandle p = padre.get();
            System.out.println("PID de padre: " + p.pid());
            System.out.println("Orden padre:  "
                    + p.info().command().orElse("(desconocida)"));
        } else {
            System.out.println("No puedo ver a mi padre");
        }
    }
}
