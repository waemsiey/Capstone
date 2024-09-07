package com.example.capstone.Admin.adminInventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface inventoryRepository extends JpaRepository <productInventory, Long> {

}
