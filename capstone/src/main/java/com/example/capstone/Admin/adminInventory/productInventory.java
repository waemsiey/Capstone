package com.example.capstone.Admin.adminInventory;

import com.example.capstone.Admin.adminProducts.products;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "inventory")
public class productInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private Integer quantity;
    private String color;

    @ManyToOne
    private products productID;

    public productInventory(){
    }

    public productInventory(Double price, Integer quantity, String color, products productID) {
        this.price = price;
        this.quantity = quantity;
        this.color = color;
        this.productID = productID;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public products getProductID() {
        return productID;
    }

    public void setProductID(products productID) {
        this.productID = productID;
    }
    
    @Override
    public String toString() {
        return "[ProductInventory] [id=" + id +
               ", price=" + price +
               ", quantity=" + quantity +
               ", color=" + color +
               ", productID=" + productID + "]";
    }
}
