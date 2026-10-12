package repaso;

public class EjemploNotificador {
    public static void main(String[] args) {
        NotificadorConsola miNotificador = new NotificadorConsola();

        // Llamada al método normal implementado en la clase
        miNotificador.enviar("El proceso se ha iniciado con éxito.");

        // Llamada al método 'default'
        // Fíjate en que NotificadorConsola NO programó 'enviarUrgente',
        // pero lo tiene disponible gratis porque viene de la interfaz.
        miNotificador.enviarUrgente("Queda poco espacio en el disco.");

        miNotificador.auditar("Usuario admin conectado.");
    }
}
