package com.example.capstone.repository.client;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.capstone.model.client.BillingDetail;
import com.example.capstone.model.client.Order;
import com.example.capstone.model.client.PaymentDetails;
import com.example.capstone.model.client.ShippingMethod;

public interface OrderRepository extends JpaRepository<Order, Long>{
  @Query("SELECT SUM(o.totalPrice) FROM Order o")
    BigDecimal getTotalRevenue();

    
    @Query("SELECT DISTINCT MONTH(o.orderDate) FROM Order o ORDER BY MONTH(o.orderDate)")
    List<String> getSalesMonths();
}
