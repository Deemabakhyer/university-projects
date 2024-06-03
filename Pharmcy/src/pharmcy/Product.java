/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmcy;

/**
 *
 * @author Layan
 */

 /**
 * The abstract Product class represents a product with a name, price, and category.
 * It contains methods to get the name, price, and category of the product, as well as a toString method.
 */
public abstract class Product {

    private String name;
    private double price;
    private String category;

    /**
     * Constructs a default Product object with no parameters.
     */
    public Product() {
    }

    /**
     * Constructs a Product object with the given name, price, and category.
     *
     * @param name     the name of the product
     * @param price    the price of the product
     * @param category the category of the product
     */
    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    /**
     * Returns the name of the product.
     *
     * @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the product.
     *
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the category of the product.
     *
     * @return the category of the product
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns a string representation of the product, including its name and price.
     *
     * @return a string representation of the product
     */
    @Override
    public String toString() {
        return name + " - $" + price;
    }
}