package com.example.capstone.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface userRepository extends JpaRepository <users, UUID>{
    Optional<users> findByEmail(String email);
    Optional <users> findByEmailAndPassword(String email, String password);
    
}
