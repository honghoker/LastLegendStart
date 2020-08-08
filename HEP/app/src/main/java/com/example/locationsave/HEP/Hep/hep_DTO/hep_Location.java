package com.example.locationsave.HEP.Hep.hep_DTO;

import com.example.locationsave.HEP.KMS_MainActivity;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class hep_Location {

    public String directoryid;
    public String name;
    public String addr;
    public String detailaddr;
    public String contact;
    public String memo;
    public double latitude; // 위도
    public double longitude; // 경도
    public long time;

    public hep_Location(){
    }

    public hep_Location(String name, String addr,
                        String detailaddr, String contact, String memo, double latitude, double longitude) {

        this.directoryid = KMS_MainActivity.directoryid;
        this.name = name;
        this.addr = addr;
        this.detailaddr = detailaddr;
        this.contact = contact;
        this.memo = memo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = System.currentTimeMillis();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("directoryid", directoryid);
        result.put("name", name);
        result.put("addr", addr);
        result.put("detailaddr", detailaddr);
        result.put("contact", contact);
        result.put("memo", memo);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("time", time);

        return result;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setDetailaddr(String detailaddr) {
        this.detailaddr = detailaddr;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
