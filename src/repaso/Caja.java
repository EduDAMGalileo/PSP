package repaso;

public class Caja<T> {
    private T contenido;

    public void guardar(T valor) {
        this.contenido = valor;
    }

    public T sacar() {
        return this.contenido;
    }
}
