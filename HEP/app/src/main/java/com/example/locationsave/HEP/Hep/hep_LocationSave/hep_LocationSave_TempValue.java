package com.example.locationsave.HEP.Hep.hep_LocationSave;

import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class hep_LocationSave_TempValue {
    private String Title; //타이틀
    private String Address; //주소
    private String DetailAddress; //상세 주소
    private String PhoneNumber; //연락처
    private String Memo; //메모
    private Parcelable.Creator<hep_ImageData> CREATOR; //이미지 정보
    private ArrayList<String> hashTagArr; //태그정보

    public static hep_LocationSave_TempValue hep_locationSave_tempValue = new hep_LocationSave_TempValue();


    public hep_LocationSave_TempValue(){}

    public hep_LocationSave_TempValue(String title, String address, String detailAddress, String phoneNumber, String memo, Parcelable.Creator<hep_ImageData> CREATOR, ArrayList<String> hashTagArr) {
        Title = title;
        Address = address;
        DetailAddress = detailAddress;
        PhoneNumber = phoneNumber;
        Memo = memo;
        this.CREATOR = CREATOR;
        this.hashTagArr = hashTagArr;
    }

    private boolean flag = false;
    public void setTrueFlag(){
        flag = true;
    }
    public void setFalseFlag(){
        flag = false;
    }
    public boolean getFlag(){
        return flag;
    }


    public String getTitle() {
        return Title;

    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddress() {

        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDetailAddress() {
        return DetailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        DetailAddress = detailAddress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public Parcelable.Creator<hep_ImageData> getCREATOR() {
        return CREATOR;
    }

    public void setCREATOR(Parcelable.Creator<hep_ImageData> CREATOR) {
        this.CREATOR = CREATOR;
    }

    public ArrayList<String> getHashTagArr() {
        return hashTagArr;
    }

    public void setHashTagArr(ArrayList<String> hashTagArr) {
        this.hashTagArr = hashTagArr;
    }

}
