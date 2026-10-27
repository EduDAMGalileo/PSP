package ejercicios.cuaderno0;

import java.nio.file.Files;
import java.nio.file.Path;

public class Ej10 {
    public static void main(String[] args) throws Exception {
        Path rutaDatos = Path.of("datos");
        Path rutaArchivo = rutaDatos.resolve("datos.txt");

        //Si no existe la carpeta la creamos
        if (!Files.exists(rutaDatos)) {
            Files.createDirectory(rutaDatos);
        }
        
        //Si no existe el archivo lo creamos
        if (!Files.exists(rutaArchivo)) {
            Files.createFile(rutaArchivo);
            System.out.println("[INFO] Se ha creado 'datos/datos.txt' automáticamente.");
        }

        System.out.println("--- Sin directory()");
        Lanzador.para("ejercicios.cuaderno0.DondeEstoy").inheritIO().start().waitFor();

        System.out.println("--- Con directory(new File(\"datos\"))");
        ProcessBuilder pb = Lanzador.para("ejercicios.cuaderno0.DondeEstoy");
        

        //Como provess builder solo acepta File lo tenemos que transformar de Files a File
        pb.directory(rutaDatos.toFile());
        System.out.println(pb.directory()); 
        pb.inheritIO().start().waitFor();
    }
   
}
