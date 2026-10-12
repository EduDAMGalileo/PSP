package repaso;

public class EjemploPrimitivoYReferencias {

    // Método que intenta modificar ambos valores:
    public static void hacerRegalo(double dineroPrimitivo, Cuenta cuentaReferencia) {
        //Modificamos el primitivo (la fotocopia):
        dineroPrimitivo = dineroPrimitivo + 500; 

        //Modificamos a través de la referencia (la llave de la casa):
        cuentaReferencia.ingresar(500); 
    }

    public static void main(String[] args) {
        double miDinero = 100.0;
        Cuenta miCuenta = new Cuenta("Edu", 100.0);

        // Llamamos al método pasándole ambos:
        hacerRegalo(miDinero, miCuenta);

        // --- RESULTADOS TRAS VOLVER DEL MÉTODO ---        
        //El primitivo NO ha cambiado:
        System.out.println("miDinero: " + miDinero); 

        //  El objeto SÍ ha cambiado:
        System.out.println("Saldo de miCuenta: " + miCuenta.getSaldo()); 
    }
}
