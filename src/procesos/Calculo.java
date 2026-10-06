package procesos;

public class Calculo {
    public static void main(String[] args) {
        long suma = 0;
        for (long i = 0; i < 3_000_000_000L; i++) {
            suma += i % 7;
        }
        System.out.println(suma);
    }
}
