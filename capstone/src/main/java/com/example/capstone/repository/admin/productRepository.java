package com.example.capstone.repository.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.capstone.model.admin.Products;

@Repository

public interface productRepository extends JpaRepository<Products, String> {
    
    // Optional<products> findByProdName(String prodName);
    List<Products> findByCategory_categoryName(String category);
    List<Products> findByCategory_categoryId(String categoryId);
       @Query(value = "SELECT p FROM Products p ORDER BY p.sku ASC")
    Page<Products> findTopNproducts(Pageable pageable);

    Optional<Products> findBySku(String sku);


}

