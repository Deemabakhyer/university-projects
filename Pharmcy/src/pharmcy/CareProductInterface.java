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
 * Interface for managing care products and shopping.
 */
public interface CareProductInterface {
    /**
     * Adds a product to the shopping cart.
     *
     * @param product the product to be added.
     */
    public void addProduct(Product product);

    /**
     * Starts the shopping process based on the specified type.
     *
     * @param type the type of shopping to start.
     */
    public void startShopping(int type);

    /**
     * Displays the available product categories.
     */
    public void displayCategories();

    /**
     * Displays the total amount in the cart.
     *
     * @return the total amount in the shopping cart.
     */
    public double displayCart();
}