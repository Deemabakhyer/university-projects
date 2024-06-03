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
 * The BodyCare class represents a body care product that extends the Product class and includes an additional area attribute.
 * It contains methods to get and set the area of the body care product, as well as to convert the object to a string representation.
 */
public class BodyCare extends Product {
    private String area;

    /**
     * Constructs a BodyCare object with the given name, price, category, and area.
     *
     * @param name     the name of the body care product
     * @param price    the price of the body care product
     * @param category the category of the body care product
     * @param area     the area of the body care product
     */
    public BodyCare(String name, double price, String category, String area) {
        super(name, price, category);
        this.area = area;
    }

    /**
     * Returns the area of the body care product.
     *
     * @return the area of the body care product
     */
    public String getArea() {
        return area;
    }

    /**
     * Sets the area of the body care product to the given value.
     *
     * @param area the new area for the body care product
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * Returns a string representation of the body care product, including its name, price, category, and area.
     *
     * @return a string representation of the body care product
     */
    @Override
    public String toString() {
        return super.toString() + "-" + area + '}';
    }
}