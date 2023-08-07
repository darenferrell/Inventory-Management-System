package ferrell.softwareproject;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {
    public Label machineIdLbl;
    public TextField machineIdTxt;
    public RadioButton outsourced;
    public TextField addIdTxt;
    public Label priceLbl;
    @FXML
    private Label addPartLbl;

    @FXML
    private AnchorPane addPartScreen;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label idLbl;

    @FXML
    private RadioButton inHouse;

    @FXML
    private Label stockLbl;

    @FXML
    private TextField stockTxt;

    @FXML
    private Label maxLbl;

    @FXML
    private TextField maxTxt;

    @FXML
    private Label minLbl;

    @FXML
    private TextField minTxt;

    @FXML
    private Label nameLbl;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private Button saveBtn;

    public String flag = new String();
    Stage stage;
    Parent scene;
    private Inventory part;
    private boolean partSuccessfullyAdded;


    /**
     * This is a method that allows the user to cancel a potential
     * part addition and return to the main screen.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelAction(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * This method allows the user to save a new part to the inventory class.
     * The method qualifies whether the input is error-ridden, whether the
     * part is classified as 'in-house' or 'outsourced', and displays any
     * appropriate alerts/errors.
     *
     * @param event
     * @throws IOException
     */

    @FXML
    void saveAction(ActionEvent event) throws IOException {
        int uniquePartId = Inventory.getUniquePartId();
        try {
            flag = "name value";
            String name = nameTxt.getText();
            if (name.isEmpty()) {
                displayAlert(1);
                throw new Exception("Name is empty");
            }
            flag = "inventory value";
            int stock = Integer.parseInt(stockTxt.getText());
            flag = "price value";
            double price = Double.parseDouble(priceTxt.getText());
            flag = "max value";
            int max = Integer.parseInt(maxTxt.getText());
            flag = "min value";
            int min = Integer.parseInt(minTxt.getText());
            int machineId;
            String companyName;
            boolean partSuccessfullyAdded = false;

            if (inventoryValid(min, max, stock)) {
                minValid(min, max);
            if (inHouse.isSelected()) {
                try {
                    machineId = Integer.parseInt(machineIdTxt.getText());
                    InHouse newInHousePart = new InHouse(uniquePartId, name, price, stock, min, max, machineId);
                    newInHousePart.setId(Inventory.getNewPartId());
                    Inventory.addPart(newInHousePart);
                    partSuccessfullyAdded = true;
                } catch (NumberFormatException e) {
                    displayAlert(6);
                }
                if (partSuccessfullyAdded) {
                    returnToMainScreen(event);
                }
            } else {
                if (outsourced.isSelected()) {
                    companyName = (machineIdTxt.getText());
                    if (companyName.isEmpty()) {
                        displayAlert(7);
                        throw new Exception("Company Name empty");
                    }
                    Outsourced newOutsourcedPart = new Outsourced(uniquePartId, name, price, stock, min, max, companyName);
                    newOutsourcedPart.setId(Inventory.getNewPartId());
                    Inventory.addPart(newOutsourcedPart);
                    partSuccessfullyAdded = true;

                    if (partSuccessfullyAdded) {
                        returnToMainScreen(event);
                    }
                }
            }
            }
        } catch (NumberFormatException e) {
            displayAlert(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        /**
     * This method allows the program to reference 'returnToMainScreen' and
     * the user is returned to the main screen.
     *
     * @param event
     * @throws IOException
     */
    private void returnToMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method defines the terms of a valid min and max, and when to display an alert.
     *
     * @param min
     * @param max
     * @return
     */
    private boolean minValid(int min, int max) {
        boolean isValid = true;
        if (min < 0 || min > max) {
            isValid = false;
            displayAlert(3);
        }
        return isValid;
    }

    /**
     * This method defines whether the 'stock' value is valid or not based on its
     * relative location to the 'min' and 'max' values.
     *
     * @param min
     * @param max
     * @param stock
     * @return
     */
    private boolean inventoryValid(int min, int max, int stock) {
        boolean isValid = true;
        try {
            if (stock <= min || stock >= max) {
                isValid = false;
                displayAlert(3);
            }
            return isValid;
        } catch (Exception e) {
            displayAlert(3);
        }
        return false;
    }

    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
        switch (alertType) {
            case 1 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Unable to add product.");
                alert.setContentText("The value entered for " + flag + " is blank or invalid.");
                alert.showAndWait();
            }
            case 2 -> {
                alert.setTitle("Information");
                alertInformation.setHeaderText("Part was not found.");
                alertInformation.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error");
                alert.setHeaderText("The value entered for 'min' is higher than the value entered for 'max'.");
                alert.setContentText("The min value must be greater than '0' and less than the max value.");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error");
                alert.setHeaderText("The value entered for 'inventory' is invalid.");
                alert.setContentText("The value must be a number equal to or between the min value and the max value");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Part was not selected.");
                alert.showAndWait();
            }
            case 6 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Unable to add part.");
                    alert.setContentText("The Machine ID field is empty. Please enter a numeric value in this field.");
                    alert.showAndWait();
            }
            case 7 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Unable to add part.");
                alert.setContentText("The Company Name field is empty. Please enter a value in this field.");
                alert.showAndWait();
            }
            case 8 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Non-numeric text entered for 'Machine ID'");
                alert.setContentText("'Machine ID' value must be numeric. Please try again.");
                alert.showAndWait();
                }
            }
        }




    /**
     * This method instructs the program to set the 'Machine ID' label to 'Company Name'
     * in the case that the user selects 'outsourced'.
     *
     * @param actionEvent
     */
    public void isOutsourcedAction(ActionEvent actionEvent) {
        machineIdLbl.setText("Company Name");
    }

    /**
     * This method initializes the controller and sets the boolean value
     * of 'inHouse' to 'true'.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inHouse.setSelected(true);
    }

    /**
     * This method creates the 'isInHouseAction' action event so that it can
     * be active/functional.
     *
     * @param actionEvent
     */
    public void isInHouseAction(ActionEvent actionEvent) {
        machineIdLbl.setText("Machine ID");
    }
}




