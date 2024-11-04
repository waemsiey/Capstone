package com.example.capstone.model.client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_detail")
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String paymentMethod , paymentDetail;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getPaymentDetail() {
        return paymentDetail;
    }
    public void setPaymentDetail(String paymentDetail) {
        this.paymentDetail = paymentDetail;
    }
    @Override
    public String toString() {
        return "PaymentDetails [id=" + id + ", paymentMethod=" + paymentMethod + ", paymentDetail=" + paymentDetail + "]";
    }
}
