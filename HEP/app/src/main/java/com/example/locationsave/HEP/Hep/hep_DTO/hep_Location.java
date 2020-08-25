package com.example.locationsave.HEP.Hep.hep_DTO;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.locationsave.HEP.KMS_MainActivity;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class hep_Location implements Parcelable {

    public String directoryid;
    public String name;
    public String addr;
    public String detailaddr;
    public String contact;
    public String memo;
    public double latitude; // 위도
    public double longitude; // 경도
    public long time;

    public hep_Location() {
    }

    private hep_Location(Parcel in) {
        this.directoryid = in.readString();
        this.name = in.readString();
        this.addr = in.readString();
        this.detailaddr = in.readString();
        this.contact = in.readString();
        this.memo = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.time = in.readLong();
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

    public static final Creator<hep_Location> CREATOR = new Creator<hep_Location>() {
        @Override
        public hep_Location createFromParcel(Parcel in) {
            return new hep_Location(in);
        }

        @Override
        public hep_Location[] newArray(int size) {
            return new hep_Location[size];
        }
    };

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("directoryid", directoryid);
        result.put("name", name);
        result.put("addr", addr);
        result.put("detailaddr", detailaddr);
        result.put("contact", contact);
        result.put("memo", memo);
        if(latitude != 0 && longitude != 0) {
            result.put("latitude", latitude);
            result.put("longitude", longitude);
        }
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

    public String getDirectoryid() {
        return directoryid;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest,int flags){
        dest.writeString(directoryid);
        dest.writeString(name);
        dest.writeString(addr);
        dest.writeString(detailaddr);
        dest.writeString(contact);
        dest.writeString(memo);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeLong(time);
    }
}
