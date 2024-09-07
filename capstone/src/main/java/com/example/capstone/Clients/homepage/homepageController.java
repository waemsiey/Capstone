package com.example.capstone.Clients.homepage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.capstone.Admin.adminProducts.productService;
import com.example.capstone.users.users;

@Controller
@RequestMapping("/")
public class homepageController {
private final productService productService;

    @Autowired
    public homepageController(productService productService){
        this.productService = productService;
    }

    @GetMapping("/home")
    public String viewHompage(Model model){
        model.addAttribute("user", new users());
        model.addAttribute("products", productService.getAllProducts());
        return "Client/home";

        
    }
}
