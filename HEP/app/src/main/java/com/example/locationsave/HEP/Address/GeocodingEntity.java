package com.example.locationsave.HEP.Address;

public class GeocodingEntity {
    private String json;
    private String roodAddress;
    private String jibunAddress;
    private String englishAddress;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getRoodAddress() {
        return roodAddress;
    }

    public void setRoodAddress(String roodAddress) {
        this.roodAddress = roodAddress;
    }

    public String getJibunAddress() {
        return jibunAddress;
    }

    public void setJibunAddress(String jibunAddress) {
        this.jibunAddress = jibunAddress;
    }

    public String getEnglishAddress() {
        return englishAddress;
    }

    public void setEnglishAddress(String englishAddress) {
        this.englishAddress = englishAddress;
    }
}
