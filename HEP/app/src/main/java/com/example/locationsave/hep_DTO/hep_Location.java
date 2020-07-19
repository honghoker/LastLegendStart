package com.example.locationsave.hep_DTO;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class hep_Location {

    String name;
    String addr;
    String detailaddr;
    String phone;
    String memo;
    long time;
    long Latitude; // 위도
    long longitude; // 경도

    public hep_Location(){
    }

    public hep_Location(String name, String addr, String detailaddr, String phone, String memo){
        this.name = name;
        this.addr = addr;
        this.detailaddr = detailaddr;
        this.phone = phone;
        this.memo = memo;
        this.time = System.currentTimeMillis();
        //new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis())); 년월일 시분초
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("name", name);
        result.put("addr", addr);
        result.put("detailaddr", detailaddr);
        result.put("phone", phone);
        result.put("memo", memo);
        result.put("time", time);

        return result;
    }
}
