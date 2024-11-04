package com.example.capstone.controller.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.capstone.model.admin.Products;
import com.example.capstone.service.CategoryService;
import com.example.capstone.service.productService;


@Controller
@RequestMapping("/")
public class ClientproductController {

    private final productService productService;
    private final CategoryService categoryService;

    @Autowired
    public ClientproductController(productService productService, CategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
    }
    @GetMapping("/products")
    public String viewProduct(Model model){
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("selectedCategory", null); 
        return "Client/products";
    }
    
    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable String id, Model model) {
        System.out.println("Fetching product with ID: " + id);
        Optional<Products> productOpt = productService.getProductById(id);
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
            System.out.println("Product found: " + productOpt.get());
        } else {
            model.addAttribute("errorMessage", "Product not found");
            System.out.println("Product not found for ID: " + id);
        }
        return "Client/productFullDescription";
    }
    @GetMapping ("/products/category/{category}")
    public String getProductByCategory(@PathVariable String category, Model model) {
        List<Products> products = productService.getProductByCategory(category);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("selectedCategory", category);
        return "Client/products";
    }

   
}
    