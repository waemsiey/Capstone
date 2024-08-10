package com.example.capstone.users;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "users" )
public class users {
    @Id
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
        )
    @GeneratedValue(
       generator= "uuid" //the universal unique identifier for the pk
    )
    private UUID id;
    private String name, email, password;

    public users() {
    }
  
    public users(UUID id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
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

    @Override
    public String toString() {
        return "users [id=" + id +
               ", name=" + name + 
               ", email=" + email + 
               ", password=" + password + "]";
    }

   
   
}
