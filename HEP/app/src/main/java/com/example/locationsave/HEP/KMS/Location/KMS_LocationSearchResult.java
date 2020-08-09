package com.example.locationsave.HEP.KMS.Location;

public class KMS_LocationSearchResult {
    private String Title;         //타이틀
    private String RoadAddress;   //도로명 주소
    private double longitude; // 경도 y
    private double latitude; // 위도 x
    private String tempLongitude;
    private String tempLatitude;

    public KMS_LocationSearchResult(String title, String roadAddress, String longitude, String latitude) {
        Title = title;
        RoadAddress = roadAddress;
        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
    }
/*
    public KMS_LocationSearchResult(Dictionary d)
    {
        Title = d.getEnglish();
        RoadAddress = d.getKorean();
        this.longitude = longitude;
        this.latitude = latitude;
    }*/


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

    public void setRoadAddress(String roadAddress) {
        RoadAddress = roadAddress;
    }

    public void setLongitude(Double longitude) { //왜 this 로 찍히는지
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
