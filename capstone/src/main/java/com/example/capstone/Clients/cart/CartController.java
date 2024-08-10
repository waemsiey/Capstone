package com.example.capstone.Clients.cart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.capstone.users.users;

@Controller
@RequestMapping("/")
public class CartController {

   @GetMapping("/cart")
    public String viewCart(Model model){
        model.addAttribute("user", new users());
        return "cart";
    }
    
}
