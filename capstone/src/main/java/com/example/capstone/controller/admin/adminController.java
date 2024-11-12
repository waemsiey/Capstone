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
import com.example.capstone.model.client.Order;
import com.example.capstone.service.CategoryService;
import com.example.capstone.service.ClientService;
import com.example.capstone.service.OrderService;
import com.example.capstone.service.SalesService;
import com.example.capstone.service.inventoryService;
import com.example.capstone.service.productService;
import com.example.capstone.users.users;

@Controller
@RequestMapping("/")  // Simplified mapping for admin-related endpoints
public class adminController {
    private final productService productService;
    private final CategoryService categoryService;
    private final inventoryService inventoryService;
    private final SalesService salesService;
    private final OrderService orderService;
    private final ClientService clientService;

    @Autowired
    public adminController(productService productService, CategoryService categoryService,
                           inventoryService inventoryService, SalesService salesService,
                           OrderService orderService, ClientService clientService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
        this.salesService = salesService;
        this.orderService = orderService;
        this.clientService = clientService;
    }

    // Get the admin dashboard and main page
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
    
        // Get client count
        model.addAttribute("clientCount", clientService.getClientCount()); 
        
        List<Order> orders = orderService.getAllOrders();  // Fetch orders
        model.addAttribute("orders", orders);
    
        return "Admin/adminpage"; 
    }

    // Get the inventory page
    @GetMapping("admin/inventory")
    public String getInventory(Model model) {
        // Fetch and pass inventory data for the inventory section
        List<ProductInventory> inventories = inventoryService.findAll();
        model.addAttribute("inventories", inventories);
        model.addAttribute("fragment", "inventoryFragment :: inventoryFragment");

        return "Admin/adminpage"; // Reuse the same view for displaying inventory
    }

    @GetMapping("/orders")
    public String getOrdersPage(Model model) {
        List<Order> orders = orderService.getAllOrders();  
        model.addAttribute("orders", orders);  
        return "adminpage";  
    }
    
}
  