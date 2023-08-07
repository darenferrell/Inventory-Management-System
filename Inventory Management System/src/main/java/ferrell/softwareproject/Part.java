package ferrell.softwareproject;

/**
 * Supplied class Part.java
 */

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Place Your Name Here
 */
public abstract class Part  {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**The getter for 'id'.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**The setter for 'id'.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**The getter for 'name'.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**The setter for 'name'.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**The getter for 'price'.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**The setter for 'price'.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**The getter for 'stock'.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**The setter for 'stock'.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**The getter for 'min'.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**The setter for 'min'.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**The getter for 'max'.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**The setter for 'max'.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
}
