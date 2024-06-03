/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmcy;

/**
 *
 * @author Layan
 */

import java.util.List;

/**
 * This class represents an order of products in a pharmacy.
 */
public class Order {
    private int orderId; // The order ID
    private Customer customer; // Object representing the customer
    
    private List<Product> products; // List of products in the order

    /**
     * Constructs a new order.
     *
     * @param orderId   The order ID
     * @param customer  The customer object
     * @param products  The list of products in the order
     */
   
    public Order(int orderId, Customer customer, List<Product> products) {
        this.orderId = orderId;
        this.customer = customer;
       
        this.products = products;
    }

    public Order()
    {
        
    }

    /**
     * Returns the order ID.
     *
     * @return The order ID
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Returns the customer associated with the order.
     *
     * @return The customer object
     */
    public Customer getCustomer() {
        return customer;
    }

  

    /**
     * Returns the list of products in the order.
     *
     * @return The list of products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Adds a new product to the order.
     *
     * @param product The new product to be added
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     *  Display the order details 
     
     */
      public void printOrder()
      {
        System.out.println("------- Order Details ---------");
        System.out.println("Order ID: " + getOrderId());
        System.out.println("Customer Name: " + getCustomer().getName());
        System.out.println("Products Info: " + "------------");
        System.out.println("Product Name " + ":    Product Price");
        for (Product product : getProducts()) {
            System.out.println(product);
           
        }
        System.out.println("Total Price: $" + calculateTotalPrice());
        System.out.println("-----------------------------------------");
      }

    /**
     * Calculates the total price of the order based on the product prices.
     *
     * @return The total price of the order
     */
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    // Override toString method
    @Override
    public String toString() {
        return String.valueOf(orderId);
    }
}
