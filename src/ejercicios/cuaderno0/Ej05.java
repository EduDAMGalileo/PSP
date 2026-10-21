package ejercicios.cuaderno0;

public class Ej05 {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.Saludo");
        pb.inheritIO();
        Process hijo = pb.start();
        System.out.println("Padre: espero a que termine el hijo...");
        int codigo = hijo.waitFor();       // se detiene aquí
        System.out.println("Padre: el hijo ha terminado con código " + codigo);
    }
}

