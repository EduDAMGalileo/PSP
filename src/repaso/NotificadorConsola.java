package repaso;

public class NotificadorConsola implements Notificador, Auditable {
    
    @Override
    public void enviar(String mensaje) {
        System.out.println("Consola: " + mensaje);
    }

    @Override
    public void auditar(String evento) {
        System.out.println("[AUDITORÍA INTERNA] Registrado evento: " + evento);
    }
}

