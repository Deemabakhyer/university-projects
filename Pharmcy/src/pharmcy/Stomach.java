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
 * The Stomach class represents a stomach medicine product that extends the Medicine class.
 * It contains information such as the name, category, price, and number of tablets.
 */
public class Stomach extends Medicine {

    /**
     * Constructs a default Stomach object with no parameters.
     */
    public Stomach() {
    }

    /**
     * Constructs a Stomach object with the given name, number of tablets, and price.
     * @param name the name of the stomach medicine
     * @param tablets the number of tablets in the stomach medicine
     * @param price the price of the stomach medicine
     */
    public Stomach(String name, int tablets, double price) {
        super(name, tablets, price);
    }

    /**
     * Constructs a Stomach object with the given name and price.
     * @param name the name of the stomach medicine
     * @param price the price of the stomach medicine
     */
    public Stomach(String name, double price) {
        super(name, price);
    }

    /**
     * Prints the menu of stomach medicines.
     * @param stomach an ArrayList of Stomach objects
     */
    public void printMenu(ArrayList<Stomach> stomach) {
        System.out.println("Menu:");
        for (Stomach item : stomach) {
            String medicineName = item.getName();
            int numberOfTablets = item.getTablets();
            double price = item.getPrice();
            System.out.printf("NAME OF MEDICINE: %s - NUMBER OF TABLETS: %d - THE PRICE: %.2f SAR%n", medicineName, numberOfTablets, price);
        }
    }

    /**
     * Adds items to the cart.
     * @param items an ArrayList of Stomach objects
     * @param s a Scanner object for user input
     */
    public void addItems(ArrayList<Stomach> items, Scanner s) {
        double price = 0;
        for (Stomach item : items) {
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