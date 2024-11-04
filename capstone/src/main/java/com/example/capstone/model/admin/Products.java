package com.example.capstone.model.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Products {
    @Id 
    private String sku; // primary key
    private String prodName, prodDescription, prodImgURL, frontprodImgURL, backprodImgURL, lSleeveProdURL,
            rSleeveProdURL;
    
    @ManyToOne // Many products can belong to one category
    @JoinColumn(name = "category_id") // Foreign key column
    private Category category;

    //for variants ref table
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<ProductVariant> variants = new ArrayList<>();

    //for inventory ref  tables;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<ProductInventory> inventoryList = new ArrayList<>();

    //getters for variants
    public List<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }
    public void addVariant(ProductVariant variant) {
        variants.add(variant);
        variant.setProduct(this);
    }

     //getters for inventory
    public List<ProductInventory> getInventories(){
        return inventoryList;
    }
    public void setInventories(List<ProductInventory> inventoryList) {
        this.inventoryList = inventoryList;
    }
    public void addInventory(ProductInventory inventory){
        inventoryList.add(inventory);
        inventory.setProducts(this);
    }

    public Products() {

    }

    public Products(String sku, String prodName,
            Category category, String prodDescription,
            String prodImgURL,
            String frontprodImgURL, String backprodImgURL,
            String lSleeveProdURL, String rSleeveProdURL) {

        this.sku = sku;
        this.prodName = prodName;
        this.category = category;
        this.prodDescription = prodDescription;
        this.prodImgURL = prodImgURL;
        this.frontprodImgURL = frontprodImgURL;
        this.backprodImgURL = backprodImgURL;
        this.lSleeveProdURL = lSleeveProdURL;
        this.rSleeveProdURL = rSleeveProdURL;
    }

    public void setInventoriesFromSet(Set<ProductInventory> inventoryList) {
        this.inventoryList.clear();
        this.inventoryList.addAll(inventoryList);
    }

    public List<ProductInventory> getInventoryList() {
        return inventoryList;
    }

    // getters and setters
    public String getSku() {
        return sku;
    }

    public void setSku(String Sku) {
        this.sku = Sku;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProdDescription() {
        return prodDescription;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }
    // Getters and setters for product images should contain 5 images including The
    // front back left and right image and the thumbnail images or the images that
    // is visible as choices of products:

    public String getImgURL() {
        return prodImgURL;
    }

    public void setImgURL(String prodImgURL) {
        this.prodImgURL = prodImgURL;
    }

    public String getFrontprodImgURL() {
        return frontprodImgURL;
    }

    public void setFrontprodImgURL(String frontprodImgURL) {
        this.frontprodImgURL = frontprodImgURL;
    }

    public String getBackprodImgURL() {
        return backprodImgURL;
    }

    public void setBackprodImgURL(String backprodImgURL) {
        this.backprodImgURL = backprodImgURL;
    }

    public String getlSleeveProdURL() {
        return lSleeveProdURL;
    }

    public void setlSleeveProdURL(String lSleeveProdURL) {
        this.lSleeveProdURL = lSleeveProdURL;
    }

    public String getrSleeveProdURL() {
        return rSleeveProdURL;
    }

    public void setrSleeveProdURL(String rSleeveProdURL) {
        this.rSleeveProdURL = rSleeveProdURL;
    }

    @Override
    public String toString() {
        return "[products] [sku=" + sku +
                ", prodName=" + prodName +
                ", category=" + category +
                ", prodDescription=" + prodDescription +
                ", prodImgURL=" + prodImgURL +
                ", frontImgURL=" + frontprodImgURL +
                ", backImgURL=" + backprodImgURL +
                ", leftSleeveImgURL = " + lSleeveProdURL +
                ", rightSleeveImgURL = " + rSleeveProdURL + "]";
    }


}