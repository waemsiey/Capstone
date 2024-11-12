package com.example.capstone.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.capstone.model.client.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
