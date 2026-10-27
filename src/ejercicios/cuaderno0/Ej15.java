package ejercicios.cuaderno0;

public class Ej15 {
    public static void main(String[] args) throws Exception {
        System.out.println("--- destroy()");
        Process a = Lanzador.para("ejercicios.cuaderno0.Terco").inheritIO().start();
        // le damos tiempo al hijo a arrancar
        Thread.sleep(1500);                
        a.destroy();
        System.out.println("Código: " + a.waitFor());

        System.out.println("--- destroyForcibly()");
        Process b = Lanzador.para("ejercicios.cuaderno0.Terco").inheritIO().start();
        // le damos tiempo al hijo a arrancar
        Thread.sleep(1500);
        b.destroyForcibly();
        System.out.println("Código: " + b.waitFor());
    }
}
