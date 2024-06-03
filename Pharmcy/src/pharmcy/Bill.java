/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmcy;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
/**
 * The Bill class represents a bill that includes a list of products, a bill number, a date, a customer, and a tax percentage.
 * It contains methods to get and set the bill number, date, and customer, as well as to calculate the total amount with tax and print the bill.
 */
public class Bill {

    private PharmacyProduct allProducts;
    private int NumberOfBill;
    private String DateOfBill;
    private Customer customer;
    private double tax = 1;

    /**
     * Constructs a default Bill object with no parameters.
     */
    public Bill() {
    }

    /**
     * Constructs a Bill object with the given bill number, customer, and list of products.
     *
     * @param NumberOfBill the number of the bill
     * @param customer     the customer who made the purchase
     * @param allProducts  the list of products purchased
     */
    public Bill(int NumberOfBill, Customer customer, PharmacyProduct allProducts) {
        this.NumberOfBill = NumberOfBill;
        this.DateOfBill = Bill.Dateof();
        this.customer = customer;
        this.allProducts = allProducts;
    }

    /**
     * Returns the number of the bill.
     *
     * @return the number of the bill
     */
    public int getNumberOfBill() {
        return NumberOfBill;
    }

    /**
     * Sets the number of the bill to the given value.
     *
     * @param NumberOfBill the new number for the bill
     */
    public void setNumberOfBill(int NumberOfBill) {
        this.NumberOfBill = NumberOfBill;
    }

    /**
     * Returns the date of the bill.
     *
     * @return the date of the bill
     */
    public String getDateOfBill() {
        return DateOfBill;
    }

    /**
     * Sets the date of the bill to the given value.
     *
     * @param DateOfBill the new date for the bill
     */
    public void setDateOfBill(String DateOfBill) {
        this.DateOfBill = DateOfBill;
    }

    /**
     * Returns the customer who made the purchase.
     *
     * @return the customer who made the purchase
     */
    public Customer getCustomerName() {
        return customer;
    }

    /**
     * Sets the customer who made the purchase to the given value.
     *
     * @param customer the new customer who made the purchase
     */
    public void setCustomerName(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the current date as a string.
     *
     * @return the current date as a string
     */
    public static String Dateof() {
        Date date = new Date();
        return date.toString();
    }

    /**
     * Calculates the total amount of the purchase with tax.
     *
     * @param TotalAmount the total amount of the purchase
     * @return the total amount of the purchase with tax added
     */
    public double TotalAmount(double TotalAmount) {
        TotalAmount = TotalAmount + Tax(TotalAmount);
        return TotalAmount;
    }

    /**
     * Calculates the amount of tax to be added to the purchase.
     *
     * @param total the total amount of the purchase
     * @return the amount of tax to be added to the purchase
     */
    public double Tax(double total) {
        double taxm = tax * total;
        return taxm;
    }

    /**
     * Prompts the user to choose a method of payment and adds the appropriate tax percentage.
     */
    public void PayingOff() {
        Scanner s = new Scanner(System.in);
        System.out.println("Choose (1) to pay in cash and (2) to pay by card. ");
        int Choose = s.nextInt();
        switch (Choose) {
            case 1:
                System.out.println("ADDING 1% TAX ");
                tax = 1;
                System.out.println("Payment completed successfully ");
                break;
            case 2:
                System.out.println("ADDING 5% TAX ");
                tax = 5;
                System.out.println("Payment completed successfully ");
                break;
        }
    }

  /**
     * Returns a string representation of the bill, including the customer name, bill number, and date.
     *
     * @return a string representation of the bill */
    @Override
    public String toString() {
        String s = "Customer Name:" + customer.getName() + "\n";
        s += "Bill Code:" + NumberOfBill + ", Bill Date:=" + DateOfBill + "\n";
        return s;
    }
/**
     * Prints the bill with the list of products, price after tax, and saves it to a file.
     */
    public void PrintBill() {
        PayingOff();

        System.out.println("\n-------------------------------------------------------");
        System.out.println(toString());
        System.out.println("---------------------------------------------------------");

        double priceaftertax = TotalAmount(allProducts.displayCart());
        System.out.println("---------------------------------------------------------");
        System.out.println("Price After adding "+tax+"% tax:" + priceaftertax);
        System.out.println("---------------------------------------------------------");
        savebilltofile(priceaftertax);
    }
    /**
     * Saves the bill to a file with the price after tax.
     *
     * @param priceaftertax the price of the purchase after tax
     */
    private void savebilltofile(double priceaftertax) {
        try {

            File WriteFile = new File("TestFile.txt");
            //1- (first method)write to the file and delete prevoiuse contents from the filr
            PrintWriter outFile = new PrintWriter(WriteFile);
            outFile.write("....................Your BILL............................");
            outFile.write("\n-------------------------------------------------------");
            outFile.write("\n" + toString());
            outFile.write("---------------------------------------------------------");
            outFile.write("\nPrice After adding 5% tax:" + priceaftertax);
            outFile.write("\n---------------------------------------------------------");
            outFile.close();
        } catch (IOException ex) {
            System.out.println("File not exsist");
        }
    }

   
}

