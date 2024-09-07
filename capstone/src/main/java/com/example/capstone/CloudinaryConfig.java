package com.example.capstone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
        "cloud_name", "lightclouds",
        "api_key", "945885959417335",
        "api_secret", "MmTfKhehisYXkYGTEHPpev15KKg")
        );

    }
}
