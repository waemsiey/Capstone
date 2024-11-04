package com.example.capstone.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.capstone.dto.ProductRequest;
import com.example.capstone.model.admin.ProductInventory;
import com.example.capstone.model.admin.Products;
import com.example.capstone.service.CategoryService;
import com.example.capstone.service.inventoryService;
import com.example.capstone.service.productService;
import com.example.capstone.users.users;


@Controller
@RequestMapping("/")
public class adminController {
  private final productService productService;
  private final CategoryService categoryService;
  private final inventoryService inventoryService;

  
    @Autowired
    public adminController(productService productService, CategoryService categoryService, inventoryService inventoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
    }

    @GetMapping("/admin")
    public String getAdminHome(Model model) {
        List<Products> allProducts = productService.getAllProducts();
         ProductRequest productRequest = new ProductRequest();

        model.addAttribute("productRequest", productRequest);
        model.addAttribute("products",  allProducts);
        
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("inventories", inventoryService.findAll());

        

        model.addAttribute("product", new Products());
        model.addAttribute("user", new users());
        model.addAttribute("inventory", new ProductInventory());
        return "Admin/adminpage";
    }

    @GetMapping("/admin/inventory")
    public String getInventory(Model model) {
        List<ProductInventory> inventories = inventoryService.findAll();
        model.addAttribute("inventories", inventories);
        model.addAttribute("fragment", "inventoryFragment :: inventoryFragment");
        return "Admin/adminpage";
    }
    
}
