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
 * The CoughCold class represents a cough and cold medicine product that extends the Medicine class.
 * It contains information such as the name, category, price, and number of tablets.
 */
public class CoughCold extends Medicine{
    
    /**
     * Constructs a CoughCold object with the given name and price.
     * @param name the name of the cough and cold medicine
     * @param price the price of the cough and cold medicine
     */
    public CoughCold(String name, double price) {
        super(name, price);
    }

    /**
     * Constructs a default CoughCold object with no parameters.
     */
    public CoughCold() {
    }

    /**
     * Constructs a CoughCold object with the given name, number of tablets, and price.
     * @param name the name of the cough and cold medicine
     * @param tablets the number of tablets in the cough and cold medicine
     * @param price the price of the cough and cold medicine
     */
    public CoughCold(String name, int tablets, double price) {
        super(name, tablets, price);
    }

    /**
     * Prints the menu of cough and cold medicines.
     * @param CoughCold an ArrayList of CoughCold objects
     */
    public void printMenu(ArrayList<CoughCold > CoughCold  ) {
        System.out.println("Menu:");
        for ( CoughCold  item : CoughCold ) {
            System.out.println("NAME OF MEDICINE:"+ item.getName() +"   - NUMBER OF TABLETS:" + item.getTablets() + "    -  THE PRICE:" + item.getPrice() + "SAR");
        }
    }
   
    /**
     * Adds items to the cart.
     * @param items an ArrayList of CoughCold objects
     * @param s a Scanner object for user input
     */
    public void addItems(ArrayList<CoughCold> items, Scanner s) {
        double price = 0;
        for (CoughCold item : items) {
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