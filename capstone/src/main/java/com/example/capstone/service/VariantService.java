package com.example.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.capstone.model.admin.ProductVariant;
import com.example.capstone.repository.admin.ProductVariantRepository;

@Service
public class VariantService {

    @Autowired 
    private ProductVariantRepository productVariantRepository;

    public ProductVariant saveVariant(ProductVariant variant){
        return productVariantRepository.save(variant);
    }

}
