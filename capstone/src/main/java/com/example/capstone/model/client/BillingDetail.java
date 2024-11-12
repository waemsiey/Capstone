package com.example.capstone.model.client;

import com.example.capstone.users.users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "billing_details")
public class BillingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName, lastName, streetAddress, address, country, municipality, barangay, postalcode, email, phoneNumber;

    private boolean termsAndCondition, returnPolicies;

 
    private String region;
    private String province;

    @OneToOne
    @JoinColumn(name = "user_id")
    private users user;

    
    @OneToOne(mappedBy = "billingDetail", cascade = CascadeType.ALL)
    private ShippingMethod shippingMethod;
    
    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }
    
    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    // Getters and Setters for all fields

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isTermsAndCondition() {
        return termsAndCondition;
    }

    public void setTermsAndCondition(boolean termsAndCondition) {
        this.termsAndCondition = termsAndCondition;
    }

    public boolean isReturnPolicies() {
        return returnPolicies;
    }

    public void setReturnPolicies(boolean returnPolicies) {
        this.returnPolicies = returnPolicies;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "BillingDetail [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", streetAddress="
                + streetAddress + ", address=" + address + ", country=" + country + ", municipality=" + municipality
                + ", barangay=" + barangay + ", postalcode=" + postalcode + ", region=" + region + "]";
    }

 
}
