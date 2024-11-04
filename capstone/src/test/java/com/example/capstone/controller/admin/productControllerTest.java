package com.example.capstone.controller.admin;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.multipart.MultipartFile;

import com.example.capstone.model.admin.Products;
import com.example.capstone.service.CategoryService;
import com.example.capstone.service.VariantService;
import com.example.capstone.service.inventoryService;
import com.example.capstone.service.productService;

@WebMvcTest(productController.class) // Use uppercase for the class name
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private productService productService; // Mock for ProductService

    @MockBean
    private CategoryService categoryService; // Mock for CategoryService

    @MockBean
    private VariantService variantService; // Mock the service

    @MockBean
    private inventoryService inventoryService; // Mock the service

    @Test
    void testSaveProduct() throws Exception {
        // Arrange
        Products product = new Products();
        product.setSku("SKU12345");
        product.setProdName("Sample Product");
    
        // Mock the service method to return the product
        when(productService.addNewProduct(any(Products.class), any(MultipartFile.class))).thenReturn(product);
    
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/products/saveProduct") // Use multipart
                .file("imageFile", "dummy content".getBytes()) // Simulate file upload
                .param("sku", "SKU12345")
                .param("prodName", "Sample Product")
                .param("category", "someCategoryId") // Add a valid category ID
                .with(csrf())
                .with(user("admin").roles("ADMIN"))) 
                .andExpect(status().is3xxRedirection()) // Expecting a redirection
                .andExpect(redirectedUrl("/admin/products")) // Expecting redirection to product list
                .andExpect(flash().attributeExists("success")); // Check for the correct flash attribute
    
        // Verify that the service method was called once
        verify(productService, times(1)).addNewProduct(any(Products.class), any(MultipartFile.class));
    }
    // Optionally, you can add more tests here for other scenarios
}