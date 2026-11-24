package com.project;

import com.project.excepcions.IOFitxerExcepcio;
import com.project.utilitats.UtilsCSV;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PR123mainTreballadors {

    // Ruta del fichero CSV
    private String filePath =
            System.getProperty("user.dir")
            + "/data/PR123treballadors.csv";

    // Scanner para leer datos introducidos por el usuario
    private Scanner scanner = new Scanner(System.in);

    /**
     * Devuelve la ruta actual del fichero.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Permite modificar la ruta del fichero.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Bucle principal del programa.
     * Muestra el menú y ejecuta la opción elegida.
     */
    public void iniciar() {

        boolean sortir = false;

        while (!sortir) {

            try {

                // Mostrar menú principal
                mostrarMenu();

                // Leer la opción seleccionada
                int opcio =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

                switch (opcio) {

                    // Mostrar todos los trabajadores
                    case 1 -> mostrarTreballadors();

                    // Modificar un trabajador
                    case 2 -> modificarTreballadorInteractiu();

                    // Salir del programa
                    case 3 -> {
                        System.out.println("Sortint...");
                        sortir = true;
                    }

                    default ->
                            System.out.println(
                                    "Opció no vàlida."
                            );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Si us plau, introdueix un número vàlid."
                );

            } catch (IllegalArgumentException e) {

                // Error de ID o columna inexistente
                System.out.println(
                        "Error: " + e.getMessage()
                );

            } catch (IOFitxerExcepcio e) {

                System.err.println(
                        "Error: " + e.getMessage()
                );
            }
        }
    }

    /**
     * Muestra las opciones disponibles.
     */
    private void mostrarMenu() {

        System.out.println(
                "\nMenú de Gestió de Treballadors"
        );

        System.out.println(
                "1. Mostra tots els treballadors"
        );

        System.out.println(
                "2. Modificar dades d'un treballador"
        );

        System.out.println(
                "3. Sortir"
        );

        System.out.print(
                "Selecciona una opció: "
        );
    }

    /**
     * Muestra por pantalla todas las líneas del CSV.
     */
    public void mostrarTreballadors()
            throws IOFitxerExcepcio {

        // Leer trabajadores desde el fichero
        List<String> treballadorsCSV =
                llegirFitxerCSV();

        // Mostrar cada línea
        for (String linia : treballadorsCSV) {
            System.out.println(linia);
        }
    }

    /**
     * Solicita por consola los datos necesarios
     * para modificar un trabajador.
     */
    public void modificarTreballadorInteractiu()
            throws IOFitxerExcepcio {

        // Pedir ID
        System.out.print(
                "\nIntrodueix l'ID del treballador que vols modificar: "
        );

        String id = scanner.nextLine();

        // Pedir columna
        System.out.print(
                "Quina dada vols modificar (Nom, Cognom, Departament, Salari)? "
        );

        String columna =
                scanner.nextLine();

        // Pedir valor nuevo
        System.out.print(
                "Introdueix el nou valor per a "
                        + columna + ": "
        );

        String nouValor =
                scanner.nextLine();

        // Realizar modificación
        modificarTreballador(
                id,
                columna,
                nouValor
        );
    }

    /**
     * Modifica un trabajador existente.
     *
     * @param id ID del trabajador
     * @param columna Campo a modificar
     * @param nouValor Nuevo valor
     */
    public void modificarTreballador(
            String id,
            String columna,
            String nouValor
    ) throws IOFitxerExcepcio {

        // Cargar fichero en memoria
        List<String> treballadorsCSV =
                llegirFitxerCSV();

        int indexColumna;

        // Identificar qué columna se modificará
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
                        "La columna '"
                        + columna
                        + "' no és vàlida."
                );
        }

        boolean trobat = false;

        // Recorrer todas las líneas
        for (int i = 0;
             i < treballadorsCSV.size();
             i++) {

            String linia =
                    treballadorsCSV.get(i);

            // Separar la línea por comas
            String[] camps =
                    linia.split(",", -1);

            // Ignorar líneas incorrectas
            if (camps.length < 5) {
                continue;
            }

            // Comprobar si coincide el ID
            if (camps[0].trim()
                    .equals(id.trim())) {

                int inici = 0;

                // Buscar el inicio de la columna
                // que queremos modificar
                for (int j = 0;
                     j < indexColumna;
                     j++) {

                    inici =
                            linia.indexOf(
                                    ",",
                                    inici
                            ) + 1;
                }

                // Buscar el final del campo
                int finalCamp =
                        linia.indexOf(
                                ",",
                                inici
                        );

                // Si es el último campo
                if (finalCamp == -1) {
                    finalCamp = linia.length();
                }

                // Crear la nueva línea
                String liniaModificada =
                        linia.substring(0, inici)
                        + nouValor
                        + linia.substring(finalCamp);

                // Sustituir la línea original
                treballadorsCSV.set(
                        i,
                        liniaModificada
                );

                trobat = true;

                break;
            }
        }

        // Comprobar si se encontró el trabajador
        if (!trobat) {

            throw new IllegalArgumentException(
                    "No existeix cap treballador amb l'Id: "
                    + id
            );
        }

        // Guardar los cambios en el fichero
        escriureFitxerCSV(
                treballadorsCSV
        );
    }

    /**
     * Lee el fichero CSV.
     */
    private List<String> llegirFitxerCSV()
            throws IOFitxerExcepcio {

        // Leer fichero mediante UtilsCSV
        List<String> treballadorsCSV =
                UtilsCSV.llegir(filePath);

        // Comprobar errores de lectura
        if (treballadorsCSV == null) {

            throw new IOFitxerExcepcio(
                    "Error en llegir el fitxer: "
                    + filePath
            );
        }

        return treballadorsCSV;
    }

    /**
     * Guarda la lista actualizada
     * en el fichero CSV.
     */
    private void escriureFitxerCSV(
            List<String> treballadorsCSV
    ) throws IOFitxerExcepcio {

        try {

            // Escribir contenido al fichero
            UtilsCSV.escriure(
                    filePath,
                    treballadorsCSV
            );

        } catch (IOException e) {

            throw new IOFitxerExcepcio(
                    "Error en escriure el fitxer: "
                    + filePath,
                    e
            );
        }
    }

    /**
     * Punto de entrada del programa.
     */
    public static void main(String[] args) {

        // Crear instancia de la aplicación
        PR123mainTreballadors programa =
                new PR123mainTreballadors();

        // Iniciar programa
        programa.iniciar();
    }
}
