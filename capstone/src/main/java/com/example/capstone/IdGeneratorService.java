package com.example.capstone;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class IdGeneratorService {
    private final AtomicInteger counter = new AtomicInteger(1000);

    public String generateProductId() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "PROD-" + timestamp + "-" + counter.getAndIncrement();
    }

    public String generateUserId() {
        return "CUS-" + counter.getAndIncrement();
    }
}
