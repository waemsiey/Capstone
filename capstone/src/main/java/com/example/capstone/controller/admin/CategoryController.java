package com.example.capstone.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.capstone.model.admin.Category;
import com.example.capstone.service.CategoryService;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String showCategoriesForm(Model model) {
        model.addAttribute("category", new Category());
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "Admin/product/categories"; 
    }

    @PostMapping("/categories")
    public String saveCategory(Category category) {
        String categoryName = category.getCategoryName();

        categoryService.addNewCategory(categoryName);

        return "redirect:/categories"; }
    }

