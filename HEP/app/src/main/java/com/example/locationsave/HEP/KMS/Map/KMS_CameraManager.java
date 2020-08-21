package com.example.locationsave.HEP.KMS.Map;

import android.util.Log;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;

public class KMS_CameraManager {
    private static final KMS_CameraManager CameraManagerInstance = new KMS_CameraManager();

    private KMS_CameraManager() {}

    public static KMS_CameraManager getInstanceCameraManager(){
        return CameraManagerInstance;
    }

    private double currentLatitude;
    private double currentLongitued;


    public void MoveCameraOnMarkerPosition(Marker marker, NaverMap naverMap){
        LatLng latLng = marker.getPosition();
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng);
        cameraUpdate.animate(CameraAnimation.Fly);
        naverMap.moveCamera(cameraUpdate);
    }

    public void MoveCameraOnLatlngPosition(double latitude, double longitude, NaverMap naverMap){
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng);
        cameraUpdate.animate(CameraAnimation.Fly);
        naverMap.moveCamera(cameraUpdate);
    }

    public void setCameraCurrentPosition(double latitude, double longitude){
        currentLatitude = latitude;
        currentLongitued = longitude;
    }
}