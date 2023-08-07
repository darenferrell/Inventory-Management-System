package ferrell.softwareproject;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static ferrell.softwareproject.Inventory.updatePart;

public class ModifyPartController implements Initializable {
    public AnchorPane modifyPartScreen;
    public Label modPartLbl;
    public Label idLbl;
    public Label nameLbl;
    public Label priceLbl;
    public Label maxLbl;
    public Label minLbl;
    public Button saveBtn;
    public Button cancelBtn;
    public TextField idTxt;
    public TextField nameTxt;
    public TextField priceTxt;
    public TextField maxTxt;
    public TextField minTxt;
    public Label stockLbl;
    public TextField stockTxt;
    public RadioButton modifyPartOutsourcedButton;
    public RadioButton modifyPartInHouseButton;

    public String flag = new String();
    public TextField partIDTxt;
    public Label partIDLbl;
    Part selectedPart;

    public Event event;

    public Stage stage;

    public Parent scene;
    int selectedIndex;

    /**
     * This method allows the user to save any altered details about a product. The method
     * qualifies the data by determining whether these are 'inHouse' or 'outsourced'. It
     * also displays any alerts/errors should the user enter data that is outside the definition
     * of acceptable input.
     * @param event
     * @throws IOException
     */

    @FXML
    void saveProductModificationAction(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(idTxt.getText());
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
            flag = "min value";
            int min = Integer.parseInt(minTxt.getText());
            flag = "max value";
            int max = Integer.parseInt(maxTxt.getText());
            boolean partSuccessfullyAdded = false;

            if (name.isEmpty()) {
                displayAlert(1);
            }
            if (minValid(min, max) && inventoryValid(min, max, stock)) {
                if (modifyPartInHouseButton.isSelected()) {
                    try {
                        int machineID = Integer.parseInt(partIDTxt.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineID);
                        int i = Inventory.getAllParts().indexOf(selectedPart);
                        Inventory.updatePart(i, newInHousePart);
                        partSuccessfullyAdded = true;
                    }
                    catch (NumberFormatException e) {
                        displayAlert(1);
                    }
                }
                    else  {
                        String companyName = partIDTxt.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        int i = Inventory.getAllParts().indexOf(selectedPart);
                        Inventory.updatePart(i, newOutsourcedPart);
                        partSuccessfullyAdded = true;
                    }
                if (partSuccessfullyAdded) {
                    Inventory.deletePart(selectedPart);
                    returnToMainScreen(event);
                }
            }
        } catch (Exception e) {
            displayAlert(1);
        }
    }
    private int uniquePartId() {
        return 0;
    }

    /**
     * This method creates the 'returnToMainScreen' action event so that it can be
     * easily referenced through the controller.
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
     * This method defines the parameters of valid versus invalid 'min' and 'max'
     * values.
     * @param min
     * @param max
     * @return
     */
    private boolean minValid(int min, int max) {
        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(3);
        }
        return isValid;
    }

    /**
     * This method defines whether the 'stock' (inventory) value meets
     * the acceptable requirements.
     * @param min
     * @param max
     * @param stock
     * @return
     */
    private boolean inventoryValid(int min, int max, int stock) {
        boolean isValid = true;
        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(4);
        }
        return isValid;
    }

    /**
     * This method allows the user to cancel the initiated product modification action
     *      * and then returns the user to the main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    private void cancelProductModificationAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Would you like to cancel and return to the main menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            returnToMainScreen(event);
        }
    }

    /**
     * This method retrieves data, and then passes selected data to the Inventory class.
     * @param transferPart
     */
    public void sendPart(Part transferPart) {
        selectedPart = transferPart;
        idTxt.setText(String.valueOf(selectedPart.getId()));
        nameTxt.setText(selectedPart.getName());
        stockTxt.setText(String.valueOf(selectedPart.getStock()));
        priceTxt.setText(String.valueOf(selectedPart.getPrice()));
        maxTxt.setText(String.valueOf(selectedPart.getMax()));
        minTxt.setText(String.valueOf(selectedPart.getMin()));
        if(transferPart instanceof InHouse) {
            partIDTxt.setText(String.valueOf(((InHouse) transferPart).machineId));
            modifyPartInHouseButton.setSelected(true);
            partIDLbl.setText("Machine ID");
        } else {
            partIDTxt.setText(String.valueOf(((Outsourced) transferPart).companyName));
            modifyPartOutsourcedButton.setSelected(true);
            partIDLbl.setText("Company Name");
        }
    }

    /**
     * This method defines various alert types and assigns them to numbered
     * cases that can be referenced by the controller when an error occurs.
     * @param alertType
     */
    private void displayAlert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
        switch (alertType) {
            case 1 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Unable to add product.");
                alert.setContentText("The "+flag+" is blank or invalid.");
                alert.showAndWait();
            }
            case 2 -> {
                alert.setTitle("Information");
                alertInformation.setHeaderText("Part was not found.");
                alertInformation.showAndWait();
            }
            case 3 -> {
                alert.setTitle("Error");
                alert.setHeaderText("The value entered for flag is invalid.");
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
                alert.setHeaderText("Name field is empty.");
                alert.setContentText("Value must be entered in name field.");
                alert.showAndWait();
            }
            case 7 -> {
                alert.setTitle("Error");
                alert.setHeaderText("Non-numeric text entered for 'Machine ID'");
                alert.setContentText("'Machine ID' value must be completely numeric. Please try again.");
                alert.showAndWait();
            }
        }
    }

    /**
     * This method initializes the controller and lays out the components of the
     * object 'selectedPart'.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * This method qualifies and then updates the part based on its assigned
     * affiliation with the 'InHouse'/'Outsourced' screens.
     * @param part
     * @param index
     */
    public void setPart(Part part, int index) {
        selectedPart = part;
        selectedIndex = index;
        if (part instanceof InHouse) {

            InHouse newPart = (InHouse) part;
            modifyPartInHouseButton.setSelected(true);
//            machineIdLbl.setText("Machine ID");
            this.nameTxt.setText(newPart.getName());
            this.stockTxt.setText((Integer.toString(newPart.getStock())));
            this.priceTxt.setText((Double.toString(newPart.getPrice())));
            this.minTxt.setText((Integer.toString(newPart.getMin())));
            this.maxTxt.setText((Integer.toString(newPart.getMax())));
            this.partIDTxt.setText((Integer.toString(newPart.getMachineId())));
            updatePart(selectedIndex, newPart);
        }
        if (part instanceof Outsourced) {
            Outsourced newPart = (Outsourced) part;
            modifyPartOutsourcedButton.setSelected(true);
//            machineIdLbl.setText("Company Name");
            this.nameTxt.setText(newPart.getName());
            this.stockTxt.setText((Integer.toString(newPart.getStock())));
            this.priceTxt.setText((Double.toString(newPart.getPrice())));
            this.minTxt.setText((Integer.toString(newPart.getMin())));
            this.maxTxt.setText((Integer.toString(newPart.getMax())));
            this.partIDTxt.setText(newPart.getCompanyName());
            updatePart(selectedIndex, newPart);
        }
    }

    /**
     * This method creates the 'isOutsourcedAction' action event,
     * and sets the text to 'Company Name' in the event that it
     * is selected.
     * @param actionEvent
     */
    @FXML
    void isOutsourcedAction(ActionEvent actionEvent) {
        partIDLbl.setText("Company Name");
    }

    /**
     * This method creates the "isInHouseAction" action event,
     * and sets the text to "Machine ID" in the event that it
     * is selected.
     * @param actionEvent
     */
    @FXML
    void isInHouseAction(ActionEvent actionEvent) {
        partIDLbl.setText("Machine ID");
    }
}