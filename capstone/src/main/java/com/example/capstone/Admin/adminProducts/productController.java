package com.example.capstone.Admin.adminProducts;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/products")
public class productController {
    private static final Logger logger = LoggerFactory.getLogger(productController.class);
    private final productService productService;

    @Autowired
    public productController(productService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {
        logger.info("Listing all products");
        model.addAttribute("products", productService.getAllProducts());
        return "Admin/product/view";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        logger.info("Showing form to create a new product");
        products newProduct = new products();
        logger.info("New Product Object: {}", newProduct);
        model.addAttribute("product", new products());
        return "Admin/adminpage";
    }

   @PostMapping
    public String saveProduct(@ModelAttribute products product, @RequestParam("imageFile") MultipartFile imageFile) {
        logger.info("Saving new product: {}", product);
        try {
            productService.addNewProduct(product, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Admin/adminpage";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        Optional<products> product = productService.getProductById(id);
        model.addAttribute("product", product.orElse(new products()));
        return "Admin/product/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") String id, @ModelAttribute products product, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        productService.updateProduct(id, product, imageFile);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
