package repaso;

public class AppCuentas {
    public static void main(String[] args) {
        // Podemos consultar miembros static ANTES de crear ninguna cuenta:
        System.out.println("Cuentas al inicio: " + Cuenta.getTotalCuentas()); 
        System.out.println("Comisión fija: " + Cuenta.COMISION + "€");

        //Creamos dos cuentas individuales:
        Cuenta c1 = new Cuenta("Ana", 100);
        Cuenta c2 = new Cuenta("Edu", 200);

        //Cada cuenta tiene sus propios datos (de instancia):
        System.out.println("Saldo Ana: " + c1.getSaldo());
        System.out.println("Saldo Edu: " + c2.getSaldo()); 

        //El contador static ha subido para ambos:
        System.out.println("Cuentas creadas: " + Cuenta.getTotalCuentas()); 
    }
}