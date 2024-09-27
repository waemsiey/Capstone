package com.example.capstone.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.capstone.Admin.adminProducts.products;
import com.example.capstone.users.users;


@Controller
@RequestMapping("/")
public class adminController {

    @GetMapping("/admin")
    public String getAdminHome(Model model) {
        model.addAttribute("product", new products());
        model.addAttribute("user", new users());
        return "Admin/adminpage";
    }
    
}
