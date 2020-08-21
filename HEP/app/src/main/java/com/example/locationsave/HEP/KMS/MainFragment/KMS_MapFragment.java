package com.example.locationsave.HEP.KMS.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.locationsave.HEP.Address.ReverseGeocodingAsyncTask;
import com.example.locationsave.HEP.Address.ReverseGetAddress;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationFlagManager;
import com.example.locationsave.HEP.KMS.Map.KMS_CameraManager;
import com.example.locationsave.HEP.KMS.Map.KMS_MapOption;
import com.example.locationsave.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.CompassView;
import com.naver.maps.map.widget.LocationButtonView;
import com.naver.maps.map.widget.LogoView;
import com.naver.maps.map.widget.ScaleBarView;
import com.naver.maps.map.widget.ZoomControlView;

import java.util.concurrent.ExecutionException;

public class KMS_MapFragment extends Fragment implements OnMapReadyCallback {

    Activity activity;

    public static NaverMap NMap;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    KMS_MapOption mapOption = KMS_MapOption.getInstanceMapOption();
    public static CompassView compassView;
    public static ScaleBarView scaleBarView;
    public static ZoomControlView zoomControlView;
    public static LocationButtonView locationButtonView;
    public static LogoView LogoView;

    KMS_CameraManager kms_cameraManager = KMS_CameraManager.getInstanceCameraManager();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.kms_map_fragment, container, false);
        compassView = rootView.findViewById(R.id.compass);
        scaleBarView = rootView.findViewById(R.id.scalebar);
        zoomControlView = rootView.findViewById(R.id.zoom);
        locationButtonView = rootView.findViewById(R.id.location);
        LogoView = rootView.findViewById(R.id.logo);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        FragmentManager fm = getChildFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(mapOption.setFirstOptions());
            Toast.makeText(getContext(), "main맵 생성 완료", Toast.LENGTH_SHORT).show();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    public void setLocation(Context context) {
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(35.857654, 128.498123), // 대상 지점
                16, // 줌 레벨
                20, // 기울임 각도
                180 // 베어링 각도
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isCompassEnabled()) {
                NMap.setLocationTrackingMode(LocationTrackingMode.Face);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    int im = 0;

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        NMap = naverMap;
        NMap.addOnCameraIdleListener(new NaverMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                KMS_FragmentFlagManager fragmentManager = KMS_FragmentFlagManager.getInstanceFragment();
                KMS_LocationFlagManager locationFragment = KMS_LocationFlagManager.getInstanceLocation();

                CameraPosition cameraPosition = NMap.getCameraPosition(); //현재 위치 정보 반환하는 메소드

                kms_cameraManager.setCameraCurrentPosition(cameraPosition.target.latitude,cameraPosition.target.longitude); //현재 카메라 위치 저장

                if(fragmentManager.flagCheckFragment() == true && locationFragment.flagGetLocation() == true) {

                    ReverseGeocodingAsyncTask asyncTask = new ReverseGeocodingAsyncTask(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    ReverseGetAddress reverseGetAddress = new ReverseGetAddress();
                    try {
                        String resultAddr = reverseGetAddress.getJsonString(asyncTask.execute().get());
                        ((TextView)activity.findViewById(R.id.selectLocation_AddressInfo)).setText(resultAddr);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mapOption.setMapPadding(NMap);
        mapOption.setMapUI(NMap);
        NMap.setLocationSource(locationSource);
    }
}