package com.example.locationsave.HEP.KMS.Map;

import android.util.Log;
import android.view.Gravity;

import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.UiSettings;

public class KMS_MapOption {
    private static final KMS_MapOption MapOptionInstance = new KMS_MapOption();

    private KMS_MapOption() {}

    public static KMS_MapOption getInstanceMapOption(){
        return MapOptionInstance;
    }

    NaverMapOptions options;

    private double currentLatitude;
    private double currentLongitued;

    public double LastLatitude;
    public double LastLongitued;

    public NaverMapOptions setFirstOptions() {
        if(LastLatitude == 0.0 && LastLongitued == 0.0){
            LastLatitude = 35.857654;
            LastLongitued= 128.498123;
        }

        options = new NaverMapOptions()
                .camera(new CameraPosition(new LatLng(LastLatitude, LastLongitued), 16))
                .mapType(NaverMap.MapType.Navi)
                .nightModeEnabled(false);

        return options;
    }

    public void setFirstAddOptions(double latitude, double longitude) {
        currentLatitude = latitude;
        currentLongitued = longitude;
    }

    public NaverMapOptions getFirstAddOptions() {
        options = new NaverMapOptions()
                .camera(new CameraPosition(new LatLng(currentLatitude, currentLongitued), 16))
                .mapType(NaverMap.MapType.Navi)
                .nightModeEnabled(false);
        return options;
    }

    public void setOnLightMap(){
        KMS_MapFragment.NMap.setNightModeEnabled(false);
    }

    public void setOnNightMap(){
        KMS_MapFragment.NMap.setNightModeEnabled(true);
    }

    public void setMapUI(NaverMap naverMap) {
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setZoomControlEnabled(false);
        uiSettings.setLocationButtonEnabled(false);

        uiSettings.setLogoMargin(0,0,20000,100000);
        uiSettings.setLogoGravity(Gravity.LEFT|Gravity.TOP);
        uiSettings.setLogoClickEnabled(false);

        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleBarEnabled(false);

        KMS_MapFragment.compassView.setMap(naverMap);
        KMS_MapFragment.scaleBarView.setMap(naverMap);
        KMS_MapFragment.zoomControlView.setMap(naverMap);
        KMS_MapFragment.locationButtonView.setMap(naverMap);
        KMS_MapFragment.LogoView.setMap(naverMap);
    }

    public void setMapPadding(NaverMap naverMap){
        naverMap.setContentPadding(0, 100, 0, 50);
    }

}