package com.example.locationsave.HEP.Hep.hep_DTO;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class hep_Location {

    public String token; // oauth token
    public String name;
    public String addr;
    public String detailaddr;
    public String contact;
    public String memo;
    public long time;
    public double Latitude; // 위도
    public double longitude; // 경도
    public String tag0;
    public String tag1;
    public String tag2;
    public String tag3;
    public String tag4;

    public hep_Location(){
    }
//
    public hep_Location(String token, String name, String addr,
                        String detailaddr, String contact, String memo,
                        long time, double latitude, double longitude,
                        String tag0, String tag1, String tag2, String tag3, String tag4) {
        this.token = token;
        this.name = name;
        this.addr = addr;
        this.detailaddr = detailaddr;
        this.contact = contact;
        this.memo = memo;
        this.time = time;
        Latitude = latitude;
        this.longitude = longitude;
        this.tag0 = tag0;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
        this.tag4 = tag4;
    }
    public hep_Location(String name, String addr,
                        String detailaddr, String contact, String memo) {

        this.name = name;
        this.addr = addr;
        this.detailaddr = detailaddr;
        this.contact = contact;
        this.memo = memo;
        this.time = System.currentTimeMillis();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("name", name);
        result.put("addr", addr);
        result.put("detailaddr", detailaddr);
        result.put("contact", contact);
        result.put("memo", memo);
        result.put("time", time);

        return result;
    }


    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }

    public String getDetailaddr() {
        return detailaddr;
    }

    public String getContact() {
        return contact;
    }

    public String getMemo() {
        return memo;
    }

    public long getTime() {
        return time;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTag0() {
        return tag0;
    }

    public String getTag1() {
        return tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public String getTag4() {
        return tag4;
    }
}
