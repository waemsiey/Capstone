package com.example.capstone;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class IdGeneratorService {
    private final AtomicInteger counter = new AtomicInteger(1000);

    public String generateProductId(String categoryCode) {

        int uniqueID = counter.getAndIncrement();
        return categoryCode.toUpperCase() + "-"  + String.format("%03d", uniqueID);
    }

    public String generateUserId() {
        return "CUS-" + counter.getAndIncrement();
    }
    public String generateCategoryId(){
        return "cat" + String.format("%03d", counter.getAndIncrement());
    }
}
