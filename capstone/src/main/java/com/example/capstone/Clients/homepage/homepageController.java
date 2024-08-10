package com.example.capstone.Clients.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.capstone.users.users;

@Controller
@RequestMapping("/")
public class homepageController {

    @GetMapping("/home")
    public String viewHompage(Model model){
        model.addAttribute("user", new users());
        return "home";
    }
}
