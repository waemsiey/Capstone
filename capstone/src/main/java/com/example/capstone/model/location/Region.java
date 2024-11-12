package com.example.capstone.model.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Region {
    private String region_id;
    private String region_name;
    private String region_description;

    
    public String getRegion_id() {
        return region_id;
    }
    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }
    public String getRegion_name() {
        return region_name;
    }
    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
    public String getRegion_description() {
        return region_description;
    }
    public void setRegion_description(String region_description) {
        this.region_description = region_description;
    }

    // Getters and setters
   
}
