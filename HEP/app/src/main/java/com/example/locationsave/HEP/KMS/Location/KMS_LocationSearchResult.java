package com.example.locationsave.HEP.KMS.Location;

public class KMS_LocationSearchResult {
    private String Title;
    private String RoadAddress;
    private double longitude;
    private double latitude;

    public KMS_LocationSearchResult(String title, String roadAddress, String longitude, String latitude) {
        Title = title;
        RoadAddress = roadAddress;
        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
    }

    public String getTitle() {
        return Title;
    }

    public String getRoadAddress() {
        return RoadAddress;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setLongitude(Double longitude) { //왜 this 로 찍히는지
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}