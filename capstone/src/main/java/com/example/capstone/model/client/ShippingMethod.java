package com.example.capstone.model.client;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "shipping_methods")
public class ShippingMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String method;
    private LocalDate estimatedDeliveryDate;
    private BigDecimal cost;

    @Column(name = "po_number")
    private String poNumber;

    private String instructions;

    @OneToOne
    @JoinColumn(name = "billing_detail_id")
    private BillingDetail billingDetail;

    // Getters and Setters for all fields

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public BillingDetail getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(BillingDetail billingDetail) {
        this.billingDetail = billingDetail;
    }

    @Override
    public String toString() {
        return "ShippingMethod [id=" + id + ", method=" + method + ", estimatedDeliveryDate=" + estimatedDeliveryDate
                + ", cost=" + cost + ", poNumber=" + poNumber + ", instructions=" + instructions + "]";
    }
}
