package com.example.locationsave.HEP.KMS.MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.locationsave.HEP.Address.AreaSearch;
import com.example.locationsave.HEP.Address.ReverseGeocodingAsyncTask;
import com.example.locationsave.HEP.Address.ReverseGetAddress;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationFlagManager;
import com.example.locationsave.HEP.KMS.Map.KMS_CameraManager;
import com.example.locationsave.HEP.KMS.Map.KMS_MapOption;
import com.example.locationsave.HEP.KMS.Map.KMS_MarkerManager;
import com.example.locationsave.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.geometry.Utmk;
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

//프래그먼트는 액티비티위에 올라가있을떄만 프래그먼트로서 동작할 수 있다.
public class KMS_MapFragment extends Fragment implements OnMapReadyCallback {

    Activity activity; //프래그먼트

    //네이버 map 전역 변수
    public static NaverMap NMap;

    //현재 위치 받아오기
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    //맵 옵션
    KMS_MapOption mapOption = KMS_MapOption.getInstanceMapOption();
    KMS_MarkerManager markerManager = new KMS_MarkerManager().getInstanceMarkerManager();
    public static CompassView compassView;
    public static ScaleBarView scaleBarView;
    public static ZoomControlView zoomControlView;
    public static LocationButtonView locationButtonView;
    public static LogoView LogoView;

    //마지막 위치 넘기기
    KMS_CameraManager kms_cameraManager = KMS_CameraManager.getInstanceCameraManager();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //이 메소드가 호출될떄는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능
        activity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //이제 더이상 엑티비티 참초가안됨
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //프래그먼트 메인을 인플레이트해주고 컨테이너에 붙여달라는 뜻임

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.kms_map_fragment, container, false);
        compassView = rootView.findViewById(R.id.compass);
        scaleBarView = rootView.findViewById(R.id.scalebar);
        zoomControlView = rootView.findViewById(R.id.zoom);
        locationButtonView = rootView.findViewById(R.id.location);
        LogoView = rootView.findViewById(R.id.logo);
