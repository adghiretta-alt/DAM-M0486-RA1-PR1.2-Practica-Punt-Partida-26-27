package com.project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.project.excepcions.IOFitxerExcepcio;
import com.project.objectes.PR122persona;

public class PR122main {

    // Ruta del fichero donde se guardará la lista serializada
    private static String filePath =
            System.getProperty("user.dir")
            + "/data/PR122persones.dat";

    public static void main(String[] args) {

        // Crear una lista de personas
        List<PR122persona> persones = new ArrayList<>();

        persones.add(new PR122persona("Maria", "López", 36));
        persones.add(new PR122persona("Gustavo", "Ponts", 63));
        persones.add(new PR122persona("Irene", "Sales", 54));

        try {

            // Guardar la lista en el fichero
            serialitzarPersones(persones);

            // Recuperar la lista desde el fichero
            List<PR122persona> deserialitzades =
                    deserialitzarPersones();

            // Mostrar todas las personas recuperadas
            deserialitzades.forEach(System.out::println);

        } catch (IOFitxerExcepcio e) {
            System.err.println(
                    "Error: " + e.getMessage());
        }
    }

    /**
     * Serializa una lista de personas y la guarda
     * en un fichero.
     */
    public static void serialitzarPersones(
            List<PR122persona> persones)
            throws IOFitxerExcepcio {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(filePath))) {

            // Guardar la lista completa en el fichero
            oos.writeObject(persones);

        } catch (FileNotFoundException e) {

            throw new IOFitxerExcepcio(
                    "Fitxer no trobat: " + filePath,
                    e);

        } catch (IOException e) {

            throw new IOFitxerExcepcio(
                    "Error en serialitzar les persones: "
                            + e.getMessage(),
                    e);
        }
    }

    /**
     * Lee el fichero y recupera la lista de personas.
     */
    @SuppressWarnings("unchecked")
    public static List<PR122persona> deserialitzarPersones()
            throws IOFitxerExcepcio {

        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream(filePath))) {

            // Leer el objeto guardado y convertirlo a List<PR122persona>
            return (List<PR122persona>) ois.readObject();

        } catch (FileNotFoundException e) {

            throw new IOFitxerExcepcio(
                    "Fitxer no trobat: " + filePath,
                    e);

        } catch (IOException e) {

            throw new IOFitxerExcepcio(
                    "Error en deserialitzar les persones: "
                            + e.getMessage(),
                    e);

        } catch (ClassNotFoundException e) {

            throw new IOFitxerExcepcio(
                    "Classe no trobada durant la deserialització: "
                            + e.getMessage(),
                    e);
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
