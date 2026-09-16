# Programación de Servicios y Procesos · 2º DAM

Repositorio con los **ejercicios y ejemplos** del módulo profesional de **Programación de Servicios y Procesos (PSP)**, del ciclo formativo de grado superior de Desarrollo de Aplicaciones Multiplataforma (DAM).

El objetivo es tener en un mismo sitio el código que vemos en clase, para poder consultarlo, probarlo y usarlo como punto de partida en las prácticas.

## Contenidos

Los ejemplos siguen, a grandes rasgos, los bloques del módulo:

- **Programación multiproceso**: creación y gestión de procesos con `ProcessBuilder` y `Process`, redirección de entrada/salida y comunicación entre procesos.
- **Programación multihilo**: creación de hilos, ciclo de vida, sincronización (`synchronized`, `wait`/`notify`), problemas clásicos como productor-consumidor y utilidades de `java.util.concurrent` (ejecutores, locks, colecciones concurrentes). También veremos **hilos virtuales**.
- **Comunicaciones en red**: sockets TCP y UDP, arquitectura cliente-servidor y servidores que atienden a varios clientes a la vez.
- **Servicios en red**: uso de protocolos estándar como HTTP desde Java con `HttpClient`.
- **Programación segura**: hashes, cifrado simétrico y asimétrico, firmas digitales y comunicaciones seguras con SSL/TLS.

## Requisitos

- **JDK 25**
- Eclipse IDE for Java Developers, en una versión con soporte para Java 25
- Los drivers o librerías necesarios, indicados en cada ejercicio (la mayoría de ejemplos solo usan la biblioteca estándar)

## Cómo usar el repositorio

1. Clona el repositorio:

   ```bash
   git clone <url-del-repositorio>
   ```

2. En Eclipse, ve a **File → Import → General → Projects from Folder or Archive** y selecciona la carpeta clonada.

3. Comprueba que el proyecto usa Java 25: clic derecho en el proyecto → **Properties → Java Compiler** y en **Java Build Path → Libraries** que el JRE sea el JDK 25.

> La configuración de Eclipse (`.project`, `.classpath`, `.settings/`) no se sube al repositorio, así que puede que tengas que ajustar la versión de Java la primera vez que importes un proyecto.

### Ejemplos con características en *preview*

Algún ejemplo puede usar características de Java que todavía están en *preview* (por ejemplo, la concurrencia estructurada). Si al compilar aparece un error indicando que la característica no está habilitada:

- **En Eclipse**: clic derecho en el proyecto → **Properties → Java Compiler** → marca **Enable preview features for Java 25**.
- **Desde terminal**:

  ```bash
  java --enable-preview Ejemplo.java
  ```

Los ejemplos que lo necesiten lo indicarán en un comentario al principio del fichero.

## Ejecutar los ejemplos de red

Los ejercicios cliente-servidor necesitan que **primero se arranque el servidor** y después uno o varios clientes. Por defecto usan `localhost` y un puerto indicado en el código; si el puerto está ocupado, cámbialo tanto en el servidor como en el cliente.

En Eclipse puedes lanzar varios programas a la vez y cambiar entre sus salidas desde el botón **Display Selected Console** de la vista *Console*.

## Aviso

Los ejemplos tienen fines **didácticos**: priorizan que el código se entienda por encima de que esté optimizado o listo para producción. Las claves, contraseñas y certificados que aparecen son de prueba y no deben usarse fuera de clase.
