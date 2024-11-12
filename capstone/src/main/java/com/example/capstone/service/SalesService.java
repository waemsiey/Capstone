package com.example.capstone.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.capstone.model.admin.SalesData;
import com.example.capstone.repository.client.OrderRepository;

@Service
public class SalesService {

    @Autowired
    private OrderRepository orderRepository; // Assuming you have an OrderRepository

    // Method to get total sales data
    public SalesData getSalesData() {
        BigDecimal totalRevenue = orderRepository.getTotalRevenue();
        long totalSales = orderRepository.count(); // Get the number of sales/orders

        SalesData salesData = new SalesData();
        salesData.setTotalRevenue(totalRevenue);
        salesData.setTotalSales(totalSales);

        return salesData;
    }

    public List<String> getSalesMonths() {
        return orderRepository.getSalesMonths(); 
    }


    public BigDecimal getSalesAmount() {
        return orderRepository.getTotalRevenue(); 
    }
}
