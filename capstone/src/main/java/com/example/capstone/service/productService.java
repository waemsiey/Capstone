package com.example.capstone.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.capstone.IdGeneratorService;
import com.example.capstone.dto.ProductRequest;
import com.example.capstone.model.admin.Category;
import com.example.capstone.model.admin.ProductVariant;
import com.example.capstone.model.admin.Products;
import com.example.capstone.model.admin.ProductInventory;
import com.example.capstone.repository.admin.CategoryRepository;
import com.example.capstone.repository.admin.ProductVariantRepository;
import com.example.capstone.repository.admin.inventoryRepository;
import com.example.capstone.repository.admin.productRepository;

import jakarta.transaction.Transactional;

@Service
public class productService {

    private final productRepository productRepository;
    private final IdGeneratorService idGeneratorService;
    private final Cloudinary cloudinary;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository productVariantRepository;
    private final inventoryRepository inventoryRepository;

    @Autowired
    @Qualifier("transactionManager")
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public productService(@Qualifier("transactionManager") PlatformTransactionManager transactionManager,
                          productRepository productRepository, IdGeneratorService idGeneratorService,
                          Cloudinary cloudinary, CategoryRepository categoryRepository,
                          ProductVariantRepository productVariantRepository,
                          inventoryRepository inventoryRepository) {
        this.transactionManager = transactionManager;
        this.productRepository = productRepository;
        this.idGeneratorService = idGeneratorService;
        this.cloudinary = cloudinary;
        this.categoryRepository = categoryRepository;
        this.productVariantRepository = productVariantRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Products> getProductById(String sku) {
        return productRepository.findById(sku);
    }

    public List<Products> getProductByCategoryId(String categoryId) {
        return productRepository.findByCategory_categoryId(categoryId);
    }

    @Transactional
    public Products addNewProduct(ProductRequest request) throws IOException {
        Products product = request.getProducts();
        MultipartFile imageFile = request.getImageFile();
        List<ProductVariant> variants = request.getVariants();
        List<ProductInventory> inventories = request.getInventories();

        try {
            // Handle Image Upload
            if (imageFile != null && !imageFile.isEmpty()) {
                product.setImgURL(uploadImage(imageFile)); // Modularized image upload logic
            }

            // Set Category and Generate SKU
            Category category = categoryRepository.findById(product.getCategory().getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            product.setCategory(category);
            product.setSku(idGeneratorService.generateProductId(category.getCategoryCode()));

            // Save Product first to get an ID
            productRepository.save(product);

            // Add Variants to the product
            if (variants != null) {
                variants.forEach(variant -> addVariantToProduct(product, variant));
            }

            // Add Inventory 
            if (inventories != null) {
                inventories.forEach(inventory -> addInventoryToProduct(product, inventory));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add new product", e); // Trigger rollback on failure
        }

        return product;
    }

    // Modularize image upload logic
    private String uploadImage(MultipartFile imageFile) throws IOException {
        var uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString(); // Return the URL of the uploaded image
    }

    private void addVariantToProduct(Products product, ProductVariant variant) {
        variant.setProducts(product); // Set the product reference on the variant
        productVariantRepository.save(variant); // Save the variant
    }

    private void addInventoryToProduct(Products product, ProductInventory inventory) {
        inventory.setProducts(product); // Set the product reference
        inventoryRepository.save(inventory); // Save inventory
    }

    @Transactional
    public void updateProduct(String sku, Products updatedProduct, MultipartFile imageFile, List<ProductInventory> inventoryList) throws IOException {
        Optional<Products> productOptional = productRepository.findById(sku);
        if (productOptional.isPresent()) {
            Products existingProduct = productOptional.get();  // Get the existing product

            // Update the product details
            existingProduct.setProdName(updatedProduct.getProdName());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setProdDescription(updatedProduct.getProdDescription());

            // Handle image update
            try {
                if (imageFile != null && !imageFile.isEmpty()) {
                    existingProduct.setImgURL(uploadImage(imageFile)); // Use modularized image upload
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to update product image", e); // Trigger rollback on failure
            }

            // Update inventories
            if (inventoryList != null) {
                for (ProductInventory inventory : inventoryList) {
                    // Link the inventory back to the product
                    inventory.setProducts(existingProduct);
                    // Save the inventory
                    inventoryRepository.save(inventory); // Save inventory
                }
            }

            // Save the updated product
            productRepository.save(existingProduct);
        } else {
            throw new IllegalArgumentException("Product not found");
        }
    }

    @Transactional
    public void deleteProduct(String sku) {
        productRepository.deleteById(sku);
    }

    public List<Products> getProductByCategory(String category) {
        return productRepository.findByCategory_categoryName(category);
    }

    public List<Products> getLimitedProducts() {
        int limit = 3;
        Pageable pageable = PageRequest.of(0, limit);
        Page<Products> productPage = productRepository.findTopNproducts(pageable);
        return productPage.getContent();
    }


    @Transactional
    public boolean isProductAvailable(String sku) {
        Optional<Products> productOptional = productRepository.findById(sku);
        if (!productOptional.isPresent()) {
            throw new IllegalArgumentException("Product not found for SKU: " + sku);
        }

        Products product = productOptional.get();

        
        for (ProductVariant variant : product.getVariants()) {
            boolean isVariantAvailable = variant.getProductInventoryList().stream()
                    .anyMatch(inventory -> inventory.getQuantity() > 0);
            if (isVariantAvailable) {
                return true; 
            }
        }
        return false; // No variants with available stock
    }
}
