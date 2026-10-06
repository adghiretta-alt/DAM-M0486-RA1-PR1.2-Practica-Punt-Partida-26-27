package com.project;

import com.project.excepcions.IOFitxerExcepcio;
import com.project.utilitats.UtilsCSV;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PR123mainTreballadors {

    private String filePath = System.getProperty("user.dir") + "/data/PR123treballadors.csv";
    private Scanner scanner = new Scanner(System.in);

    // Getters i setters per a filePath
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void iniciar() {
        boolean sortir = false;

        while (!sortir) {
            try {
                // Mostrar menú
                mostrarMenu();

                // Llegir opció de l'usuari
                int opcio = Integer.parseInt(scanner.nextLine());

                switch (opcio) {
                    case 1 -> mostrarTreballadors();
                    case 2 -> modificarTreballadorInteractiu();
                    case 3 -> {
                        System.out.println("Sortint...");
                        sortir = true;
                    }
                    default -> System.out.println("Opció no vàlida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Si us plau, introdueix un número vàlid.");

            } catch (IllegalArgumentException e) {
                // Id inexistent o columna no vàlida
                System.out.println("Error: " + e.getMessage());

            } catch (IOFitxerExcepcio e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\nMenú de Gestió de Treballadors");
        System.out.println("1. Mostra tots els treballadors");
        System.out.println("2. Modificar dades d'un treballador");
        System.out.println("3. Sortir");
        System.out.print("Selecciona una opció: ");
    }

    public void mostrarTreballadors() throws IOFitxerExcepcio {

        List<String> treballadorsCSV = llegirFitxerCSV();

        for (String linia : treballadorsCSV) {
            System.out.println(linia);
        }
    }

    // Mètode per modificar un treballador (interactiu)
    public void modificarTreballadorInteractiu() throws IOFitxerExcepcio {

        // Demanar l'ID del treballador
        System.out.print("\nIntrodueix l'ID del treballador que vols modificar: ");
        String id = scanner.nextLine();

        // Demanar quina dada vols modificar
        System.out.print("Quina dada vols modificar (Nom, Cognom, Departament, Salari)? ");
        String columna = scanner.nextLine();

        // Demanar el nou valor
        System.out.print("Introdueix el nou valor per a " + columna + ": ");
        String nouValor = scanner.nextLine();

        // Modificar treballador
        modificarTreballador(id, columna, nouValor);
    }

    // Mètode que modifica treballador (per a tests i usuaris)
    // llegint i escrivint sobre disc.
    //
    // - Si l'Id no existeix o la columna no és vàlida
    //   llança IllegalArgumentException.
    // - Si hi ha problemes amb el fitxer
    //   llança IOFitxerExcepcio.
    public void modificarTreballador(
            String id,
            String columna,
            String nouValor
    ) throws IOFitxerExcepcio {

        List<String> treballadorsCSV = llegirFitxerCSV();

        int indexColumna;

        // Determinem quina columna volem modificar
        switch (columna.trim().toLowerCase()) {

            case "nom":
                indexColumna = 1;
                break;

            case "cognom":
                indexColumna = 2;
                break;

            case "departament":
                indexColumna = 3;
                break;

            case "salari":
                indexColumna = 4;
                break;

            default:
                throw new IllegalArgumentException(
                        "La columna '" + columna + "' no és vàlida."
                );
        }

        boolean trobat = false;

        for (int i = 0; i < treballadorsCSV.size(); i++) {

            String linia = treballadorsCSV.get(i);

        
            String[] camps = linia.split(",", -1);

            // Si la línia no té almenys 5 camps, la ignorem
            if (camps.length < 5) {
                continue;
            }

            // Comprovem si l'Id coincideix
            if (camps[0].trim().equals(id.trim())) {

 

                int inici = 0;

                // Busquem l'inici de la columna que volem modificar
                for (int j = 0; j < indexColumna; j++) {
                    inici = linia.indexOf(",", inici) + 1;
                }

                // Busquem el final del camp
                int finalCamp = linia.indexOf(",", inici);

                // Si és l'últim camp, el final és el final de la línia
                if (finalCamp == -1) {
                    finalCamp = linia.length();
                }

                // Substituïm únicament el valor del camp
                String liniaModificada =
                        linia.substring(0, inici)
                        + nouValor
                        + linia.substring(finalCamp);

                // Guardem la línia modificada
                treballadorsCSV.set(i, liniaModificada);

                trobat = true;

                break;
            }
        }

        if (!trobat) {
            throw new IllegalArgumentException(
                    "No existeix cap treballador amb l'Id: " + id
            );
        }

        escriureFitxerCSV(treballadorsCSV);
    }

    private List<String> llegirFitxerCSV() throws IOFitxerExcepcio {

        List<String> treballadorsCSV = UtilsCSV.llegir(filePath);

        if (treballadorsCSV == null) {
            throw new IOFitxerExcepcio(
                    "Error en llegir el fitxer: " + filePath
            );
        }

        return treballadorsCSV;
    }

    private void escriureFitxerCSV(
            List<String> treballadorsCSV
    ) throws IOFitxerExcepcio {

        try {
            UtilsCSV.escriure(filePath, treballadorsCSV);

        } catch (IOException e) {
            throw new IOFitxerExcepcio(
                    "Error en escriure el fitxer: " + filePath,
                    e
            );
        }
    }

    public static void main(String[] args) {

        PR123mainTreballadors programa =
                new PR123mainTreballadors();

        programa.iniciar();
    }
}
