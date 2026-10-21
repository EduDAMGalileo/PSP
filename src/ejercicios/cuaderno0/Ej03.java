package ejercicios.cuaderno0;


public class Ej03 {
    public static void main(String[] args) throws Exception {
        System.out.println("Padre: mi PID es " + ProcessHandle.current().pid());
        ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.MiPadre");
        pb.inheritIO();
        pb.start().waitFor();
    }
}
