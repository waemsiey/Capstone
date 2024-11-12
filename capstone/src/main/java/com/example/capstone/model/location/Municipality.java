package com.example.capstone.model.location;

public class Municipality {
    private String municipality_id;
    private String province_id;
    private String municipality_name;
 
    public String getMunicipality_id() {
        return municipality_id;
    }

    public void setMunicipality_id(String municipality_id) {
        this.municipality_id = municipality_id;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getMunicipality_name() {
        return municipality_name;
    }

    public void setMunicipality_name(String municipality_name) {
        this.municipality_name = municipality_name;
    }

 
}
