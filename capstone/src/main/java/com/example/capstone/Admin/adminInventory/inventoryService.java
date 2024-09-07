package com.example.capstone.Admin.adminInventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class inventoryService {
    private final inventoryRepository inventoryRepository;

    
    @Autowired
    public inventoryService(inventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<productInventory> getAllProducts(){
        return inventoryRepository.findAll();
    }

    public List<productInventory> getInventoryByProductId(String productId) {
        return inventoryRepository.findAll().stream()
            .filter(inventory -> inventory.getProductID().getProdID().equals(productId))
            .toList();
    }
}

