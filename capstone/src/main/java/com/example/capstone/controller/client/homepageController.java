package com.example.capstone.controller.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.capstone.model.admin.Products;
import com.example.capstone.service.CategoryService;
import com.example.capstone.service.productService;
import com.example.capstone.users.users;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class homepageController {

    private final productService productService;
    private final CategoryService categoryService;

    @Autowired
    public homepageController(productService productService, CategoryService categoryService){
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @ModelAttribute
    public void addCategoriesToModel(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
    }

    @GetMapping("/home")
    public String viewHomepage(Model model, HttpSession session) {
        // Get the list of top products
        List<Products> topProducts = productService.getLimitedProducts();
        model.addAttribute("products", topProducts);

        // Check if a user is logged in via the session
        users loggedInUser = (users) session.getAttribute("user");
        
        if (loggedInUser != null) {
            // If a user is logged in, add a personalized message
            model.addAttribute("message", "Welcome, " + loggedInUser.getName());
        } else {
            // If no user is logged in, prompt to log in
            model.addAttribute("message", "Please log in to access your account.");
        }

        return "Client/home";
    }

    @GetMapping("/terms")
    public String showTermsPage(Model model) {
        
        return "Client/fragmentterms";
    }
}
