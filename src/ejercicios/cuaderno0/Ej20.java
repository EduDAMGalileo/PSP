package ejercicios.cuaderno0;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Ej20 {
    public static void main(String[] args) throws Exception {
        Process hijo = Lanzador.para("ejercicios.cuaderno0.Mayusculas").start();

        try (BufferedWriter w = hijo.outputWriter()) {
            w.write("hola");
            w.newLine();
            w.write("procesos en java");
            w.newLine();
        } 
        // al cerrar, el hijo ve el final, se cierra por el try-with-resources

        try (BufferedReader r = hijo.inputReader()) {
            r.lines().forEach(l -> System.out.println("Hijo: " + l));
        }
        System.out.println("Código: " + hijo.waitFor());
    }
}
