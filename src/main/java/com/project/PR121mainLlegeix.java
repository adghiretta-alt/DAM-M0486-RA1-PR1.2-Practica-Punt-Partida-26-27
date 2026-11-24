package com.project;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.project.excepcions.IOFitxerExcepcio;
import com.project.objectes.PR121hashmap;

public class PR121mainLlegeix {

    // Ruta del archivo serializado
    private static String filePath =
            System.getProperty("user.dir")
            + "/data/PR121HashMapData.ser";

    public static void main(String[] args) {

        try {

            // Recuperar el objeto desde el fichero
            PR121hashmap hashMap = deserialitzarHashMap();

            // Mostrar todas las personas almacenadas
            hashMap.getPersones().forEach(
                    (nom, edat) ->
                            System.out.println(
                                    nom + ": "
                                    + edat + " anys")
            );

        } catch (IOFitxerExcepcio e) {
            System.err.println(
                    "Error al llegir l'arxiu: "
                    + e.getMessage());
        }
    }

    /**
     * Lee el fichero .ser y reconstruye
     * el objeto PR121hashmap.
     */
    public static PR121hashmap deserialitzarHashMap()
            throws IOFitxerExcepcio {

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(filePath))) {

            // Leer el objeto guardado en el fichero
            return (PR121hashmap) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {

            throw new IOFitxerExcepcio(
                    "Error en deserialitzar l'objecte HashMap: "
                    + e.getMessage()
            );
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
}
