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
 * Represents a skin care product that extends the general product class.
 */
class SkinCare extends Product {

    /**
     * Indicates whether the skin care product provides moisture.
     */
    boolean moisture;

    /**
     * Constructs a SkinCare object with the specified name, price, category, and moisture indicator.
     *
     * @param name     the name of the skin care product.
     * @param price    the price of the skin care product.
     * @param category the category of the skin care product.
     * @param moisture a boolean indicating whether the product provides moisture.
     */
    public SkinCare(String name, double price, String category, boolean moisture) {
        super(name, price, category);
        this.moisture = moisture;
    }

    /**
     * Retrieves the moisture indicator for the skin care product.
     *
     * @return the boolean value indicating whether the product provides moisture.
     */
    public boolean getmoisture() {
        return moisture;
    }

    /**
     * Sets the moisture indicator for the skin care product.
     *
     * @param moisture a boolean indicating whether the product provides moisture.
     */
    public void setArea(boolean moisture) {
        this.moisture = moisture;
    }

    /**
     * Returns a string representation of the SkinCare object.
     *
     * @return a string representing the details of the SkinCare object, including its name, price, and category.
     */
    @Override
    public String toString() {
        return super.toString();
    }

}