package repaso;

public class AppPuntos {
    public static void main(String[] args) {
        // --- CREACIÓN ---
        PuntoClase p1 = new PuntoClase(3, 4);
        PuntoRecord pr1 = new PuntoRecord(3, 4);

        // ---  LECTURA DE VALORES ---
        System.out.println(p1.x() + ", " + p1.y());     
        System.out.println(pr1.x() + ", " + pr1.y());   

        // ---  TOSTRING AUTOMÁTICO ---
        System.out.println(p1);   
        System.out.println(pr1);  

        // ---  COMPARACIÓN POR VALOR (equals) ---
        PuntoClase p2 = new PuntoClase(3, 4);
        PuntoRecord pr2 = new PuntoRecord(3, 4);

        // Compara las coordenadas, no las direcciones de memoria
        System.out.println("PuntoClase equals: " + p1.equals(p2));
        System.out.println("PuntoRecord equals: " + pr1.equals(pr2));    

        // ---  INMUTABILIDAD ---
        // p1.x = 10;  
        // pr1.x = 10;
        // Tampoco existen métodos setX() en ninguno de los dos.
    }
}