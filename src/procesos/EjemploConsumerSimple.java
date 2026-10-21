package procesos;

import java.util.List;
import java.util.function.Consumer;

public class EjemploConsumerSimple {
    public static void main(String[] args) {
        List<String> alumnos = List.of("Ana", "Carlos", "Elena");

        // El método .forEach() de las listas acepta un Consumer<String>
        alumnos.forEach(new Consumer<String>() {
            @Override
            public void accept(String nombre) {
                // 'nombre' es el elemento que la lista le entrega a nuestro Consumer
                System.out.println("Saludando al alumno: " + nombre);
            }
        });
    }
}