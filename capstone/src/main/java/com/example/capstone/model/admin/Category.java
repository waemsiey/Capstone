package com.example.capstone.model.admin;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    private String categoryId;
    private String categoryCode;
    private String categoryName;
    
   @OneToMany(mappedBy = "category")
   private List<Products> products = new ArrayList<>();
    public Category() {

    }   
    
    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }


    public Category(String categoryId, String categoryCode, String categoryName) {
        this.categoryId = categoryId;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
 
    @Override
    public String toString() {
        return categoryName;
    }
}
