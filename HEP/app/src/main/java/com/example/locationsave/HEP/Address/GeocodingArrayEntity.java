package com.example.locationsave.HEP.Address;

public class GeocodingArrayEntity {
    private String roadAddress;
    private String jibunAddress;
    private String longitude; // 경도 y
    private String latitude; // 위도 x

    public GeocodingArrayEntity(String jibunAddress, String roadAddress, String longitude, String latitude) {
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public String getJibunAddress() {
        return jibunAddress;
    }

    public void setJibunAddress(String jibunAddress) {
        this.jibunAddress = jibunAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
