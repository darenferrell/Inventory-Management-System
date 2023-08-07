package ferrell.softwareproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProductController implements Initializable {

    public TableColumn associatedPartIdCol;
    public TableColumn associatedPartNameCol;
    public TableColumn associatedPartStockCol;
    public TableColumn associatedPartPriceCol;
    public TableColumn partStockCol;
    public TableView associatedPartsTableView;
    public TableView partsTableView;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partPriceCol;
    public AnchorPane addProductScreen;
    public Label addProductLbl;
    public Label nameLbl;
    public Label invLbl;
    public Label priceLbl;
    public Label maxLbl;
    public Label minLbl;
    public Button saveBtn;
    public Button cancelBtn;
    public Button removeBtn;
    public Button addBtn;
    public TextField addNameTxt;
    public TextField addStockTxt;
    public TextField addPriceTxt;
    public TextField addMaxTxt;
    public TextField addMinTxt;
    public TextField productSearchTxt;
    public TextField addProductTxt;
    public Label productIdLbl;
    Stage stage;
    Parent scene;
    public String flag = new String();
    private EventObject event;
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This is a method that allows the user to add a product to the associated parts table view.
     * @param actionEvent
     */
    @FXML
    void addProductAction(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayAlert(5);
        } else {
            associatedParts.add(selectedPart);
            associatedPartsTableView.setItems(associatedParts);
        }
    }

    /**
     * This is a method that sends part information to the
     * @param part
     */
    public void sendPart(Part part) {
        productIdLbl.setText(String.valueOf(part.getId()));
    }

    /**
     * This is a method that cancels 'add product' changes and returns the user to the main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelProductAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Would you like to cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This method allows the user to search for a product by 'id' or 'name'.
     * @param actionEvent
     */
    public void productSearchAction(ActionEvent actionEvent) {
        String textInField = productSearchTxt.getText();
        System.out.println("Text has been grabbed!" + textInField);
        ObservableList<Part> results = Inventory.lookUpPart(textInField);
        try {
            if (results.size() == 0) {
                int partid = Integer.parseInt(textInField);

                Part pr = Inventory.lookUpPart(partid);
                if (pr == null) {
                    displayAlert(2);
                } else {
                    results.add(pr);
                }
            }
            partsTableView.setItems(results);
        } catch(NumberFormatException e){
            Alert noPartsFound = new Alert(Alert.AlertType.ERROR);
            noPartsFound.setTitle("Error");
            noPartsFound.setContentText("Part not found.");
            noPartsFound.showAndWait();
          //  throw new RuntimeException(e);
        }
    }
    /**
     * @return This is a method that creates a unique product identification from the Inventory class.
     * It then automatically increments the count so that it continues from the highest item on the list.
     */
    private int getUniqueProductID(){
        int newID = 1;
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getId() == newID) {
                newID++;
            }
        }
        return newID;
    }
    /**
     * @param actionEvent This is a method that allows the user to delete a product.
     */
    public void deleteProductAction(ActionEvent actionEvent) {
        Object selectedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayAlert(5);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Would you like to remove the selected product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                associatedPartsTableView.setItems(associatedParts);
            }
        }
    }
    /**
     * This method allows the user to save newly added products
     * and displays alerts if methods are employed that are not consistent with the approved method.
     * @param event
     * @throws IOException
     */
    @FXML
    void saveProductAction(ActionEvent event) throws IOException {
        int id = Inventory.getUniqueProductId();
        try {
            flag = "name value";
            String name = addNameTxt.getText();
            flag = "price value";
            double price = Double.parseDouble(addPriceTxt.getText());
            flag = "inventory value";
            int stock = Integer.parseInt(addStockTxt.getText());
            flag = "max value";
            int max = Integer.parseInt(addMaxTxt.getText());
            flag = "min value";
            int min = Integer.parseInt(addMinTxt.getText());
            boolean productSuccessfullyAdded = false;

            if (addNameTxt.getText().isEmpty()) displayAlert(6);
            Product newProduct = new Product(getUniqueProductID(), name, price, stock, min, max);

            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }
            Inventory.addProduct(newProduct);
            productSuccessfullyAdded = true;
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException e) {
            displayAlert(1);
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @param min This is a method that determines whether the user has added a min/max within the approved limits.
     * @param max
     * @return
     */
    private Boolean minValid(int min, int max) {
        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(3);
        }
        return isValid;
    }
    /**
     * This is a method that determines whether the user has added a product
     * that contains an appropriate min, max, and stock/inventory value.
     * @param min
     * @param max
     * @param stock
     * @return
     */
    private Boolean inventoryValid(int min, int max, int stock) {
        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
            displayAlert(4);
        }
        return isValid;
    }
    /**
     * @param alertType This is a method that displays alerts and corresponding
     *                  information when an error is registered by the user.
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
                alert.setHeaderText("The value entered for 'min' is invalid.");
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
                alert.setHeaderText("Numeric text entered for 'Name'");
                alert.setContentText("'Name' value must be non-numeric. Please try again.");
                alert.showAndWait();
            }
        }
    }
    /**
     * This is a method that initializes the controller and populates the tableviews.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(associatedParts);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

