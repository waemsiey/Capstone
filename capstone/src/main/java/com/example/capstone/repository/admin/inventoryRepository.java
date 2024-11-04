package com.example.capstone.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstone.model.admin.ProductInventory;

@Repository
public interface inventoryRepository extends JpaRepository <ProductInventory, Long> {

}
