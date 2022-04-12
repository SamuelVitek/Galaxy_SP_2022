import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Třída sloužící k načtení dat z předaného souboru
 *
 * @author Samuel Vítek
 */
public class LoadData {
    /** Gravitační síla */
    private double g;
    /** Časový krok za 1s */
    private double timeStep;
    /** Kolekce všech načtených planet */
    private final List<Planet> planets = new ArrayList<>();

    /**
     * Konstruktor naplňující všechny atributy
     *
     * @param path cesta k souboru
     */
    public LoadData(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            //Rozdělení řádek podle čárky a uložení do pole stringů
            String[] firsRow = myReader.nextLine().split(",");
            g = Double.parseDouble(firsRow[0]);
            timeStep = Double.parseDouble(firsRow[1]);

            //Procházení pole a uložení všech hodnot
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("")) {
                    System.out.println("Posledni radek souboru je prazdny.");
                    return;
                }

                //Rozdělení řádek podle čárky a uložení do pole stringů
                String[] explodedData = data.split(",");

                //Vytvoření instance planety
                Planet actualPlanet = new Planet(
                        explodedData[0],
                        explodedData[1],
                        Double.parseDouble(explodedData[2]),
                        Double.parseDouble(explodedData[3]),
                        Double.parseDouble(explodedData[4]),
                        Double.parseDouble(explodedData[5]),
                        Math.abs(Double.parseDouble(explodedData[6]))
                );

                planets.add(actualPlanet);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File was not found.");
        }
    }

    /**
     * Vrátí kolekci s planetami
     *
     * @return kolekce planet
     */
    public List<Planet> getPlanets() {
        return planets;
    }

    /**
     * Vrátí hodnotu gravitační přitažlivosti g
     *
     * @return g
     */
    public double getG() {
        return g;
    }

    /**
     * Vrátí hodnotu časového kroku
     *
     * @return časový krok
     */
    public double getTimeStep() {
        return timeStep;
    }
}
