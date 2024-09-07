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
    private String prodName, category, prodDescription, prodImgURL; 

    @OneToMany(mappedBy = "productID")
    private Set<productInventory> inventories;

public products(){
    
}
public products(String prodID, String prodName, String category, String prodDescription, String prodImgURL){
    this.prodID = prodID;
    this.prodName =prodName;
    this.category= category;
    this.prodDescription = prodDescription;
    this.prodImgURL = prodImgURL;
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

public String getImgURL() {
    return prodImgURL;
}

public void setImgURL(String prodImgURL) {
    this.prodImgURL = prodImgURL;
}
    @Override
    public String toString() {
        return "[products] [prodID=" + prodID +
            ", prodName=" + prodName + 
            ", category=" + category + 
            ", prodDescription=" + prodDescription +
            ", prodImgURL=" + prodImgURL + "]";
    }
}