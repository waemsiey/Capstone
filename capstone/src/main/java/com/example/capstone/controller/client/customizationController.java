package com.example.capstone.controller.client;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.capstone.model.admin.Category;
import com.example.capstone.model.admin.Products;
import com.example.capstone.service.CategoryService;
import com.example.capstone.service.productService;
import com.example.capstone.users.users;


@Controller
@RequestMapping("/")
public class customizationController {

    @Autowired 
    private CategoryService categoryService;

    @Autowired
    private productService productService;

    @GetMapping("/customize")
    public String viewCustomize(Model model){
        model.addAttribute("user", new users());
        model.addAttribute("product", new Products());
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getCategories());

        return "Client/customize";
    }
    @PostMapping("/customize")
    public String customize(@RequestParam("category") String category, Model model) {
        List<Products> products = productService.getProductByCategory(category);
        model.addAttribute("products", products);
        return "Client/customize";
}
    @GetMapping("/design/{sku}")
    public String viewCustomize(@PathVariable String sku, Model model){
        Products product = productService.getProductById(sku).orElse(null);
        model.addAttribute("product", product);
      
        return "Client/customize";
    }
}