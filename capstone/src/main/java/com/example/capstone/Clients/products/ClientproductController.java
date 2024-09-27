package com.example.capstone.Clients.products;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.capstone.Admin.adminProducts.productService;
import com.example.capstone.Admin.adminProducts.products;


@Controller
@RequestMapping("/")
public class ClientproductController {

    private final productService productService;

    @Autowired
    public ClientproductController(productService productService){
        this.productService = productService;
    }
    @GetMapping("/products")
    public String viewProduct(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "Client/products";
    }
    
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id, Model model) {
        Optional<products> productOpt = productService.getProductById(id);
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
        } else {
            // Handle the case where the product is not found
            model.addAttribute("errorMessage", "Product not found");
        }
        return "Client/productFullDescription";
    }
}

    