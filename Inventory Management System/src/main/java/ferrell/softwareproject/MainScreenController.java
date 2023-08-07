package ferrell.softwareproject;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainScreenController implements Initializable {
    public AnchorPane invMgmtSysScreen;
    public Label invManSysLbl;
    public AnchorPane partsTable;
    public Label partsLbl;
    public Button exitBtn;
    public Button addPartBtn;
    public TextField partSearch;
    public TextField productSearch;
    public TableView<Product> productTableView;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productStockCol;
    public TableColumn productPriceCol;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partStockCol;
    public TableColumn partPriceCol;
    public TableView partTableView;
    public Button addProductBtn;
    public Button modifyProductBtn;
    public Button deleteProductBtn;
    public Button modifyPartBtn;
    public Button deletePartBtn;
    public AnchorPane productTable;
    public Label productLbl;
    Stage stage;
    Parent scene;
    private int partId;


    /**
     * This is a method that retrieves the part that the user selects to modify.
     *
     * @return
     */
    public static Part getPartToModify() {
        return null;
    }

    /**
     * This is a method that sends the user to the 'add part' screen when activated.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void addPartAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("addPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is a method that sends the user to the 'modify part' screen when activated.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void modifyPartAction(ActionEvent event) throws IOException {
        Object p = (Part) partTableView.getSelectionModel().getSelectedItem();
        if (p == null) {
            displayAlert(5);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("modifyPart.fxml"));
            scene = loader.load();
            ModifyPartController MPCController = loader.getController();
            p = partTableView.getSelectionModel().getSelectedItem();
            MPCController.sendPart((Part) p);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This is a method that sends the user to the 'modify product' screen when activated.
     *
     * @param event
     * @throws IOException
     */

    /**
     * This is a method that sends the user to the 'modify product' screen when activated.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void modifyProductAction(ActionEvent event) throws IOException {
        Product pr = (Product) productTableView.getSelectionModel().getSelectedItem();
        if (pr == null) {
            displayAlert(5);
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("modifyProduct.fxml"));
            scene = loader.load();
            ModifyProductController MPCController = loader.getController();
            pr = productTableView.getSelectionModel().getSelectedItem();
            MPCController.sendProduct(pr);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This is a method that sends the user to the 'add product' screen when activated.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void addProductAction(ActionEvent event) throws IOException {
        Product pr = (Product) productTableView.getSelectionModel().getSelectedItem();
        {
            Parent parent = FXMLLoader.load(getClass().getResource("addProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * This is a method that deletes a part when activated.
     *
     * @param actionEvent
     */
    public void deletePartAction(ActionEvent actionEvent) {
        System.out.println("Delete button clicked.");
        Part p = (Part) partTableView.getSelectionModel().getSelectedItem();
        if (p == null) {
            displayAlert(5);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(p);
            }
        }
    }

        /**
         * This is a method that deletes a product when activated.
         *
         * @param actionEvent
         */
        public void deleteProductAction (ActionEvent actionEvent){
            System.out.println("Delete button clicked.");
            Product pr = productTableView.getSelectionModel().getSelectedItem();
            if (pr == null) {
                displayAlert(5);
            }
            if(!pr.getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setContentText("Delete Associate part before deleting product");
                alert.show();
                return;
            }
                if (pr == null) {
                    displayAlert(5);
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Alert");
                    alert.setContentText("Are you sure you want to delete this product?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        Inventory.deleteProduct(pr);
                    }
                }
            }

        /**
         * This is a method that closes the program when activated.
         *
         * @param actionEvent
         */
        public void onExitBtn (ActionEvent actionEvent){
            System.exit(0);
        }

        /**
         * This is a method that allows the user to search for a specific part by id or by name.
         * It is also designed to inform the user should an error occur.
         *
         * @param actionEvent
         * */
        public void partSearchAction (ActionEvent actionEvent){
            String textInField = partSearch.getText();
            System.out.println("Text has been grabbed!" + textInField);
            ObservableList<Part> results = Inventory.lookUpPart(textInField);
            try {
                if (results.size() == 0) {
                    int partId = Integer.parseInt(textInField);

                    Part p = Inventory.lookUpPart(partId);
                    if (p == null) {
                        displayAlert(2);
                    } else {
                        results.add(p);
                    }
                }
                partTableView.setItems(results);
            } catch (NumberFormatException e) {
                Alert noPartsFound = new Alert(Alert.AlertType.ERROR);
                noPartsFound.setTitle("Error");
                noPartsFound.setContentText("Part not found.");
                noPartsFound.showAndWait();
                throw new RuntimeException(e);
            }
        }


        /**
         * This is a method that allows the user to search for a specific product by id or by name.
         * It is also designed to inform the user should an error occur.
         *
         * @param actionEvent
         */
        public void productSearchAction (ActionEvent actionEvent){
            String textInField = productSearch.getText();
            System.out.println("Text has been grabbed!" + textInField);
            ObservableList<Product> results = Inventory.lookUpProduct(textInField);
            try {
                if (results.size() == 0) {
                    int productId = Integer.parseInt(textInField);

                    Product pr = Inventory.lookUpProduct(productId);
                    if (pr == null) {
                        displayAlert(2);
                    } else {
                        results.add(pr);
                    }
                }
                productTableView.setItems(results);
            } catch (NumberFormatException e) {
                Alert noPartsFound = new Alert(Alert.AlertType.ERROR);
                noPartsFound.setTitle("Error");
                noPartsFound.setContentText("Part not found.");
                noPartsFound.showAndWait();
                throw new RuntimeException(e);
            }
        }

        /**
         * This is a method that refreshes the tableview to show all parts when the search field is empty.
         *
         * @param keyEvent
         */
        public void keyPressedPartSearchAction (KeyEvent keyEvent){
        }

        /**
         * This is a method that refreshes the tableview to show all products when the search field is empty.
         *
         * @param keyEvent
         */
        public void keyPressedProductSearchAction (KeyEvent keyEvent){
        }

        @Override
        public void initialize (URL location, ResourceBundle resources){
            partTableView.setItems(Inventory.getAllParts());
            partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            productTableView.setItems(Inventory.getAllProducts());
            productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

        private void displayAlert ( int alertType){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
            switch (alertType) {
                case 1 -> {
                    alert.setTitle("Error");
                    alert.setHeaderText("Unable to add product.");
                    alert.setContentText("The entered values are blank or invalid.");
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
                    alert.setHeaderText("Part or product was not selected.");
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
}