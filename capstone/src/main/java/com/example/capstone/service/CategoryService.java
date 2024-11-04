package com.example.capstone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.capstone.repository.admin.CategoryRepository;
import com.example.capstone.IdGeneratorService;
import com.example.capstone.model.admin.Category;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepo;
    private final IdGeneratorService idGeneratorService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepo, IdGeneratorService idGeneratorService) {
        this.categoryRepo = categoryRepo;
        this.idGeneratorService = idGeneratorService;
    }

    public List<Category> getCategories() {
        return categoryRepo.findAll();
    }

    public Optional<Category> getProductById(String categoryId) {
        return categoryRepo.findById(categoryId);
    }

    public Category addNewCategory(String categoryName) {
        String categoryId = idGeneratorService.generateCategoryId();
        String categoryCode = generateCategoryCode(categoryName);
        Category newCategory = new Category(categoryId, categoryCode, categoryName);
        return categoryRepo.save(newCategory);
    }

    private String generateCategoryCode(String categoryName) {
        // Logic to generate category code based on category type
        String code;
        switch (categoryName.toLowerCase()) {
            case "t-shirt":
                code = "TSH";  break;
            case "mug":
                code = "MUG";  break;
            case "totbag":  
                code= "TOT";   break;
            case "polo shirt":
                code = "PSH";  break;
            case "v-neck":
                code = "VNE";  break;
            case "long sleeves":
                code = "LSH";  break;
            default:
                code = "GEN";  break;
        }return code;
    }
}
