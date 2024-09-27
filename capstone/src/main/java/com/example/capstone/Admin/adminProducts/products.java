package com.example.capstone.Admin.adminProducts;

import java.util.Set;

import com.example.capstone.Admin.adminInventory.productInventory;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class products {
    @Id
    private String  prodID;
    private String prodName, category, prodDescription, prodImgURL,frontprodImgURL, backprodImgURL, lSleeveProdURL, rSleeveProdURL; 

    @OneToMany(mappedBy = "productID")
    private Set<productInventory> inventories;

public products(){
    
}
public products(String prodID, String prodName, 
                String category, String prodDescription,
                String prodImgURL,
                String frontprodImgURL, String backprodImgURL,
                String lSleeveProdURL, String rSleeveProdURL){

    this.prodID = prodID;
    this.prodName =prodName;
    this.category= category;
    this.prodDescription = prodDescription;
    this.prodImgURL = prodImgURL;
    this.frontprodImgURL = frontprodImgURL;
    this.backprodImgURL = backprodImgURL;
    this.lSleeveProdURL = lSleeveProdURL;
    this.rSleeveProdURL = rSleeveProdURL;
}

//getters and setters
public String getProdID() {
    return prodID;
}

public void setProdID(String prodID) {
    this.prodID = prodID;
}

public String getProdName() {
    return prodName;
}

public void setProdName(String prodName) {
    this.prodName = prodName;
}
public String getCategory() {
    return category;
}

public void setCategory(String category) {
    this.category = category;
}

public String getProdDescription() {
    return prodDescription;
}

public void setProdDescription(String prodDescription) {
    this.prodDescription = prodDescription;
}
//Getters and setters for product images should contain 5 images including The front back left and right image and the thumbnail images or the images that is visible as choices of products:

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
        return "[products] [prodID=" + prodID +
            ", prodName=" + prodName + 
            ", category=" + category + 
            ", prodDescription=" + prodDescription +
            ", prodImgURL=" + prodImgURL + 
            ", frontImgURL=" + frontprodImgURL+
            ", backImgURL=" + backprodImgURL +
            ", leftSleeveImgURL = "+ lSleeveProdURL+
            ", rightSleeveImgURL = " + rSleeveProdURL + "]";
    }
}