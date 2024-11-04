package com.example.capstone.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.capstone.dto.ProductRequest;
import com.example.capstone.model.admin.Category;
import com.example.capstone.model.admin.ProductInventory;
import com.example.capstone.model.admin.Products;
import com.example.capstone.service.CategoryService;
import com.example.capstone.service.VariantService;
import com.example.capstone.service.inventoryService;
import com.example.capstone.service.productService;

@Controller
@RequestMapping("/product")
public class productController {
    private static final Logger logger = LoggerFactory.getLogger(productController.class);
    
    private final productService productService;
    private final CategoryService categoryService; 
    private final VariantService variantService;
    private final inventoryService inventoryService;

    @Autowired
    public productController(productService productService, 
                             CategoryService categoryService,
                             VariantService variantService,
                             inventoryService inventoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.variantService = variantService;
        this.inventoryService = inventoryService;
    }

    @GetMapping("")
    public String listProducts(Model model) {
        logger.info("Listing all products");
        List<Products> products = productService.getAllProducts();
        ProductRequest productRequest = new ProductRequest();
        model.addAttribute("productRequest", productRequest);
        model.addAttribute("productRequest", new ProductRequest());

        model.addAttribute("product", products);
        model.addAttribute("products", new Products());
        return "Admin/adminpage";
    }


    @GetMapping("/new")
    public String showNewProductForm(Model model) {
        logger.info("Showing form to create a new product");
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProducts(new Products());
        productRequest.setVariants(new ArrayList<>());
        productRequest.setInventories(new ArrayList<>());

        
        model.addAttribute("productRequest", new ProductRequest());
        model.addAttribute("productRequest", productRequest);
     
        model.addAttribute("categories", categoryService.getCategories()); // Load categories
        logger.debug("Model attributes: {}", model.asMap());
        return "Admin/adminpage"; 
    }

    @PostMapping("/saveProduct")
    @ResponseBody
    public String saveProduct(@ModelAttribute("productRequest") ProductRequest  productRequest,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        logger.info("Saving new product: {}", productRequest.getProducts());
        Map<String, String> response = new HashMap<>();

        try {
            String categoryId = productRequest.getProducts().getCategory().getCategoryId();
            Optional<Category> optionalCategory = categoryService.getProductById(categoryId);

            if (optionalCategory.isPresent()) {
                productRequest.getProducts().setCategory(optionalCategory.get());

                // Save the product along with variants and inventory
                productService.addNewProduct(productRequest);

                redirectAttributes.addFlashAttribute("success", "Product saved successfully.");
                return "redirect:/admin"; 
            } else {
                logger.warn("Category not found with ID: {}", categoryId);
                redirectAttributes.addFlashAttribute("error", "Category not found.");
                return "redirect:/admin/new"; // Redirect back to the form
            }
        } catch (IOException e) {
            logger.error("Error occurred while saving product: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error occurred while saving product.");
            return "redirect:/admin/new"; // Redirect back to the form
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") String id, Model model) {
        Optional<Products> product = productService.getProductById(id);
        model.addAttribute("product", product.orElse(new Products()));
        return "Admin/product/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") String id,
            @ModelAttribute Products product,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("inventory") List<ProductInventory> inventoryList) throws IOException {
        productService.updateProduct(id, product, imageFile, inventoryList);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    public inventoryService getInventoryService() {
        return inventoryService;
    }
}