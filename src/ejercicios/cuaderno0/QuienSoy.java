package ejercicios.cuaderno0;

public class QuienSoy {
    public static void main(String[] args) throws InterruptedException {
        long pid = ProcessHandle.current().pid();
        System.out.println("Soy un proceso y mi PID es " + pid);
        Thread.sleep(30_000);   // 30 segundos para que puedas buscarme
        System.out.println("Termino");
    }
}

