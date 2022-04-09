import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Trida k nacteni dat z csv sobuoru
 *
 * @author Samuel Vítek
 */
public class LoadData {
    private double g;
    private double timeStep;
    private List<Planet> planets = new ArrayList<>();

    public LoadData(String name) {
        try {
            File myObj = new File("data/" + name);
            Scanner myReader = new Scanner(myObj);
            String[] firsRow = myReader.nextLine().split(",");
            g = Double.parseDouble(firsRow[0]);
            timeStep = Double.parseDouble(firsRow[1]);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.equals("")) {
                    System.out.println("Posledni radek souboru je prazdny.");
                    return;
                }

                String[] explodedData = data.split(",");

                planets.add(new Planet(
                        explodedData[0],
                        explodedData[1],
                        Double.parseDouble(explodedData[2]),
                        Double.parseDouble(explodedData[3]),
                        Double.parseDouble(explodedData[4]),
                        Double.parseDouble(explodedData[5]),
                        Double.parseDouble(explodedData[6])
                ));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File was not found.");
        }
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public double getG() {
        return g;
    }

    public double getTimeStep() {
        return timeStep;
    }
}
