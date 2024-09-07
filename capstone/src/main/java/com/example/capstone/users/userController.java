package com.example.capstone.users;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class userController {
    
    private final userService userservice;
    
    @Autowired //injecting the userService class as the userservice to use the method in this class!
    public userController(userService userservice) {
        this.userservice = userservice;
    }

    @GetMapping("/signup") //directing a form from the html
    public String register(Model model){
        model.addAttribute("user", new users());
        return "signup";
    }

    @PostMapping("/signup") 
    public String registerNewUser(
                @ModelAttribute users user,
                @RequestParam String passwordConfirm,
                Model model) throws Exception {
        
        Optional<users> userOptional = userservice.findByEmail(user.getEmail());
        try {
            userservice.addNewUser(user, passwordConfirm);
            model.addAttribute("message", "User registered successfully!");
            return "Client/home";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "signup";
        }
    }
    @GetMapping("/login")
    public String viewLoginpage(Model model){
        model.addAttribute("user", new users());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute users user, Model model) {
        Optional<users> loginUser = userservice.loginUser(user.getEmail(), user.getPassword());
        if(loginUser.isPresent()){
            model.addAttribute("message","Login Succesfully");
            return "Client/home";
        }
        else {
            model.addAttribute("message", "Email not found!");
            return "login";
        }
    }

    @GetMapping("/account")
    public String account() {
        return "account"; //html file
    }

    @GetMapping("/logout")
    public String logout() {
        return "Client/home"; //html file
    }

    @GetMapping("/help")
    public String help() {
       
        return "help"; //html file
    }
    
// for postmantestingusers
    @PostMapping("/api/login")
    @ResponseBody 
    public ResponseEntity<String> loginUser(@RequestBody users user) {
        Optional<users> loginUser = userservice.loginUser(user.getEmail(), user.getPassword());
        if (loginUser.isPresent()) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("Email or password is incorrect");
        }
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<String> registerUserApi(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String name = requestBody.get("name");
        String password = requestBody.get("password");
        String passwordConfirm = requestBody.get("passwordConfirm");
    
        users user = new users(name, email, password);
    
        try {
            userservice.addNewUser(user, passwordConfirm);
            return ResponseEntity.status(HttpStatus.SC_CREATED).body("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(e.getMessage());
        }
    }
   @RestController
    @RequestMapping("/api")
    public static class ApiUserController {
        private final userService userservice;

        @Autowired
        public ApiUserController(userService userservice) {
            this.userservice = userservice;
        }

        @GetMapping("/users")
        public ResponseEntity<List<users>> getAllUsers() {
            List<users> users = userservice.getUsers();
            return ResponseEntity.ok(users);
        }
    }
}

