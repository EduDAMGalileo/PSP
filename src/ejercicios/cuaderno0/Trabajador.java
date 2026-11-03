package ejercicios.cuaderno0;

/** Cuenta los números primos menores que n y escribe "n;cuantos". */ 
public class Trabajador { 
    public static void main(String[] args) { 
        int n = Integer.parseInt(args[0]); 
        int cuantos = 0; 
        for (int i = 2; i < n; i++) { 
            if (esPrimo(i)) { 
                cuantos++; 
            } 
        } 
        System.out.println(n + ";" + cuantos); 
    } 
 
    static boolean esPrimo(int x) { 
    	//Usamos el algortimo de la raíz, pero con potencias
        for (int d = 2; (long) d * d <= x; d++) { 
            if (x % d == 0) { 
                return false; 
            } 
        } 
        return true; 
    } 
}