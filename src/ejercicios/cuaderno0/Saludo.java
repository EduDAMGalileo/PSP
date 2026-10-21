package ejercicios.cuaderno0;

public class Saludo {
    public static void main(String[] args) {
        System.out.println("Hola, soy el hijo. Mi PID es "
                + ProcessHandle.current().pid());
    }
}
