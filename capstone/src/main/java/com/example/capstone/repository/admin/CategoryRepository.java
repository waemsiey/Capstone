package com.example.capstone.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.capstone.model.admin.Category;

public interface CategoryRepository extends JpaRepository <Category, String>{
    Category findByCategoryName(Category category);
}
