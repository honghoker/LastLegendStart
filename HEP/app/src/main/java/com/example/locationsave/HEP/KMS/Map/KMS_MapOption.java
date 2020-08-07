package com.example.locationsave.HEP.KMS.Map;

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
    //네이버 api

    NaverMapOptions options; //초기 옵션 설정을 위한 옵션

    public NaverMapOptions setFirstOptions() { //1-8. 초기옵션 설정
        options = new NaverMapOptions()
                .camera(new CameraPosition(new LatLng(35.857654, 128.498123), 16))

                .mapType(NaverMap.MapType.Navi)
                .nightModeEnabled(false);

        return options;
    } //1-8. 초기옵션 설정


    //1-13. UI Setting
    public void setMapUI(NaverMap naverMap) {
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setZoomControlEnabled(false);  //줌 컨트롤 여부
        uiSettings.setLocationButtonEnabled(true); //
        uiSettings.isLocationButtonEnabled();
       // uiSettings.
    }

    public void setMapPadding(NaverMap naverMap){
        naverMap.setContentPadding(0, 100, 0, 50);
    }

}
