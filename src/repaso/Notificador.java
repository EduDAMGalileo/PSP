package repaso;

public interface Notificador {
    // Método abstracto obligatorio: cada clase decidirá cómo implementarlo
    void enviar(String mensaje);

    // Método default: ya viene programado, ninguna clase está obligada a reescribirlo
    default void enviarUrgente(String mensaje) {
    	// Reutiliza el método enviar() de la clase
        enviar("[URGENTE] " + mensaje); 
    }
}