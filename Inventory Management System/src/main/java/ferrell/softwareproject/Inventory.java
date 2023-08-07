package ferrell.softwareproject;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partId = 0;



    /**
     * This is a method that calls for the unique part id,
     * returns the value, and then automatically increments the
     * index where the last assigned part left off.
     * @return
     */
    public static int getUniquePartId() {
        return partId++;
    }
    private static int productId = 0;

    /**
     * This method calls for the unique product id,
     * returns the value, and then automatically increments
     * the index where the last assigned product left off.
     * @return
     */
    public static int getUniqueProductId() {
        return productId++;
    }

    /**
     * This method adds parts to the 'allParts' list.
     * @param part
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * This method calls for the 'allParts' list and returns the values.
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method adds a product to the allProducts list.
     * @param product
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * This method calls for the 'allProducts' list and returns the values.
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    /**
     * This is a method that deletes a part from the 'allParts' list.
     * @param part
     * @return boolean
     */
    public static boolean deletePart(Object part) {
        boolean result = allParts.remove(part);
        return result;
    }
    /**
     * This is a method that deletes a product
     * @param product
     * @return
     */
    public static boolean deleteProduct(Product product) {

        return allProducts.remove(product);
    }
    /**
     * This is a method that updates the features of a part.
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /**
     * This is a method that updates the features of a product.
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * This method allows the user to search through the inventory and returns
     * the matching part.
     * @param partId
     * @return
     */
    public static Part lookUpPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId)
                return part;
        }
        return null;
    }
    /**
     * This method allows the user to search through the parts list,
     * adds the selected part to the list, and returns the updated
     * version of the list.
     * the tempParts list.
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> tempParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase()))
                tempParts.add(part);
        }
        return tempParts;
    }
    /**
     * This method allows the user to search through the products list,
     * and matches the product to any matching results.
     * @param productId
     * @return
     */
    public static Product lookUpProduct(int productId) {
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() == productId)
                return product;
        }
        return null;
    }

    /**
     * This method allows the user to search through the products list,
     * adds the selected product to the list, and returns the updated
     * version of the list.
     * the tempParts list.
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookUpProduct(String productName) {
        ObservableList<Product> tempProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(productName.trim().toLowerCase()))
                tempProducts.add(product);
        }
        return tempProducts;
    }

    public static int getProductId() {
        return productId;
    }

    public static void setProductId() {
        Inventory.productId = productId;
    }

    public static int getNewPartId() {
        return partId;
    }
}