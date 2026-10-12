package repaso;

public class Cuenta {
    // --- MIEMBROS STATIC (Pertenecen a la CLASE, compartidos por todas las cuentas) ---
    private static int totalCuentas = 0;              // Contador global
    public static final double COMISION = 1.5;        // Constante común para todas las cuentas

    // --- MIEMBROS DE INSTANCIA (Cada objeto tiene su propia copia) ---
    private final String titular;
    private double saldo;

    public Cuenta(String titular, double saldoInicial) {
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("Saldo inicial negativo");
        }
        this.titular = titular;
        this.saldo = saldoInicial;

        // Cada vez que se hace un "new Cuenta", sumamos una al total global:
        totalCuentas++;
    }

    public void ingresar(double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad: " + cantidad);
        }
        saldo += cantidad;
    }

    public double getSaldo() { 
        return saldo; 
    }

    public String getTitular() { 
        return titular; 
    }

    // --- MÉTODO STATIC ---
    // Se puede llamar sin necesidad de tener un objeto creado (Cuenta.getTotalCuentas())
    public static int getTotalCuentas() {
        return totalCuentas;
    }
}
