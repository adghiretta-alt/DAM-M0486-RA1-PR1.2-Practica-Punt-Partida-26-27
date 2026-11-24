package com.project;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.project.excepcions.IOFitxerExcepcio;
import com.project.objectes.PR121hashmap;

public class PR121mainEscriu {

    // Ruta donde se guardará el archivo serializado
    private static String filePath =
            System.getProperty("user.dir")
            + "/data/PR121HashMapData.ser";

    public static void main(String[] args) {

        // Crear un objeto PR121hashmap
        PR121hashmap hashMap = new PR121hashmap();

        // Añadir personas al HashMap interno
        hashMap.getPersones().put("Anna", 25);
        hashMap.getPersones().put("Bernat", 30);
        hashMap.getPersones().put("Carla", 22);

        try {
            // Guardar el objeto completo en un fichero .ser
            serialitzarHashMap(hashMap);

        } catch (IOFitxerExcepcio e) {
            System.err.println(
                    "Error al desar l'arxiu: "
                    + e.getMessage());
        }
    }

    /**
     * Devuelve la ruta actual del fichero.
     */
    public static String getFilePath() {
        return filePath;
    }

    /**
     * Permite cambiar la ruta del fichero.
     */
    public static void setFilePath(String newFilePath) {
        filePath = newFilePath;
    }

    /**
     * Serializa el objeto PR121hashmap y lo guarda
     * en un archivo .ser.
     */
    public static void serialitzarHashMap(
            PR121hashmap hashMap)
            throws IOFitxerExcepcio {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(filePath))) {

            // Escribir el objeto completo en el fichero
            oos.writeObject(hashMap);

        } catch (IOException e) {
            throw new IOFitxerExcepcio(
                    "Error en serialitzar l'objecte HashMap: "
                    + e.getMessage()
            );
        }
    }
}
