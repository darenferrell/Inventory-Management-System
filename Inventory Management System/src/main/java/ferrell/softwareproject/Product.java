package ferrell.softwareproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();



    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method creates the getter method for the object 'id'.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * This method creates the setter for the object 'id'.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method creates the getter for the string 'name'.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * This method creates the setter for the string 'name'.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method creates the getter for the double value 'price'.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method creates the setter for the double value 'price'.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method creates the setter for the int value 'stock'.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method creates the setter for the int value 'stock'.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method creates the getter for the int value 'min'.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * This method creates the setter for the int value 'min'.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method creates the getter for the int value 'max'.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * This method creates the setter or the int value 'max'.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method adds a part to the associated parts list for the product.
     *
     * @param part
     */
    public void  addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes a part from the associated parts list for the product.
     *
     * @param selectedAssociatedPart The part to delete
     * @return a boolean indicating status of part deletion
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * Gets list of associated parts for the product.
     *
     * @return a list of parts
     */
    public ObservableList<Part> getAllAssociatedParts() {return associatedParts;}
}


