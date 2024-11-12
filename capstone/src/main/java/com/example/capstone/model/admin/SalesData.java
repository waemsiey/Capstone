package com.example.capstone.model.admin;

import java.math.BigDecimal;

public class SalesData {
    private BigDecimal totalRevenue;
    private long totalSales;

    // Getters and setters
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(long totalSales) {
        this.totalSales = totalSales;
    }
}
