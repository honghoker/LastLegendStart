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


        public void MoveCameraOnMarkerPosition(Marker marker, NaverMap naverMap){  //마커 입력하면 이동
        LatLng latLng = marker.getPosition();
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng); //카메라 업데이트 위해 클릭마커 좌표 입력

        cameraUpdate.animate(CameraAnimation.Fly); //애니메이션

        naverMap.moveCamera(cameraUpdate);
        Log.d("####MoveCamera",   "카메라를 마커 위치로 이동" + marker.getCaptionText() + " " + marker.getPosition());

    }

    public void MoveCameraOnLatlngPosition(double latitude, double longitude, NaverMap naverMap){  //좌표 입력하면 이동
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng); //카메라 업데이트 위해 클릭마커 좌표 입력

        cameraUpdate.animate(CameraAnimation.Fly); //애니메이션

        naverMap.moveCamera(cameraUpdate);
        Log.d("####MoveCamera",   "카메라를 위경도 위치로 이동" + " " + latLng);

    }
}
