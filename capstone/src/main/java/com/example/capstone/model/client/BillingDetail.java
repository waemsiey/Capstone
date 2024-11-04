package com.example.capstone.model.client;

import com.example.capstone.users.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="billing_details")
public class BillingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName, lastName, streetAddress, address, country, municipality, barangay, postalcode;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private users user;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getMunicipality() {
        return municipality;
    }
    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
    public String getBarangay() {
        return barangay;
    }
    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }
    public String getPostalcode() {
        return postalcode;
    }
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }
    @Override
    public String toString() {
        return "BillingDetails [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", streetAddress="
                + streetAddress + ", address=" + address + ", country=" + country + ", municipality=" + municipality
                + ", barangay=" + barangay + ", postalcode=" + postalcode + "]";
    }
}
