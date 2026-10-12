package repaso;

public class EjemploComparacion {
    public static void main(String[] args) {
        PuntoRecord p1 = new PuntoRecord(5, 5);
        PuntoRecord p2 = new PuntoRecord(5, 5);

        // ¿Son el mismo objeto en memoria?
        System.out.println(p1 == p2);      

        // ¿Tienen las mismas coordenadas?
        System.out.println(p1.equals(p2)); 

        // ¿Cuándo daría 'true' el ==? Solo si apuntan a la misma casa:
        PuntoRecord p3 = p1; 
        System.out.println(p1 == p3);
    }
}