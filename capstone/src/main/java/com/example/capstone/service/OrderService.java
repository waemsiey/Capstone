

package com.example.capstone.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.capstone.model.client.BillingDetail;
import com.example.capstone.model.client.CartItem;
import com.example.capstone.model.client.Order;
import com.example.capstone.model.client.PaymentDetails;
import com.example.capstone.model.client.ShippingMethod;
import com.example.capstone.repository.client.BillingDetailRepository;
import com.example.capstone.repository.client.CartItemRepository;
import com.example.capstone.repository.client.OrderItemRepository;
import com.example.capstone.repository.client.OrderRepository;
import com.example.capstone.repository.client.PaymentMethodRepository;
import com.example.capstone.repository.client.ShippingMethodRepository;
import com.example.capstone.users.users;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private BillingDetailRepository billingDetailRepository;

    @Autowired
    private ShippingMethodRepository shippingDetailRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Saving the billing details
    public BillingDetail saveBillingDetail(BillingDetail billingDetail,  users currentUser) {
        logger.info("Attempting to save BillingDetail: {}", billingDetail);
        BillingDetail savedBillingDetail = billingDetailRepository.save(billingDetail);
        billingDetail.setUser(currentUser); 
        logger.info("BillingDetail saved successfully with ID: {}", savedBillingDetail.getId());
        return savedBillingDetail;
    }

    // getting the billing ID
    public BillingDetail getBillingDetailById(Long id) {
        return billingDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid billing ID: " + id));
    }

    // Saving the shipping method details
    public ShippingMethod saveShippingMethod(ShippingMethod shippingMethod) {

        logger.info("Attempting to save ShippingMethod: {}", shippingMethod);
        ShippingMethod savedShippingMethod = shippingDetailRepository.save(shippingMethod);
        logger.info("BillingDetail saved successfully with ID: {}", savedShippingMethod.getId());
        return savedShippingMethod;
    }

    // Linking the shipping and billing for
    public void linkBillingToShipping(BillingDetail billingDetail, ShippingMethod shippingMethod) {
        billingDetail.setShippingMethod(shippingMethod);
        shippingMethod.setBillingDetail(billingDetail);
        billingDetailRepository.save(billingDetail);
    }

    // Getting the shipping id
    public ShippingMethod getShippingDetailById(Long id) {
        return shippingDetailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Shipping ID: " + id));
    }

    public ShippingMethod getShippingMethodByBillingId(Long billingId) {
        return shippingDetailRepository.findByBillingDetailId(billingId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Shipping method not found for billing ID: " + billingId));
    }

    // Saving payment method details
    public PaymentDetails savePaymentMethod(PaymentDetails paymentDetails) {
        logger.info("Attempting to save PaymentDetails: {}", paymentDetails);
        PaymentDetails savePaymentMethod = paymentMethodRepository.save(paymentDetails);
        return savePaymentMethod;
    }

    public PaymentDetails getPaymentDetailsByShippingId(Long shippingId) {
        return paymentMethodRepository.findByShippingMethodId(shippingId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Payment details not found for shipping ID: " + shippingId));
    }

    public ShippingMethod getShippingMethodById(Long shippingId) {
        return shippingDetailRepository.findById(shippingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Shipping ID: " + shippingId));
    }

    public PaymentDetails getPaymentDetailsById(Long paymentId) {
        return paymentMethodRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Payment ID: " + paymentId));
    }

    public Order createOrder(List<CartItem> cartItems, BillingDetail billingDetail,
        ShippingMethod shippingMethod, PaymentDetails paymentDetails) {

    // Create and set up the Order first
    Order order = new Order();
    order.setOrderDate(LocalDateTime.now());
    order.setStatus("Pending");
    order.setCartItems(cartItems);
    order.setBillingDetail(billingDetail);
    order.setShippingMethod(shippingMethod);
    order.setPaymentDetails(paymentDetails);

    // Save the order first
    Order savedOrder = orderRepository.save(order);

    // Save CartItems and link them to the Order
    for (CartItem cartItem : cartItems) {
        cartItem.setOrder(savedOrder);  // Link the cart item to the order
        cartItemRepository.save(cartItem); // Persist the cart items
    }

    return savedOrder;
}

    
    public Order saveOrder(BillingDetail billingDetail, ShippingMethod shippingMethod, PaymentDetails paymentDetails, List<CartItem> cartItems, users currentUser) {
        logger.info("Attempting to save Order with billing, shipping, and payment details");
    
        Order order = new Order();
        order.setBillingDetail(billingDetail);
        order.setShippingMethod(shippingMethod);
        order.setPaymentDetails(paymentDetails);
        order.setOrderDate(LocalDateTime.now());
        order.setUser(currentUser);
        // Save the order itself
        Order savedOrder = orderRepository.save(order);
    
        // Link each cart item to the order
        cartItems.forEach(cartItem -> {
            cartItem.setOrder(savedOrder);
            cartItemRepository.save(cartItem);
        });
    
        logger.info("Order saved successfully with ID: {}", savedOrder.getId());
        return savedOrder;
    }

    public void updateOrderStatus(Long orderId, String status) {
        // Retrieve the order by its ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderId));
        
        // Update the order's status
        order.setStatus(status);
    
        // Save the updated order
        orderRepository.save(order);
        
        logger.info("Order status updated successfully. Order ID: {}, New Status: {}", orderId, status);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
