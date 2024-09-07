package com.example.capstone.Admin.adminProducts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface productRepository extends JpaRepository<products, String> {
    
    // Optional<products> findByProdName(String prodName);
    // List<products> findByCategory(String category);
    // Optional<products> findByProdNameAndCategory(String prodName, String category);
}

