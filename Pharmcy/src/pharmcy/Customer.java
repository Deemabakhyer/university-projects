/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmcy;

import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author Layan
 */
 
/**
 * Represents a customer with associated information such as name, phone number, ID, and email.
 */
public class Customer {

    private String name;
    private String phoneNumber;
    private int Id;
    private String email;

    /**
     * Default constructor for the Customer class.
     */
    public Customer() {
    }

    /**
     * Constructs a Customer object with the specified name, phone number, ID, and email.
     *
     * @param name        the name of the customer.
     * @param phoneNumber the phone number of the customer.
     * @param Id          the ID of the customer.
     * @param email       the email of the customer.
     */
    public Customer(String name, String phoneNumber, int Id, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.Id = Id;
        this.email = email;
    }

    /**
     * Constructs a Customer object with the specified name, phone number, and email.
     *
     * @param name        the name of the customer.
     * @param phoneNumber the phone number of the customer.
     * @param email       the email of the customer.
     */
    public Customer(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Retrieves the name of the customer.
     *
     * @return the name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the ID of the customer.
     *
     * @return the ID of the customer.
     */
    public int getId() {
        return Id;
    }

    /**
     * Retrieves the email of the customer.
     *
     * @return the email of the customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the phone number of the customer.
     *
     * @return the phone number of the customer.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name the name to set for the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the ID of the customer.
     *
     * @param Id the ID to set for the customer.
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * Sets the email of the customer.
     *
     * @param email the email to set for the customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phoneNumber the phone number to set for the customer.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Provides the ability for customers to provide feedback on the services offered.
     */

    public void aboutServices() {
        Scanner scan = new Scanner(System.in);
        int rate;
         String complaints;
        System.out.println(" rate our services out of 5 , Please  ");

        rate = scan.nextInt();
        if (rate ==5 )
            System.out.println("Thank you for rating , we are happy that you like our services");
        if (rate <= 4) {
            System.out.println("Can you write your complaints ?");
            complaints = scan.nextLine(); 
            complaints = scan.nextLine(); 
            System.out.println("we are sorry to hear that, our pharmacits will contact you soon ! ");
        }
    }


    /**
     * Returns a string representation of the Customer object.
     *
     * @return a string representing the details of the Customer object, including its name, phone number, ID, and email.
     */
    @Override
    public String toString() {
        return "Customer{" + "name=" + name + ", phoneNumber=" + phoneNumber + ", Id=" + Id + ", email=" + email + '}';
    }

/**
 * Indicates whether some other object is "equal to" this one.
 * The method compares the current object with the specified object.
 *
 * @param obj the reference object with which to compare.
 * @return true if this object is the same as the obj argument; false otherwise.
 */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        return Objects.equals(this.email, other.email);
    }

}
