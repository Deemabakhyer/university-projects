/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmcy;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author user
 */

/**
 * The KidsMedicine class represents a medicine product for kids that extends the Medicine class.
 * It contains information such as the name, amount in mg, and price.
 */
public class KidsMedicine extends Medicine {

    private int Mg;

    /**
     * Constructs a KidsMedicine object with the given name, amount in mg, and price.
     * @param name the name of the kids medicine
     * @param Mg the amount in mg of the kids medicine
     * @param price the price of the kids medicine
     */
    public KidsMedicine(String name, int Mg, double price) {
        super(name, price);
        this.Mg = Mg;
    }

    /**
     * Constructs a default KidsMedicine object with no parameters.
     */
    public KidsMedicine() {
    }

    /**
     * Returns the amount in mg of the kids medicine.
     * @return the amount in mg of the kids medicine
     */
    public int getMg() {
        return Mg;
    }

    /**
     * Sets the amount in mg of the kids medicine.
     * @param Mg the amount in mg of the kids medicine
     */
    public void setMg(int Mg) {
        this.Mg = Mg;
    }

    /**
     * Prints the menu of kids medicines.
     * @param kidsMedicine an ArrayList of KidsMedicine objects
     */
    public void printMenu(ArrayList<KidsMedicine> kidsMedicine) {
        System.out.println("Menu:");
        for (KidsMedicine item : kidsMedicine) {
            String medicineName = item.getName();
            int Mg = item.getMg();
            double price = item.getPrice();
            System.out.printf("NAME OF MEDICINE: %s - AMOUNT IN MG: %d - THE PRICE: %.2f SAR%n", medicineName, Mg, price);
        }
    }

    /**
     * Adds items to the cart.
     * @param items an ArrayList of KidsMedicine objects
     * @param s a Scanner object for user input
     */
    public void addItems(ArrayList<KidsMedicine> items, Scanner s) {
        double price = 0;
        for (KidsMedicine item : items) {
            System.out.println((items.indexOf(item) + 1) + "-" + item.getName());
        }
        boolean b = true;
        while (b) {
            for (int i = 0; i < items.size(); i++) {

                System.out.println("Enter the number of the selected medication or (0) to stop");
                int c = s.nextInt();
                if (c != 0) {
                    System.out.println(items.get(c - 1).getName() + " It was added to the cart successfully");

                } else {
                    b = false;
                    break;
                }
            }

        }

    }
}