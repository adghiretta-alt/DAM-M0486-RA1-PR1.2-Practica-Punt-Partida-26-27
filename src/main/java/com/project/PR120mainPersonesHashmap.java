package com.project;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.project.excepcions.IOFitxerExcepcio;

public class PR120mainPersonesHashmap {

    // Ruta del archivo donde se guardarán los datos
    private static String filePath =
            System.getProperty("user.dir") + "/data/PR120persones.dat";

    public static void main(String[] args) {

        // Crear un HashMap con nombre y edad de varias personas
        HashMap<String, Integer> persones = new HashMap<>();

        persones.put("Anna", 25);
        persones.put("Bernat", 30);
        persones.put("Carla", 22);
        persones.put("David", 35);
        persones.put("Elena", 28);

        try {
            // Escribir los datos en el fichero
            escriurePersones(persones);

            // Leer los datos del fichero y mostrarlos por pantalla
            llegirPersones();

        } catch (IOFitxerExcepcio e) {
            System.err.println(
                    "Error en treballar amb el fitxer: "
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
     * Guarda las personas del HashMap en un fichero binario.
     *
     * Primero guarda el número de personas y después,
     * para cada persona, guarda el nombre y la edad.
     */
    public static void escriurePersones(
            HashMap<String, Integer> persones)
            throws IOFitxerExcepcio {

        try (DataOutputStream dos =
                     new DataOutputStream(
                             new FileOutputStream(filePath))) {

            // Guardar cuántas personas hay en el HashMap
            dos.writeInt(persones.size());

            // Recorrer todas las entradas del HashMap
            for (Map.Entry<String, Integer> persona
                    : persones.entrySet()) {

                // Guardar nombre
                dos.writeUTF(persona.getKey());

                // Guardar edad
                dos.writeInt(persona.getValue());
            }

        } catch (IOException e) {
            throw new IOFitxerExcepcio(
                    "Error en escriure les persones al fitxer",
                    e);
        }
    }

    /**
     * Lee las personas almacenadas en el fichero binario
     * y las muestra por pantalla..
     */
    public static void llegirPersones()
            throws IOFitxerExcepcio {

        try (DataInputStream dis =
                     new DataInputStream(
                             new FileInputStream(filePath))) {

            // Leer el número de personas guardadas
            int nombrePersones = dis.readInt();

            // Leer todas las personas almacenadas
            for (int i = 0; i < nombrePersones; i++) {

                // Leer nombre
                String nom = dis.readUTF();

                // Leer edad
                int edat = dis.readInt();

                // Mostrar los datos por pantalla
                System.out.println(
                        nom + ": " + edat + " anys");
            }

        } catch (IOException e) {
            throw new IOFitxerExcepcio(
                    "Error en llegir les persones del fitxer",
                    e);
        }
    }
}
