package com.example.capstone.repository.client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.capstone.model.client.ShippingMethod;

public interface ShippingMethodRepository extends JpaRepository <ShippingMethod, Long> {
    Optional<ShippingMethod> findByBillingDetailId(Long billingDetailId);
}
