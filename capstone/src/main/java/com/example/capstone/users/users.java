package com.example.capstone.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "users" )
public class users {
    @Id
    private String id;
    private String name, email, password;

    public users() {
    }
  //with pass constructor
    public users(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    //w/o pass constructor 
    public users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "users [id=" + id +
               ", name=" + name + 
               ", email=" + email + 
               ", password=" + password + "]";
    }

   
   
}