/*
        Button btn = rootView.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerManager.initMarker();
            }
        });
*/

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //현재 위치
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        //1-1. 프래그먼트 생성 시 불러오기
        FragmentManager fm = getChildFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map); //맵프래그먼트 객체 생성 map id 가져와서

        if (mapFragment == null) { //맵프래그먼트 생성된 적 없으면
            mapFragment = MapFragment.newInstance(mapOption.setFirstOptions()); //새로 만들어주고   // 1-8. 초기옵션 추가

            Toast.makeText(getContext(), "main맵 생성 완료", Toast.LENGTH_SHORT).show();
            fm.beginTransaction().add(R.id.map, mapFragment).commit(); // 프래그매니저에게 명령 map 레이아웃에 생성된 맵 객체를 add
        }
        //1-3. 맵 프래그먼트에 NaverMap 객체 가져옴 : MapFragment 및 MapView는 지도에 대한 뷰 역할만을 담당하므로 API를 호출하려면 인터페이스 역할을 하는 NaverMap 객체가 필요
        mapFragment.getMapAsync(this); //이거 만들면 onMapReady 사용 가능


        //1-5. 다른 좌표 표현방법
        Utmk utmk = new Utmk(953935.5, 1952044.1);
        LatLng latLng = utmk.toLatLng(); //형변환 가능

        LatLng latLng2 = new LatLng(37.5666103, 126.9783882);
        Utmk utmk2 = Utmk.valueOf(latLng);

        //1-6. MBR 최소 경계 사각형 (Minimum Bounding Rectangle
        //각각 남서쪽과 북동쪽 꼭지점을 의미하는 두 개의 좌표가 주어진다면 직사각형 형태의 영역을 만들 수 있습니다. 이를 최소 경계 사각형 또는 MBR
        LatLng southWest = new LatLng(31.43, 122.37);
        LatLng northEast = new LatLng(44.35, 132);
        LatLngBounds bounds = new LatLngBounds(southWest, northEast); //MBR 생성

        //1-7. 디스플레이 - 심벌크기 / 건물원근감 / 건물표시 /

        //1-8. 지도 초기 옵션 : NaverMapOptions 어플 껐을 때 마지막 위치 저장 등 해야함. 상단에 선언해준다. 78줄
        //하단 함수선언

        //1-9. 카메라 위치 선언 : cameraPostion 속성 final

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@보류
        //1-10. 카메라 이동 : 보류
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@보류
    public void setLocation(Context context) { //1-9. 카메라 위치 선언
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(35.857654, 128.498123), // 대상 지점
                //35.857654, 128.498123 그린빌
                16, // 줌 레벨
                20, // 기울임 각도
                180 // 베어링 각도
        );

        Toast.makeText(context,
                "대상 지점 위도: " + cameraPosition.target.latitude + ", " +
                        "대상 지점 경도: " + cameraPosition.target.longitude + ", " +
                        "줌 레벨: " + cameraPosition.zoom + ", " +
                        "기울임 각도: " + cameraPosition.tilt + ", " +
                        "베어링 각도: " + cameraPosition.bearing,
                Toast.LENGTH_SHORT).show();
    }//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@보류

    //1-10.카메라 현재 위치 얻기
    //CameraPosition cameraPosition = naverMap.getCameraPosition();
    public void getLocationPosition(Context context, NaverMap naverMap) {
        CameraPosition cameraPosition = NMap.getCameraPosition(); //현재 위치 정보 반환하는 메소드

        Toast.makeText(context,
                "현재위치 = 대상 지점 위도: " + cameraPosition.target.latitude + ", " +
                        "대상 지점 경도: " + cameraPosition.target.longitude + ", " +
                        "줌 레벨: " + cameraPosition.zoom + ", " +
                        "기울임 각도: " + cameraPosition.tilt + ", " +
                        "베어링 각도: " + cameraPosition.bearing,
                Toast.LENGTH_SHORT).show();
    }// getL~~종료

    public void saveLocation(Context context) {
        CameraPosition cameraPosition = NMap.getCameraPosition(); //현재 위치 정보 반환하는 메소드

        Toast.makeText(context,
                "현재위치 = 대상 지점 위도: " + cameraPosition.target.latitude + ", " +
                        "대상 지점 경도: " + cameraPosition.target.longitude, Toast.LENGTH_SHORT);
    }

    //1-11. 지도 패딩
    // NaverMap.setContentPadding()을 호출하면 콘텐츠 패딩을 지정할 수 있습니다.
    // naverMap.setContentPadding(0, 0, 0, 200);

    //1-12. 화면 좌표 <-> 지도 좌표 변환

    //1-14. 현재 위치 setLocationSource
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,  @NonNull int[] grantResults) {
        Log.d("현재위치onRequestPermissionsResult","일단 호출됨");
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isCompassEnabled()) { // 권한 거부됨
                NMap.setLocationTrackingMode(LocationTrackingMode.Face);
                Log.d("현재위치","follow");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("현재위치","super 호출");
    }

    int im = 0;

    @Override //자동으로 호출
    public void onMapReady(@NonNull NaverMap naverMap) {
//        naverMap.setMapType(NaverMap.MapType.Navi);
//        naverMap.setNightModeEnabled(true);
        NMap = naverMap; //전역에 naverMap 당겨옴
        NMap.addOnCameraIdleListener(new NaverMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                //getLocationPosition(activity, NMap);
                //saveLocation(activity);
                final AreaSearch areaSearch = new AreaSearch();
                KMS_FragmentFlagManager fragmentManager = KMS_FragmentFlagManager.getInstanceFragment();
                KMS_LocationFlagManager locationFragment = KMS_LocationFlagManager.getInstanceLocation();

                CameraPosition cameraPosition = NMap.getCameraPosition(); //현재 위치 정보 반환하는 메소드

                kms_cameraManager.setCameraCurrentPosition(cameraPosition.target.latitude,cameraPosition.target.longitude); //현재 카메라 위치 저장

                if(fragmentManager.flagCheckFragment() == true && locationFragment.flagGetLocation() == true) {

                    Log.d("MapMap", "onCameraIdle 위도 : " + cameraPosition.target.latitude + "경도 : " + cameraPosition.target.longitude + im++);

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

                Toast.makeText(getActivity(),
                        "현재위치 = 대상 지점 위도: " + cameraPosition.target.latitude + ", " +
                                "대상 지점 경도: " + cameraPosition.target.longitude, Toast.LENGTH_SHORT);
                Log.d("MapMap", "onCameraIdle 위도 : " + cameraPosition.target.latitude + "경도 : " + cameraPosition.target.longitude + im++);
            }
        });

/*
        초기값
        for(int i = 0; i < 3; i++){
            markerManager.addMarker(markerManager.markers,"그린빌" + i,35.857654 + i * 0.03, 128.498123  + i * 0.03);
        }
*/

        markerManager.loadMarker(NMap); //초기 마커값 불러옴

        mapOption.setMapPadding(NMap);  //좌표 중앙 패딩 설정
        mapOption.setMapUI(NMap); //맵 ui 설정

        //현재위치
        NMap.setLocationSource(locationSource); //얘가 있으면 버튼이 활성화
        Log.d("현재위치","onMapReady, " + locationSource);



    }//onMapReady

} //전체코드
