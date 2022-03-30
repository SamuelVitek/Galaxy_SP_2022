import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Trida k nacteni dat z csv sobuoru
 *
 * @author Samuel Vítek
 */
public class LoadData {
    private double G;
   // private HashMap<>

    public LoadData(String name) {
        try {
            File myObj = new File("data/" + name);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File was not found.");
        }
    }


}
