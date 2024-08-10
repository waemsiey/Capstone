package com.example.capstone.Clients.products;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.capstone.users.users;

@Controller
@RequestMapping("/")
public class ClientproductController {

    @GetMapping("/products")
    public String viewHompage(Model model){
        model.addAttribute("user", new users());
        return "products";
    }
}
