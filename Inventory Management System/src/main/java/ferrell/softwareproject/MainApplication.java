package ferrell.softwareproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Author: Daren Ferrell
 * JAVADOCS FOLDER LOCATION: In this project, under the 'src' folder.
 * FUTURE ENHANCEMENTS: A helpful feature that could be added in the future is the addition of a program
 * that makes suggestions about the types of parts that the user may want to add to the product or to the parts
 * list based on the nature of the items kept in the database by the user. For example, if the parts department.
 */
public class MainApplication extends Application {
    public static Part getProductToModify() {
        return null;
    }

    public static Part getPartToModify() {
        return null;
    }

    /**
     *This method initializes and sets up the MainScreenController upon the program's launch.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *This method stores all of the test data and once called, passes it into the various controllers.
     * @param args
     */
    public static void main(String[] args)
    {
        Part bandAids = new InHouse(Inventory.getUniquePartId(), "band aids", 450.00, 34, 2, 50, 4903);
        Inventory.addPart(bandAids);
        Part napalmAgent = new InHouse(Inventory.getUniquePartId(), "napalm agent", 375, 13, 5, 45, 3987);
        Inventory.addPart(napalmAgent);
        Part dehydratedMilk = new InHouse(Inventory.getUniquePartId(), "Dehydrated Milk", 43829, 4, 2, 6, 8441);
        Inventory.addPart(dehydratedMilk);
        Part radPills = new InHouse(Inventory.getUniquePartId(), "Rob's Radiation Recovery Pills ", 35, 137, 10, 200, 19);
        Inventory.addPart(radPills);
        Part wires = new InHouse(Inventory.getUniquePartId(), "Pack of 5 wires", 20, 10, 1, 100, 20);
        Inventory.addPart(wires);
        Product firstAidKit = new Product(Inventory.getUniqueProductId(), "first aid kit", 35, 137, 10, 200);
        Inventory.addProduct(firstAidKit);
        Product napalmKit = new Product(Inventory.getUniqueProductId(), "Napalm Kit", 29.95, 19, 1, 50);
        Inventory.addProduct(napalmKit);
        Product milkshakeMix = new Product(Inventory.getUniqueProductId(), "End of the World Milkshake Mix", 17.50, 5, 1, 10);
        Inventory.addProduct(milkshakeMix);
        Product radiationRecoveryKit = new Product(Inventory.getUniqueProductId(), "Nuclear Radiation Recovery Kit", 35, 18, 1, 30);
        Inventory.addProduct(radiationRecoveryKit);
        Product apocPal = new Product(Inventory.getUniqueProductId(), "Apocalypto-pal - An 'end times' robotic friend!", 945, 7, 1, 10);
        Inventory.addProduct(apocPal);
        launch();
    }
}
