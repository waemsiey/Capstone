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
    public String viewHompage(Model model, HttpSession session){
        List<Products> topProducts = productService.getLimitedProducts();
        model.addAttribute("products", topProducts);
        return "Client/home";
        // users loggedInUser = (users)session.getAttribute("loggedInUser");
        // if(loggedInUser  != null){
        //     model.addAttribute("message", "Welcome, " + loggedInUser .getName());
        //     return "Client/home";
        // }else {
        //     return "redirect:/login";
    // }
    }
    
}