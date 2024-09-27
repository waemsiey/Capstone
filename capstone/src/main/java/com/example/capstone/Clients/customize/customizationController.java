package com.example.capstone.Clients.customize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.capstone.Admin.adminProducts.products;
import com.example.capstone.users.users;


@Controller
@RequestMapping("/")
public class customizationController {

    @GetMapping("/customize")
    public String viewCustomize(Model model){
        model.addAttribute("user", new users());
        model.addAttribute("product", new products());
        return "Client/customize";
    }
}
