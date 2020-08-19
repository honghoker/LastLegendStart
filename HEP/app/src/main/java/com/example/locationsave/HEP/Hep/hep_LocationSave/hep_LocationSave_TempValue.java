package com.example.locationsave.HEP.Hep.hep_LocationSave;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;

public class hep_LocationSave_TempValue {
    private String Title; //타이틀
    private String Address; //주소
    private String DetailAddress; //상세 주소
    private String PhoneNumber; //연락처
    private String Memo; //메모
    public ArrayList<hep_ImageData> ImageDataArray; //이미지 정보
    private ArrayList<String> hashTagArr; //태그정보


    private static hep_LocationSave_TempValue hep_locationSave_tempValue = null;

    public hep_LocationSave_TempValue() {

    }

    public hep_LocationSave_TempValue getHep_locationSave_tempValue(){
        if(hep_locationSave_tempValue == null) {
            hep_locationSave_tempValue = new hep_LocationSave_TempValue();
        }
        return hep_locationSave_tempValue;
    }

    public void setData(String title, String address, String detailAddress, String phoneNumber, String memo) {
        Title = title;
        Address = address;
        DetailAddress = detailAddress;
        PhoneNumber = phoneNumber;
        Memo = memo;
        this.ImageDataArray = new ArrayList<>();
        this.ImageDataArray.addAll(new hep_locationImageDataArr().getImageDataArrayInstance());
        
        this.hashTagArr = new ArrayList<>();
        this.hashTagArr.addAll(new hep_HashTagArr().getHashTagArr());
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

    public ArrayList<hep_ImageData> getImageDataArray() {
        if(ImageDataArray == null)
            ImageDataArray = new ArrayList<hep_ImageData>();
        return ImageDataArray;
    }

    public void setImageDataArray(ArrayList<hep_ImageData> imageDataArray) {
        ImageDataArray = imageDataArray;
    }

    public ArrayList<String> getHashTagArr() {
        if(hashTagArr == null)
            hashTagArr = new ArrayList<>();
        return hashTagArr;
    }

    public void setHashTagArr(ArrayList<String> hashTagArr) {
        this.hashTagArr = hashTagArr;
    }

    public static void setHep_locationSave_tempValue(hep_LocationSave_TempValue hep_locationSave_tempValue) {
        hep_LocationSave_TempValue.hep_locationSave_tempValue = hep_locationSave_tempValue;
    }
}
