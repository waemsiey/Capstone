package com.example.capstone.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.capstone.model.admin.ProductVariant;
import com.example.capstone.repository.admin.ProductVariantRepository;

@RestController
@RequestMapping("/variants")
public class VariantController {

    @Autowired
    private ProductVariantRepository variantRepository;

    @PostMapping
    public ResponseEntity<ProductVariant> createVariant(@RequestBody ProductVariant variant) {
        ProductVariant savedVariant = variantRepository.save(variant);
        return ResponseEntity.ok(savedVariant);
    }

    @GetMapping
    public List<ProductVariant> getAllVariants() {
        return variantRepository.findAll();
    }
}

