package com.example.capstone.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.capstone.model.client.BillingDetail;

public interface BillingDetailRepository extends JpaRepository<BillingDetail, Long> {

}
