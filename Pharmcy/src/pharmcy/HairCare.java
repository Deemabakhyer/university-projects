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
*Represents a hair care product that extends the general product class.
 */
class HairCare extends Product {

    /**
     * The type of hair for the specific hair care product.
     */
    String hairtype;

    /**
     * Constructs a HairCare object with the specified name, price, category, and hair type.
     *
     * @param name     the name of the hair care product.
     * @param price    the price of the hair care product.
     * @param category the category of the hair care product.
     * @param hairtype the type of hair associated with the hair care product.
     */
    public HairCare(String name, double price, String category, String hairtype) {
        super(name, price, category);
        this.hairtype = hairtype;
    }

    /**
     * Retrieves the type of hair associated with the hair care product.
     *
     * @return the type of hair associated with the hair care product.
     */
    public String gethairtype() {
        return hairtype;
    }

    /**
     * Sets the type of hair for the hair care product.
     *
     * @param hairtype the type of hair to be set.
     */
    public void sethairtype(String hairtype) {
        this.hairtype = hairtype;
    }

    /**
     * Returns a string representation of the HairCare object.
     *
     * @return a string representing the details of the HairCare object, including its name, price, category, and hair type.
     */
    @Override
    public String toString() {
        return super.toString() + "-" + hairtype;
    }

}