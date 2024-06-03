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
/**
* 
*/

/**
 * The PainRelief class represents a pain relief medicine product that extends the Medicine class.
 * It contains information such as the name, category, price, and number of tablets.
 */
public class PainRelief extends Medicine {

    /**
     * Constructs a default PainRelief object with no parameters.
     */
    public PainRelief() {
    }

    /**
     * Constructs a PainRelief object with the given name, number of tablets, and price.
     * @param name the name of the pain relief medicine
     * @param tablets the number of tablets in the pain relief medicine
     * @param price the price of the pain relief medicine
     */
    public PainRelief(String name, int tablets, double price) {
        super(name, tablets, price);
    }

    /**
     * Prints the menu of pain relief medicines.
     * @param Painrelief an ArrayList of PainRelief objects
     */
    public void printMenu(ArrayList<PainRelief> Painrelief) {
        System.out.println("Menu:");
        for (PainRelief item : Painrelief) {
            String medicineName = item.getName();
            int numberOfTablets = item.getTablets();
            double price = item.getPrice();
            System.out.printf("NAME OF MEDICINE: %s - NUMBER OF TABLETS: %d - THE PRICE: %.2f SAR%n", medicineName, numberOfTablets, price);
        }
    }

    /**
     * Adds items to the cart.
     * @param items an ArrayList of PainRelief objects
     * @param s a Scanner object for user input
     */
    public void addItems(ArrayList<PainRelief> items, Scanner s) {
        double price = 0;
        for (PainRelief item : items) {
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