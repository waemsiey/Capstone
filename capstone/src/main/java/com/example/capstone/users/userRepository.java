package com.example.capstone.users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface userRepository extends JpaRepository <users, String>{
    Optional<users> findByEmail(String email);
    Optional <users> findByEmailAndPassword(String email, String password);

    List<users> findByRole(String role);
    
}
