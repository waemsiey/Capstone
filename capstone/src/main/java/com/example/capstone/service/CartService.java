package com.example.capstone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capstone.model.admin.Products;
import com.example.capstone.model.client.CartItem;
import com.example.capstone.repository.admin.productRepository;
import com.example.capstone.repository.client.CartItemRepository;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final productRepository productRepository;

    public CartService(CartItemRepository cartItemRepository, productRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }


    public CartItem addProductToCart(String sku, int quantity, String size, String color, Double price) {

        Products product = productRepository.findById(sku)
            .orElseThrow(() -> new RuntimeException("Product not found"));

            System.out.println("Adding to cart: SKU=" + sku + ", Size=" + size + ", Color=" + color);

    
        // Use orElseGet to lazily create a new CartItem if not found
        CartItem cartItem = cartItemRepository.findByProduct_SkuAndSizeAndColorAndPrice(sku, size, color, price)
            .orElseGet(() -> {
                CartItem newItem = new CartItem();
                newItem.setProduct(product);
                newItem.setSize(size);
                newItem.setColor(color);
                newItem.setPrice(price);
                newItem.setQuantity(0); // Initialize quantity to zero
                return newItem;
            });
    
        // Update the quantity
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    
        return cartItemRepository.save(cartItem);
    }
    // Update the quantity of an existing cart item
    public CartItem updateCartItem(Long cartItemId, int quantity,  String size, String color, Double price) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("CartItem not found"));
        
        cartItem.setQuantity(quantity);
        cartItem.setSize(size);
        cartItem.setColor(color);
        cartItem.setPrice(price);
        return cartItemRepository.save(cartItem);
    }

    // Remove an item from the cart
    public void removeCartItem(Long cartItemId) {
        
        cartItemRepository.deleteById(cartItemId);
    }

    // Get all items in the cart
    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }
}
