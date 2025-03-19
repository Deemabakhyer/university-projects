package com.mycompany.sallatk;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CUSTOMER_ORDER") // Changed from ORDER since it's a reserved word
public class Order implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @ManyToOne
    @JoinColumn(name = "customerID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;

    @Column(name = "numberOfItems")
    private int numberOfItems;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "orderDate")
    private Date orderDate;

    @Column(name = "total")
    private double total;

    @Column(name = "status")
    private String status = "PENDING";

    public Order() {
    }

    public Order(Customer customer, int numberOfItems, double total) {
        this.customer = customer;
        this.numberOfItems = numberOfItems;
        this.orderDate = new Date();
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderID() {
        return this.orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getNumberOfItems() {
        return this.numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
