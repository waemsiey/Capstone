package com.example.capstone.Admin.adminProducts;

import java.io.IOException;
import java.util.Optional;

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

    private final productService productService;

    @Autowired
    public productController(productService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "Admin/product/view";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new products());
        return "Admin/product/create";
    }

   @PostMapping
    public String saveProduct(@ModelAttribute products product, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            productService.addNewProduct(product, imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception, maybe add a message to the model or redirect to an error page
        }
        return "redirect:/admin/products";
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
