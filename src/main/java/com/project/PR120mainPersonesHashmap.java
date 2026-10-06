package com.project;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.project.excepcions.IOFitxerExcepcio;

public class PR120mainPersonesHashmap {
    private static String filePath = System.getProperty("user.dir") + "/data/PR120persones.dat";

    public static void main(String[] args) {
        HashMap<String, Integer> persones = new HashMap<>();
        persones.put("Anna", 25);
        persones.put("Bernat", 30);
        persones.put("Carla", 22);
        persones.put("David", 35);
        persones.put("Elena", 28);

        try {
            escriurePersones(persones);
            llegirPersones();
        } catch (IOFitxerExcepcio e) {
            System.err.println("Error en treballar amb el fitxer: " + e.getMessage());
        }
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String newFilePath) {
        filePath = newFilePath;
    }

    public static void escriurePersones(HashMap<String, Integer> persones) throws IOFitxerExcepcio {
        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(filePath))) {

            dos.writeInt(persones.size());

            for (Map.Entry<String, Integer> persona : persones.entrySet()) {
                dos.writeUTF(persona.getKey());
                dos.writeInt(persona.getValue());
            }

        } catch (IOException e) {
            throw new IOFitxerExcepcio(
                    "Error en escriure les persones al fitxer", e);
        }
    }

    public static void llegirPersones() throws IOFitxerExcepcio {
        try (DataInputStream dis = new DataInputStream(
                new FileInputStream(filePath))) {

            int nombrePersones = dis.readInt();

            for (int i = 0; i < nombrePersones; i++) {
                String nom = dis.readUTF();
                int edat = dis.readInt();

                System.out.println(nom + ": " + edat + " anys");
            }

        } catch (IOException e) {
            throw new IOFitxerExcepcio(
                    "Error en llegir les persones del fitxer", e);
        }
    }
}