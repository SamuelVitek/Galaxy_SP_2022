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
    private double spaceStartX;
    private double spaceEndX;
    private double spaceStartY;
    private double spaceEndY;
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

                Planet actualPlanet = new Planet(
                        explodedData[0],
                        explodedData[1],
                        Double.parseDouble(explodedData[2]),
                        Double.parseDouble(explodedData[3]),
                        Double.parseDouble(explodedData[4]),
                        Double.parseDouble(explodedData[5]),
                        Double.parseDouble(explodedData[6])
                );

                planets.add(actualPlanet);

                if (Double.parseDouble(explodedData[2]) < spaceStartX) {
                    spaceStartX = Double.parseDouble(explodedData[2]) - actualPlanet.getR() / 2;
                }
                else if (Double.parseDouble(explodedData[2]) > spaceEndX) {
                    spaceEndX = Double.parseDouble(explodedData[2]) + actualPlanet.getR() / 2;
                }

                if (Double.parseDouble(explodedData[3]) < spaceStartY) {
                    spaceStartY = Double.parseDouble(explodedData[3]) - actualPlanet.getR() / 2;
                }
                else if (Double.parseDouble(explodedData[3]) > spaceEndY) {
                    spaceEndY = Double.parseDouble(explodedData[3]) + actualPlanet.getR() / 2;
                }
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

    public double getSpaceEndX() {
        return spaceEndX;
    }

    public double getSpaceStartX() {
        return spaceStartX;
    }

    public double getSpaceEndY() {
        return spaceEndY;
    }

    public double getSpaceStartY() {
        return spaceStartY;
    }
}
