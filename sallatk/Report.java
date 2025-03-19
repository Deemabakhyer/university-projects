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

/**
 *
 * @author deema
 */
@Entity
@Table(name = "ISSUE")
public class Report implements java.io.Serializable {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportID;
    @Column(name = "reportDate")
    private String reportDate;
    @Column(name = "totalSales")
    private double totalSales;
    @Column(name = "totalOrders")
    private int totalOrders;
    @Column(name = "totalCustomers")
    private int totalCustomers;
    @Column(name = "reportType")
    private String reportType;

    public Report() {
    }

    public Report(String reportDate, double totalSales, int totalOrders, int totalCustomers, String reportType) {
        this.reportDate = reportDate;
        this.totalSales = totalSales;
        this.totalOrders = totalOrders;
        this.totalCustomers = totalCustomers;
        this.reportType = reportType;

    }

    public int getReportID() {
        return this.reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public String getReportDate() {
        return this.reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public double getTotalSales() {
        return this.totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public int getTotalOrders() {
        return this.totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalCustomers() {
        return this.totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public String getReportType() {
        return this.reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    
}
