package ferrell.softwareproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.Optional;
import java.util.ResourceBundle;
import static ferrell.softwareproject.Inventory.*;

public class ModifyProductController implements Initializable {
    public Label idLbl;
    public Label nameLbl;
    public Label invLbl;
    public Label priceLbl;
    public Label maxLbl;
    public Label minLbl;
    public Button saveBtn;
    public Button cancelBtn;
    public Button removeBtn;
    public Button addBtn;
    public TableView partTblView1;
    public TableColumn partIdCol1;
    public TableColumn partNameCol1;
    public TableColumn invLvlCol1;
    public TableColumn priceCol1;
    public TableView associatedPartsTable;
    public TableColumn associatedPartIdCol;
    public TableColumn associatedPartNameCol;
    public TableColumn associatedStockCol;
    public TableColumn associatedPriceCol;
    public TextField productId;
    public TextField productName;
    public TextField productStock;
    public TextField productPrice;
    public TextField productMax;
    public TextField productMin;
    public Label modifyProductLbl;
    public TextField modifyProductSearch;
    Product selectedProduct;
    ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();
    public String flag = new String();
    Stage stage;
    Parent scene;
    private EventObject event;
    public int indexOf;
    private boolean productSuccessfullyAdded;


    /**
     * This method allows the user to add a product from the table view. It then
     * qualifies the data, and adds it to the 'tempAssociatedParts' list.
     * @param actionEvent
     */
    public void addProductAction(ActionEvent actionEvent) {
        Part selectedPart = (Part) partTblView1.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        } else {
            tempAssociatedParts.add(selectedPart);
            associatedPartsTable.setItems(tempAssociatedParts);
        }
    }

    /**
     * This method allows the user to update product information.
     * Essentially, the method defines and qualifies the objects/parameters,
     * and then gets the old part and its index. Then, the method takes
     * the permanent data and adds it to a temporary list.
     * @param actionEvent
     * @throws IOException
     */
    /**
     * RUNTIME ERROR:Caused by: java.lang.NullPointerException: Cannot
     * invoke "java.util.EventObject.getSource()" because "this.event" is null
     * 	at ferrell.softwareproject/ferrell.softwareproject.
     * 	ModifyProductController.saveProductAction(ModifyProductController.java:112)
     * 	Essentially, this error was caused by omission of the word 'action'. I only
     * 	had the word 'event'. Once I corrected this on line 119 of the
     * 	'ModifyProductController', the controller modified and saved properly.
     *
     * RUNTIME ERROR: Caused by: java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 5
     * 	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
     * 	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
     * 	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
     * 	at java.base/java.util.Objects.checkIndex(Objects.java:359)
     * 	at java.base/java.util.ArrayList.set(ArrayList.java:441)
     * 	at javafx.base/com.sun.javafx.collections.ObservableListWrapper.doSet(ObservableListWrapper.java:106)
     * 	at javafx.base/javafx.collections.ModifiableObservableListBase.set(ModifiableObservableListBase.java:167)
     * 	at ferrell.softwareproject/ferrell.softwareproject.Inventory.updateProduct(Inventory.java:92)
     * 	at ferrell.softwareproject/ferrell.softwareproject.ModifyProductController.saveProductAction(ModifyProductController.java:116)
     * 	... 57 more
     *
     * 	This runtime error occurred because I had written the code for the save action section, but had neglected to include the
     * 	function that would allow the program to exit to the main screen. I solved this problem by adding lines 130-133,
     * 	which provided the program with an exit strategy.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void saveProductAction(ActionEvent actionEvent) throws IOException {
        int id = Inventory.getUniquePartId();
        try {
            flag = "name value";
            String name = productName.getText();
            flag = "inventory value";
            int stock = Integer.parseInt(productStock.getText());
            flag = "price value";
            double price = Double.parseDouble(productPrice.getText());
            flag = "max value";
            int max = Integer.parseInt(productMax.getText());
            flag = "min value";
            int min = Integer.parseInt(productMin.getText());
            boolean productSuccessfullyAdded = false;


            if (minValid(min, max) && inventoryValid(min, max, stock)) {

                int index = Inventory.getAllProducts().indexOf(selectedProduct);
                Product updatedProduct = new Product(getUniqueProductId(), name, price, stock, min, max);
                for (Part temp : tempAssociatedParts) {
                    updatedProduct.addAssociatedPart(temp);
                }
                Inventory.updateProduct(index, updatedProduct);
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        } catch (NumberFormatException e) {
            displayAlert(1);
            throw new RuntimeException(e);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
    private boolean minValid(int min, int max) {
        boolean isValid = true;

        if (min <= 0 || min >= max) {
            isValid = false;
            displayAlert(3);
        }
        return isValid;
    }
    /**
     * This method allows the user to cancel a product update and returns the user
     * to the main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelProductUpdateAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method allows the user to delete an associated part from the associated
     * part list.
     * @param actionEvent
     */
    public void removeAssociatedPartAction(ActionEvent actionEvent) {
        Object selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            displayAlert(5);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Would you like to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                tempAssociatedParts.remove(selectedPart);
                associatedPartsTable.setItems(tempAssociatedParts);
            }
        }
    }

    /**
     * This method enables the user to search for a particular product and then
     * add the product to the table view.
     * @param actionEvent
     */
    public void productSearchAction(ActionEvent actionEvent) {
        String textInField = modifyProductSearch.getText();
        System.out.println("Text has been grabbed!" + textInField);
        ObservableList<Product> results = Inventory.lookUpProduct(textInField);
        try {
            if (results.size() == 0) {
                int partId = Integer.parseInt(textInField);

                Product p = Inventory.lookUpProduct(partId);
                if (p == null) {
                    displayAlert(2);
                } else {
                    results.add(p);
                }
            }
            partTblView1.setItems(results);
        } catch(NumberFormatException e){
            Alert noPartsFound = new Alert(Alert.AlertType.ERROR);
            noPartsFound.setTitle("Error");
            noPartsFound.setContentText("Part not found.");
            noPartsFound.showAndWait();
            throw new RuntimeException(e);
        }
    }


    /**
     * This method enables the user to clear the product table view
     * once the search data is cleared from the text field.
     * @param event
     */
    @FXML
    void searchKeyPressed(KeyEvent event) {

        if (modifyProductSearch.getText().isEmpty()) {
            partTblView1.setItems(Inventory.getAllParts());
        }
    }

    /**
     * This method creates the boolean method 'minValid', its sub-parameters,
     * and its assigned return value.
     * @param min
     * @param max
     * @return
     */


    /**
     * This method creates the boolean method 'inventoryValid', its sub-parameters,
     * and its assigned return value.
     * @param min
     * @param max
     * @param stock
     * @return
     */
    private boolean inventoryValid(int min, int max, int stock) {
        boolean isValid = true;
        try {
            if (stock < min || stock > max) {
                isValid = false;
                displayAlert(1);
            }
            return isValid;
        }
        catch (Exception e) {
            displayAlert(1);
        }
        return false;
    }

    /**
     * This method creates and defines various alert types/messages and assigns
     * each to a case number that can be accessed by the controller when improper
     * input is entered by the user.
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
                alert.setHeaderText("Non-numeric text entered for 'Machine ID'");
                alert.setContentText("'Machine ID' value must be numeric. Please try again.");
                alert.showAndWait();
            }
        }
    }

    /**
     * This method sends the product data to the Inventory class. Essentially, it takes the
     * data from the temporary list and applies it to permanents storage.
     * @param transferProduct
     */
    public void sendProduct(Product transferProduct) {
        selectedProduct = transferProduct;
        for(Part temp: selectedProduct.getAllAssociatedParts()) {
            tempAssociatedParts.add(temp);
        }
        productId.setText(String.valueOf(selectedProduct.getId()));
        productName.setText(selectedProduct.getName());
        productStock.setText(String.valueOf(selectedProduct.getStock()));
        productPrice.setText(String.valueOf(selectedProduct.getPrice()));
        productMax.setText(String.valueOf(selectedProduct.getMax()));
        productMin.setText(String.valueOf(selectedProduct.getMin()));
    }

    /**
     * This method initializes the controller and populates the table views.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partTblView1.setItems(Inventory.getAllParts());
        partIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLvlCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));


        associatedPartsTable.setItems(tempAssociatedParts);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}