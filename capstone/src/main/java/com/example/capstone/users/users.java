package com.example.capstone.users;

import java.util.List;

import com.example.capstone.model.client.BillingDetail;
import com.example.capstone.model.client.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "users" )
public class users {
    @Id
    private String id;
    private String name, email, password, role;

    @OneToOne
    @JoinColumn(name = "billing_details_id")
    private BillingDetail billigDetailes;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public users() {
    }
  //with pass constructor
    public users(String id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    //w/o pass constructor 
    public users(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.password = role;
    }
  //getter setter

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return "users [id=" + id +
               ", name=" + name + 
               ", email=" + email + 
               ", password=" + password + 
               ", role ="+role+ "]";
    }

   
   
}
