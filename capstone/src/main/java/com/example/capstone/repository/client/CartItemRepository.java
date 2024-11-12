package com.example.capstone.repository.client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.capstone.model.client.CartItem;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProduct_SkuAndSizeAndColorAndPrice(String sku, String size, String color, Double price);
}