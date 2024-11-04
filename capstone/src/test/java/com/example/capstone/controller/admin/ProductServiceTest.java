package com.example.capstone.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.capstone.IdGeneratorService;
import com.example.capstone.model.admin.Category;
import com.example.capstone.model.admin.ProductVariant;
import com.example.capstone.model.admin.Products;
import com.example.capstone.model.admin.ProductInventory;
import com.example.capstone.repository.admin.CategoryRepository;
import com.example.capstone.repository.admin.ProductVariantRepository;
import com.example.capstone.repository.admin.inventoryRepository;
import com.example.capstone.repository.admin.productRepository;


public class ProductServiceTest {

    @InjectMocks
    private com.example.capstone.service.productService productService;

    @Mock
    private productRepository productRepository;

    @Mock
    private ProductVariantRepository variantRepository;

    @Mock
    private inventoryRepository inventoryRepository;

    @Mock
    private Cloudinary cloudinary; // Mock Cloudinary dependency

    @Mock
    private MultipartFile imageFile;

    @Mock // Mock for CategoryRepository
    private CategoryRepository categoryRepository;

    @Mock // Add mock for IdGeneratorService
    private IdGeneratorService idGeneratorService; // Mock IdGeneratorService

    // Mock the Uploader class
    @Mock
    private Uploader uploader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Set up the Cloudinary mock to return the mocked Uploader
        when(cloudinary.uploader()).thenReturn(uploader);
        
        // Mock the idGeneratorService behavior if needed
        when(idGeneratorService.generateProductId(any())).thenReturn("generated-id"); // Example
    }

    @Test
    public void testAddNewProduct() throws IOException {
        // Arrange
        Products product = new Products();
        product.setProdName("Test Product");
        product.setProdDescription("Description of Test Product");

        // Create and set a category
        Category category = new Category();
        category.setCategoryId("cat-001"); // Set an example ID
        product.setCategory(category); // Set the category in the product

        when(categoryRepository.findById(any())).thenReturn(Optional.of(category));

        List<ProductVariant> variants = new ArrayList<>();
        ProductVariant variant1 = new ProductVariant();
        variant1.setSize("M");
        variant1.setPrice(10.0);
        variants.add(variant1);

        List<ProductInventory> inventories = new ArrayList<>();
        ProductInventory inventory1 = new ProductInventory();
        inventory1.setQuantity(100);
        inventories.add(inventory1);

        product.setVariants(variants);
        product.setInventories(inventories);

        // Mock the behavior of the uploader's upload method to return a URL
        HashMap<String, String> uploadResult = new HashMap<>();
        uploadResult.put("url", "http://example.com/image.jpg"); // Simulated URL
        when(uploader.upload(any(), anyMap())).thenReturn(uploadResult);

        // Act
        productService.addNewProduct(product, imageFile);

        // Assert
        verify(productRepository, times(1)).save(product);
        verify(variantRepository, times(1)).save(any(ProductVariant.class));
        verify(inventoryRepository, times(1)).save(any(ProductInventory.class));
        
        // Verify that the Cloudinary uploader was called
        verify(uploader, times(1)).upload(any(), anyMap());
    }
}