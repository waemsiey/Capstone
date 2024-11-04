package com.example.capstone.controller.admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.capstone.model.admin.ProductVariant;
import com.example.capstone.model.admin.Products;
import com.example.capstone.model.admin.ProductInventory;
import com.example.capstone.repository.admin.ProductVariantRepository;
import com.example.capstone.repository.admin.inventoryRepository;
import com.example.capstone.repository.admin.productRepository;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private inventoryRepository inventoryRepository;

    @Autowired
    private productRepository productRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @PostMapping
    public ResponseEntity<?> addInventory(@RequestBody ProductInventory inventory) {
        // Validate and save the associated product and variant
        Products product = inventory.getProducts();
        ProductVariant variant = inventory.getProductVariant();

        // Save the product if it doesn't exist
        if (!productRepository.existsById(product.getSku())) {
            productRepository.save(product);
        }

        // Save the variant if it doesn't exist
        if (!productVariantRepository.existsById(variant.getVariantId())) {
            productVariantRepository.save(variant);
        }

        // Now save the inventory
        inventoryRepository.save(inventory);
        return ResponseEntity.ok("Inventory added successfully");
    }
}
