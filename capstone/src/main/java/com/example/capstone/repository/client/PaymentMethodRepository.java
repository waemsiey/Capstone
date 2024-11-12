package com.example.capstone.repository.client;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.capstone.model.client.PaymentDetails;
public interface PaymentMethodRepository extends  JpaRepository <PaymentDetails, Long>{
    Optional<PaymentDetails> findByShippingMethodId(Long shippingMethodId);
}
