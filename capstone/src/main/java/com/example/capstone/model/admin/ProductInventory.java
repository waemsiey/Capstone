package com.example.capstone.model.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class ProductInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_sku") // Ensure this matches the primary key in products
    private Products product;

    @ManyToOne
    @JoinColumn(name = "variant_id") // Foreign key to the ProductVariant
    private ProductVariant productVariant; // Reference to the specific variant

    private Integer quantity;

    public ProductInventory() {
    }

    public ProductInventory(Products product, ProductVariant productVariant, Integer quantity) {
        this.product = product;
        this.productVariant = productVariant;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Products getProducts() {
        return product;
    }

    public void setProducts(Products products) {
        this.product = products;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Price derived from ProductVariant
    public Double getPrice() {
        return productVariant.getPrice();
    }

    @Override
    public String toString() {
        return "[ProductInventory] [id=" + id +
               ", product=" + product +
               ", variant=" + productVariant +
               ", quantity=" + quantity + "]";
    }
}