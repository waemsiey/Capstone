package com.example.capstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.capstone.users.users;

@Service
public class ClientService {

    @Autowired
    private com.example.capstone.users.userRepository userRepository;

    // Get total number of clients
    public long getClientCount() {
        List<users> clients = userRepository.findByRole("CLIENT");
        return clients.size();  
    }
    
}
