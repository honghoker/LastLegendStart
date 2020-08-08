package com.example.locationsave.HEP.Address;

public class SearchAreaArrayEntity {
    private String title;
    private String address;
    private String roadAddress;

    public SearchAreaArrayEntity(String title, String address, String roadAddress) {
        this.title = title;
        this.address = address;
        this.roadAddress = roadAddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }
}
