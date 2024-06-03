/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmcy;


import java.util.Scanner;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author sadeel
 */
/**
 * The Pharmacist class represents a pharmacist object with an ID, name, and customer.
 * It contains methods to get and set the ID, name, and customer, as well as methods to get work shifts,
 * and arrays of pharmacist IDs and names.
 */
public class Pharmacist {

    Scanner c = new Scanner(System.in);
    private int id;
    private String name;
    private Customer newCustomer1;

    /**
     * Constructs a Pharmacist object with the given ID and name.
     * @param id the ID of the pharmacist
     * @param name the name of the pharmacist
     */
    public Pharmacist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructs a Pharmacist object with the given ID, name, and customer.
     * @param id the ID of the pharmacist
     * @param name the name of the pharmacist
     * @param newCustomer the customer of the pharmacist
     */
    public Pharmacist(int id, String name, Customer newCustomer) {
        this.id = id;
        this.name = name;
        this.newCustomer1 = newCustomer;
    }

    /**
     * Constructs a default Pharmacist object with no parameters.
     */
    public Pharmacist() {
    }

    /**
     * Constructs a Pharmacist object with the given ID.
     * @param id the ID of the pharmacist
     */
    public Pharmacist(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of the pharmacist.
     * @return the ID of the pharmacist
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the pharmacist.
     * @param id the ID of the pharmacist
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the pharmacist.
     * @return the name of the pharmacist
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pharmacist.
     * @param name the name of the pharmacist
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the customer of the pharmacist.
     * @return the customer of the pharmacist
     */
    public Customer getNewCustomer1() {
        return newCustomer1;
    }

    /**
     * Sets the customer of the pharmacist.
     * @param newCustomer1 the customer of the pharmacist
     */
    public void setNewCustomer1(Customer newCustomer1) {
        this.newCustomer1 = newCustomer1;
    }

    /**
     * Returns an array of pharmacist IDs.
     * @return an array of pharmacist IDs
     */
    public int[] ids() {
        int[] pharmacistIDS = {1, 2, 3, 4, 5, 6};
        return pharmacistIDS;
    }

    /**
     * Returns an array of pharmacist names.
     * @return an array of pharmacist names
     */
    public String[] names() {
        String[] names = {"sadeel", "dema", "bayan", "norah", "layan", "remas"};
        return names;
    }

    /**
     * Prints the work shifts for a given pharmacist ID.
     * @param n the ID of the pharmacist
     */
    public void workShifts(int n) {
        if (n == 1) {
            System.out.println("Sunday: 8am to 4pm ");
            System.out.println("Monday: 8am to 4pm");
            System.out.println("Tuesday:4pm to 12am");
            System.out.println("Wednesday:off");
            System.out.println("Thursday:12am to 8am");
            System.out.println("Friday:12am to 8am");
            System.out.println("Saturday:4pm to 12am");
        } else if (n == 2) {
            System.out.println("Sunday:     8am to 4pm ");
            System.out.println("Monday:     8am to 4pm");
            System.out.println("Tuesday:    off");
            System.out.println("Wednesday:  4pm to 12am");
            System.out.println("Thursday:   12am to 8am");
            System.out.println("Friday:     12am to 8am");
            System.out.println("Saturday:   4pm to 12am");
        } else if (n == 3) {
            System.out.println("Sunday:     12am to 8am");
            System.out.println("Monday:     12am to 8am");
            System.out.println("Tuesday:    4pm to 12am");
            System.out.println("Wednesday:  4pm to 12am");
            System.out.println("Thursday:   off");
            System.out.println("Friday:     8am to 4pm");
            System.out.println("Saturday:   8am to 4pm");
        } else if (n == 4) {
            System.out.println("Sunday:     12am to 8am");
            System.out.println("Monday:     12am to 8am");
            System.out.println("Tuesday:    12am to 8am");
            System.out.println("Wednesday:  4pm to 12am");
            System.out.println("Thursday:   8am to 4pm");
            System.out.println("Friday:     off");
            System.out.println("Saturday:   8am to 4pm");
        } else if (n == 5) {
            System.out.println("Sunday:     4pm to 12am");
            System.out.println("Monday:     4pm to 12am");
            System.out.println("Tuesday:    4pm to 12am");
            System.out.println("Wednesday:  4pm to 12am");//
            System.out.println("Thursday:   8am to 4pm");
            System.out.println("Friday:     8am to 4pm");
            System.out.println("Saturday:   off");
        } else if (n == 6) {
            System.out.println("Sunday:     off");
            System.out.println("Monday:     4pm to 12am");
            System.out.println("Tuesday:    4pm to 12am");
            System.out.println("Wednesday:  4pm to 12am");//
            System.out.println("Thursday:   8am to 4pm");
            System.out.println("Friday:     8am to 4pm");
            System.out.println("Saturday:  12am to 8am ");
        }

        //  LocalDate date=LocalDate.of(n, n, n);
    }//نهاية ميثود جدول العمل
//-------------------------------------------------------------------------------------
  /**
     * Asks the pharmacist for a leave and prints the details of the leave.
     */
    public void askForLeave() {
        System.out.println("choose the type of leave:");
        System.out.println("1-sick leave");
        System.out.println("2-unpaid leave");
        System.out.println("3-casual leave");
        System.out.println("4-maternity leave");
        System.out.println("5-paternity leave");
        System.out.println("6-Annual leave");
        int x = c.nextInt();
        switch (x) {
            case 1:
                String n = "sick leave";
                printLeave(n);
                break;
            case 2:
                n = "unpaid leave";
                printLeave(n);
                break;
            case 3:
                n = "casual leave";
                printLeave(n);
                break;
            case 4:
                n = "maternity leave";
                printLeave(n);
                break;
            case 5:
                n = "paternity leave";
                printLeave(n);
                break;
            case 6:
                n = "Annual leave";
                printLeave(n);
                break;
            default:
                System.out.println("you have not choose any leave");
                break;
        }
    }
  /**
     * Prints the details of the given type of leave, including the starting and ending dates.
     *
     * @param nameOfLeave the name of the type of leave
     */
    private void printLeave(String nameOfLeave) {
        boolean date = true;
        DateTimeFormatter format;
        LocalDate startingdate = null;
        LocalDate lastdate = null;
        String inputDate;

        while (date) {
            System.out.println("please write the starting date in format (yyyy-MM-dd)");
            inputDate = c.next();

            format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            startingdate = LocalDate.parse(inputDate, format);
            if (startingdate.isBefore(LocalDate.now())) {
                System.out.println("Try AGAIN!! you can't choose a date in the past");

            } else {
                date = false;
            }
        }

        //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        boolean date2=true;
        while (date2) {

            System.out.println("please write the last date in format (yyyy-MM-dd)");
            inputDate = c.next();

            format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            lastdate = LocalDate.parse(inputDate, format);
            if (lastdate.isBefore(startingdate)) {
                System.out.println(" ");
                System.out.println("TRY AGAIN!! last date can't be before first date");

            } else {
                date2 = false;
            }
        }

        int days = (int) ChronoUnit.DAYS.between(startingdate, lastdate);

        System.out.println("||*****************||");
        System.out.println("||     Your " + nameOfLeave + " have been approved    ||");
        System.out.println("||                                               ||");
        System.out.println("||Start from " + startingdate + " to " + lastdate + "            " + "||");
        System.out.println("||                                               ||");
        System.out.println("||And last for (" + days + " day)                          ||");
        System.out.println("||                                              ||");
        System.out.println("||*****************||");

    }
      /**
     * takes the customer informations such as name , phone number and email.
     */
    public Customer newCustomer() {
     
        System.out.print("Enter the customer name: ");
        String name = c.nextLine();
        //newCustomer1.setName(name);
        System.out.print("Enter the customer phone number: ");
        String phone = c.nextLine();
       // System.out.println("");
       // newCustomer1.setPhoneNumber(phone);
        System.out.print("Enter the customer email:");
        String email = c.nextLine();
       newCustomer1= new Customer(name,phone,email) ;
   return newCustomer1; }
    
     /**
     * it is about the products that out of supply .
     */
    public void outOfSupply(){
        System.out.println("Write the name of the product that was out of supply");
       String s=c.next();
        System.out.println("The company was informed to supply the pharmacy with quantities of"+' '+s);
    }
    
}
