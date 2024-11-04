package com.example.capstone.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capstone.model.admin.ProductVariant;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, String>{

}
