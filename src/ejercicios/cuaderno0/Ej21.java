package ejercicios.cuaderno0;

import java.io.BufferedWriter;
import java.util.concurrent.TimeUnit;

public class Ej21 {
    public static void main(String[] args) throws Exception {
        Process hijo = Lanzador.para("ejercicios.cuaderno0.Mayusculas")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .start();
        BufferedWriter w = hijo.outputWriter();
        w.write("hola");
        w.newLine();
        // enviado, pero sin cerrar
        w.flush();                         

        boolean termino = hijo.waitFor(3, TimeUnit.SECONDS);
        System.out.println("Padre: ¿terminó mi hijo? " + termino);
        
        // ahora sí: fin de la entrada
        w.close();                         
        System.out.println("Padre: tras cerrar, código " + hijo.waitFor());
    }
}
