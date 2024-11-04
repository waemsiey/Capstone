package com.example.capstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.capstone.model.admin.Products;
import com.example.capstone.model.admin.ProductInventory;
import com.example.capstone.repository.admin.inventoryRepository;

@Service
public class inventoryService {
    private final inventoryRepository inventoryRepository;

    
    @Autowired
    public inventoryService(inventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<ProductInventory> findAll(){
        return inventoryRepository.findAll();
    }

    public List<ProductInventory> getInventoryByProduct(Products product) {
        return inventoryRepository.findAll().stream()
            .filter(inventory -> inventory.getProducts().getSku().equals(product.getSku()))
            .toList();
    }

    public void saveInventory(ProductInventory inventory) {
        inventoryRepository.save(inventory);
    }
}

