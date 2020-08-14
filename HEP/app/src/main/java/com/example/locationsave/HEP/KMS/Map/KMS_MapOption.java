package com.example.locationsave.HEP.KMS.Map;

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

    public NaverMapOptions setFirstOptions() { //1-8. 초기옵션 설정
        options = new NaverMapOptions()
                .camera(new CameraPosition(new LatLng(35.857654, 128.498123), 16))

                .mapType(NaverMap.MapType.Navi)
                .nightModeEnabled(false);

        return options;
    } //1-8. 초기옵션 설정

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
