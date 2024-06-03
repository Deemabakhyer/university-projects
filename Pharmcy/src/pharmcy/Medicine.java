/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmcy;



/**
 *
 * @author sadeel
 */
/**
 * The Medicine class represents a medicine product that extends the Product
 * class. It contains information such as the name, category, price, and number
 * of tablets.
 */
public class Medicine extends Product {

    private String name;
    private String Category;
    private double price;
    private int tablets;

    /**
     * Constructs a default Medicine object with no parameters.
     */
    public Medicine() {
    }

    /**
     * Constructs a Medicine object with the given name, number of tablets, and
     * price.
     *
     * @param name the name of the medicine
     * @param tablets the number of tablets in the medicine
     * @param price the price of the medicine
     */
    public Medicine(String name, int tablets, double price) {
        this.name = name;
        this.price = price;
        this.tablets = tablets;
        Category = "Medicine";
    }

    /**
     * Constructs a Medicine object with the given name and price.
     *
     * @param name the name of the medicine
     * @param price the price of the medicine
     */
    public Medicine(String name, double price) {
        this.name = name;
        this.price = price;
        Category = "Medicine";
    }

    /**
     * Returns the name of the medicine.
     *
     * @return the name of the medicine
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the medicine.
     *
     * @param name the name of the medicine
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the category of the medicine.
     *
     * @return the category of the medicine
     */
    public String getCategory() {
        return Category;
    }

    /**
     * Sets the category of the medicine.
     *
     * @param Category the category of the medicine
     */
    public void setCategory(String Category) {
        this.Category = Category;
    }

    /**
     * Returns the price of the medicine.
     *
     * @return the price of the medicine
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the medicine.
     *
     * @param price the price of the medicine
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the number of tablets in the medicine.
     *
     * @return the number of tablets in the medicine
     */
    public int getTablets() {
        return tablets;
    }

    /**
     * Sets the number of tablets in the medicine.
     *
     * @param tablets the number of tablets in the medicine
     */
    public void setTablets(int tablets) {
        this.tablets = tablets;
    }

    /**
     * Returns a string representation of the Medicine object.
     *
     * @return a string representation of the Medicine object
     */
    @Override
    public String toString() {
        return name + " - $" + price + '$';
    }


}
