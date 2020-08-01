package com.example.locationsave.HEP.Hep.hep_DTO;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class hep_Location {

    public String diretoryid; // oauth token
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
//
    public hep_Location(String token, String name, String addr,
                        String detailaddr, String contact, String memo,
                        long time, double latitude, double longitude,
                        String tag0, String tag1, String tag2, String tag3, String tag4) {
        this.diretoryid = diretoryid;
        this.name = name;
        this.addr = addr;
        this.detailaddr = detailaddr;
        this.contact = contact;
        this.memo = memo;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getDiretoryid() {
        return diretoryid;
    }

    public void setDiretoryid(String diretoryid) {
        this.diretoryid = diretoryid;
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
