package com.example.capstone.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.capstone.model.admin.ProductInventory;
import com.example.capstone.model.admin.ProductVariant;
import com.example.capstone.model.admin.Products;

public class ProductRequest {
    private Products products = new Products();; // Main product details
    private MultipartFile imageFile; // Image file
    private List<ProductVariant> variants; // List of product variants
    private List<ProductInventory> inventories; // List of inventories

    // Getters and Setters
    public Products getProducts() {
        return products;
    } 

    public void setProducts(Products products) {
        this.products = products;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }

    public List<ProductInventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<ProductInventory> inventories) {
        this.inventories = inventories;
    }
}