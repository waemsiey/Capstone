package com.example.capstone.controller.client;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.capstone.model.admin.Products;
import com.example.capstone.model.client.BillingDetail;
import com.example.capstone.model.client.CartItem;
import com.example.capstone.model.client.Order;
import com.example.capstone.model.client.PaymentDetails;
import com.example.capstone.model.client.ShippingMethod;
import com.example.capstone.model.location.Barangay;
import com.example.capstone.model.location.Municipality;
import com.example.capstone.model.location.Province;
import com.example.capstone.model.location.Region;
import com.example.capstone.service.CartService;
import com.example.capstone.service.OrderService;
import com.example.capstone.service.productService;
import com.example.capstone.users.userService;
import com.example.capstone.users.users;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/")
@SessionAttributes({ "cartItems", "billing", "shipping", "payment" })
public class CartController {

    private final CartService cartService;
    private final productService productService;

    @Autowired
    private userService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    public CartController(CartService cartService, productService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @ModelAttribute("cartItems")
    public List<CartItem> initializeCartItems() {
        return cartService.getCartItems();
    }

    @ModelAttribute("billing")
    public BillingDetail initializeBilling() {
        
        return new BillingDetail();
    }

    @ModelAttribute("shipping")
    public ShippingMethod initializeShipping() {
        return new ShippingMethod();
    }

    @ModelAttribute("payment")
    public PaymentDetails initializePayment() {
        return new PaymentDetails();
    }

    // Add product to cart
    @GetMapping("/add/{sku}")
    public String addToCart(@PathVariable String sku,
            @RequestParam(defaultValue = "1") int quantity,
            @RequestParam String size,
            @RequestParam String color,
            @RequestParam Double price, Model model) {

        Products product = productService.getProductById(sku).orElse(null);

        if (product == null) {
            model.addAttribute("error", "Product not found!");
            return "redirect:/cart";
        }

        cartService.addProductToCart(sku, quantity, size, color, price);
        model.addAttribute("product", product);
        return "redirect:/cart";
    }

    // View all items in the cart
    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        return "Client/cart";
    }

    // View the billing page with an initialized BillingDetail object
    @GetMapping("/checkout")
    public String viewBillingPage(Model model, @SessionAttribute(value = "user", required = false) users currentUser)
            throws IOException {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Your cart is empty!");
            return "redirect:/cart";
        }
        // Cheker for user log
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Load location data from JSON files
        ObjectMapper objectMapper = new ObjectMapper();
        model.addAttribute("barangays", objectMapper.readValue(new File("src/main/resources/data/table_barangay.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Barangay.class)));
        model.addAttribute("municipalities",
                objectMapper.readValue(new File("src/main/resources/data/table_municipality.json"),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Municipality.class)));
        model.addAttribute("regions", objectMapper.readValue(new File("src/main/resources/data/table_region.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Region.class)));
        model.addAttribute("provinces", objectMapper.readValue(new File("src/main/resources/data/table_province.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Province.class)));

        return "Client/Billings/BillingForm";
    }

    // Proceed to shipping after billing info is entered
    @PostMapping("/checkout")
    public String proceedToCheckOut(@Valid @ModelAttribute("billing") BillingDetail billing, BindingResult result,
            RedirectAttributes redirectAttributes) {
        return "redirect:/shipping";
    }

    // View and select the shipping method
    @GetMapping("/shipping")
    public String viewShippingPage(Model model) {
        return "Client/Billings/ShippingMethod";
    }

    @PostMapping("/shipping")
    public String proceedToShipping(@ModelAttribute("shipping") ShippingMethod shippingMethod, Model model) {
        // Set estimated delivery date based on method
        if ("J&T EXPRESS DELIVERY".equals(shippingMethod.getMethod())
                || "SUPERLINE BUS".equals(shippingMethod.getMethod())) {
            shippingMethod.setEstimatedDeliveryDate(LocalDate.now().plusDays(10));
        } else if ("PICK UP".equals(shippingMethod.getMethod())) {
            shippingMethod.setEstimatedDeliveryDate(LocalDate.now().plusDays(7));
        }

        return "redirect:/payment";
    }

    @GetMapping("/payment")
    public String viewPaymentPage(Model model) {
        PaymentDetails paymentDetails = new PaymentDetails();
        model.addAttribute("paymentDetails", paymentDetails);
        return "Client/Billings/PaymentMethod";
    }

    @PostMapping("/payment")
    public String postPaymentMethod(@ModelAttribute("payment") PaymentDetails paymentDetails, Model model) {
        paymentDetails.setPaymentDate(LocalDateTime.now());
        return "redirect:/confirm";
    }

    // Order confirmation and final data save
    @GetMapping("/confirm")
    public String showOrderConfirmationPage(@ModelAttribute("cartItems") List<CartItem> cartItems,
            @ModelAttribute("billing") BillingDetail billingDetail,
            @ModelAttribute("shipping") ShippingMethod shippingMethod,
            @ModelAttribute("payment") PaymentDetails paymentDetails,
            @SessionAttribute(value = "user", required = false) users currentUser,
            Model model) {
        

        
        if (currentUser == null) {

            model.addAttribute("redirect", "/checkout");
            return "redirect:/login"; // If not logged in, redirect to login page
        }

        // Check if cart is empty
        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("error", "Your cart is empty!");
            return "redirect:/cart";
        }

        // Check if billing information is null or empty
        if (billingDetail == null || billingDetail.getEmail() == null) {
            model.addAttribute("error", "Billing information is missing!");
            return "redirect:/checkout";
        }

        // Check if shipping and payment information are null
        if (shippingMethod == null || paymentDetails == null) {
            model.addAttribute("error", "Shipping or payment details are missing!");
            return "redirect:/shipping";
        }

        model.addAttribute("user", billingDetail);

        // Save order details (billing, shipping, and payment)
        try {
            BillingDetail savedBilling = orderService.saveBillingDetail(billingDetail, currentUser);
            ShippingMethod savedShipping = orderService.saveShippingMethod(shippingMethod);
            PaymentDetails savedPayment = orderService.savePaymentMethod(paymentDetails);

            // Link billing and shipping
            orderService.linkBillingToShipping(savedBilling, savedShipping);
            Order savedOrder = orderService.saveOrder(savedBilling, savedShipping, savedPayment, cartItems, currentUser);

            // Add saved details to model
            model.addAttribute("order", savedOrder);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("billingDetail", savedBilling);
            model.addAttribute("shippingMethod", savedShipping);
            model.addAttribute("paymentDetails", savedPayment);

        } catch (Exception e) {
            model.addAttribute("error", "Error processing your order: " + e.getMessage());
            return "Client/Billings/Checkout";
        }

        return "Client/Billings/OrderConfirmation";
    }

    // For handling errors
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

    @GetMapping("/precheckout")
    public String getMethodName(@ModelAttribute("billing") BillingDetail billingDetail, Model model) {
        return "login";
    }
  

}