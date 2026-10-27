package ejercicios.cuaderno0;

public class Ej08 {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Versión A: un argumento por elemento");
        ProcessBuilder a = Lanzador.para("ejercicios.cuaderno0.MuestraArgs",
                "Hola mundo", "adiós");
        //Podemos heredar la consola, arrancar el proceso y esperar a que acabe de un plumazo
        a.inheritIO().start().waitFor();

        System.out.println("--- Versión B: cada palabra por separado");
        ProcessBuilder b = Lanzador.para("ejercicios.cuaderno0.MuestraArgs",
                "Hola", "mundo", "adiós");
        b.inheritIO().start().waitFor();
    }
}
