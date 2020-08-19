package com.example.locationsave.HEP.KMS.Map;

import android.util.Log;
import android.view.Gravity;

import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.widget.CompassView;
import com.naver.maps.map.widget.LocationButtonView;
import com.naver.maps.map.widget.ScaleBarView;
import com.naver.maps.map.widget.ZoomControlView;

public class KMS_MapOption {
    private static final KMS_MapOption MapOptionInstance = new KMS_MapOption();

    private KMS_MapOption() {}

    public static KMS_MapOption getInstanceMapOption(){
        return MapOptionInstance;
    }
    //네이버 api

    NaverMapOptions options; //초기 옵션 설정을 위한 옵션

    private double currentLatitude;
    private double currentLongitued;

    public double LastLatitude;
    public double LastLongitued;

    public NaverMapOptions setFirstOptions() { //1-8. 초기옵션 설정
        if(LastLatitude == 0.0 && LastLongitued == 0.0){
            LastLatitude = 35.857654;
            LastLongitued= 128.498123;
        }

        Log.d("@@@@@", "LastLatitude = " + LastLatitude + " LastLongitued = " + LastLongitued);
        options = new NaverMapOptions()
                .camera(new CameraPosition(new LatLng(LastLatitude, LastLongitued), 16))
                .mapType(NaverMap.MapType.Navi)
                .nightModeEnabled(false);
        Log.d("#####맵옵션",   "초기옵션");


        return options;
    } //1-8. 초기옵션 설정

    public void setFirstAddOptions(double latitude, double longitude) { //1-8. 초기옵션 설정
        currentLatitude = latitude;
        currentLongitued = longitude;
        Log.d("#####Add맵옵션",   "Add초기옵션 위경도 세팅");
    } //1-8. 초기옵션 설정

    public NaverMapOptions getFirstAddOptions() { //1-8. 초기옵션 설정
        options = new NaverMapOptions()
                .camera(new CameraPosition(new LatLng(currentLatitude, currentLongitued), 16))
                .mapType(NaverMap.MapType.Navi)
                .nightModeEnabled(false);
        Log.d("#####Add맵옵션",   "Add초기옵션 리턴");
        return options;
    }

    public void setOnLightMap(){
        KMS_MapFragment.NMap.setNightModeEnabled(false);
    }

    public void setOnNightMap(){
        KMS_MapFragment.NMap.setNightModeEnabled(true);
    }

    //1-13. UI Setting
    public void setMapUI(NaverMap naverMap) {
        // UI 컨트롤 재배치
        UiSettings uiSettings = naverMap.getUiSettings();
        //줌컨트롤ui
        uiSettings.setZoomControlEnabled(false);  //줌 컨트롤 여부

        //현재위치버튼ui
        uiSettings.setLocationButtonEnabled(false); // 기본값 : false

        //현재 내가 쓰고있는 거
        //uiSettings.setLocationButtonEnabled(true); //
        //uiSettings.isLocationButtonEnabled();

        //로고
        uiSettings.setLogoMargin(0,0,20000,100000);
       // uiSettings.
        uiSettings.setLogoGravity(Gravity.LEFT|Gravity.TOP);

        uiSettings.setLogoClickEnabled(false);

        //나침반ui
        uiSettings.setCompassEnabled(false); // 기본값 : true
        uiSettings.setScaleBarEnabled(false); // 기본값 : true


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
