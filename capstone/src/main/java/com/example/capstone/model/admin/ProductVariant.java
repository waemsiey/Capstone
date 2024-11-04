package com.example.capstone.model.admin;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_variants")
public class ProductVariant {

    @Id
    private String variantId;
    private String size;
    private String color;
    private double price;


    @ManyToOne
    @JoinColumn(name = "sku")
    private Products product;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<ProductInventory> productInventoryList;

    public Products getProducts() {
        return product;
    }

    public void setProducts(Products product) {
        this.product = product;
    }

    public List<ProductInventory> getProductInventory() {
        return productInventoryList;
    }

    public void setProductInventory(List<ProductInventory> productInventoryList) {
        this.productInventoryList = productInventoryList;
    }

    public ProductVariant() {

    }

    public ProductVariant(String variantId, String size, String color, double price, Products product) {
        this.variantId = variantId;
        this.size = size;
        this.color = color;
        this.price = price;
        this.product = product;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

}
