/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sallatk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class Customer implements java.io.Serializable {

    // each customer will be assigned an id that is generated in auto increments
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerID;
    @Column(name = "password")
    private String password;
    @Column(name = "customerName")
    private String customerName;
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;
    @Column(name = "address")
    private String address;

    public Customer() {
    }

    public Customer(String password, String customerName, String email, String phoneNumber, String address) {
        this.password = password;
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Standard getters and setters
    public int getCustomerID() {
        return this.customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Static authentication method
    public static Customer authenticate(String phoneNumber, String password) {
        // Log the authentication attempt
        System.out.println("[Customer] Attempting authentication for phone: " + phoneNumber);

        try {
            // Open a new session with the database using Hibernate
            var session = HibernateUtil.getSessionFactory().openSession();

            // Create an HQL query to fetch the customer based on phone number and password
            var query = session.createQuery(
                    "FROM Customer WHERE phoneNumber = :phone AND password = :password",
                    Customer.class
            );

            // setParameter("phone", phoneNumber) → Replaces :phone in the query with phoneNumber (user input).
            //setParameter("pass", password) → Replaces :pass with password.
            query.setParameter("phone", phoneNumber);
            query.setParameter("password", password);

            // Execute query and get a unique result
            var result = query.uniqueResult();

            if (result != null) {
                // If a matching customer is found, authentication is successful
                System.out.println("[Customer] Authentication successful for phone: " + phoneNumber);
                return result;
            } else {
                // If no match is found, authentication fails
                System.out.println("[Customer] Authentication failed for phone: " + phoneNumber);
                return null;
            }

//org.hibernate.HibernateException
//org.hibernate.QueryException
//org.hibernate.JDBCConnectionException
        } catch (Exception e) {
            // Handle any exceptions that occur during the authentication process
            System.err.println("[Customer] Database error during authentication: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
