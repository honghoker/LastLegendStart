package com.example.locationsave.HEP.KMS.Map;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;

import java.util.ArrayList;
import java.util.Vector;

import static com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment.REFERANCE_LAT;
import static com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment.REFERANCE_LNG;


public class KMS_MarkerManager {
    private static final KMS_MarkerManager markerManagerInstance = new KMS_MarkerManager();

    private KMS_MarkerManager() {}

    public static KMS_MarkerManager getInstanceMarkerManager(){

        return markerManagerInstance;
    }

    KMS_CameraManager cameraManager = KMS_CameraManager.getInstanceCameraManager();

    public ArrayList<Marker> markers = new ArrayList<>(); //모든 곳에서 사용할 마커 어레이 리스트

    public void initMarker() { //맵의 모든 마커 삭제, 초기화
        for(Marker marker : markers){
            marker.setMap(null);
            markers.remove(marker);
        }
        Log.d("####initMarker",   "마커 삭제 완료!!" + markers.size());

    }

    public void loadMarker(final NaverMap naverMap){

        for (int i = 0; i < 6; ++i) {
            final Marker marker = new Marker();
            LatLng addMarkerLatLng = new LatLng(35.857654 + i*0.005 , 128.498123 + i*0.005);

            marker.setPosition(addMarkerLatLng);


            marker.setIcon(OverlayImage.fromResource(R.drawable.marker_design_pika2));
            marker.setCaptionText("피카씨발" + i);
            marker.setWidth(120);
            marker.setHeight(160);
            marker.setOnClickListener(new Overlay.OnClickListener() { //마커 클릭이벤트 추가
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    cameraManager.MoveCameraOnMarkerPosition(marker, KMS_MapFragment.NMap); //카메라를 마커 위치로 이동

                    Log.d("####addMarker",   "피카츄 클릭!!" + marker.getCaptionText() + " " + marker.getPosition());

                    marker.setMap(null);
                    markers.remove(marker);
                    Log.d("####addMarker",   "남은 피카츄 수" + markers.size());
                    return false;
                }
            });

            markers.add(marker);
        }
        for (Marker marker : markers) {
            marker.setMap(naverMap);
        }
        //앱 시작시 호출해야함.
    }
    // 디비에서 1. 장소 타이틀 가져옴 2. 위경도 값 가져와서 3. 주소로 변경 4.  마커 출력

    public void deleteMarker() {} //장소 삭제 시 마커도 삭제

    /*
    1. init - load 식으로 갱신할 것인지
    2. 개별로 업데이트 시킬 것인지.
     */

    public void saveMaker(){} //장소 저장 시 추가된 장소의 타이틀을 마커에 삽입, 위경도 값 주소로 변경하여 마커 삽입

    public void addMarker(){
        /*
        1. 맵에서 장소추가한 경우
        2. 옮길 때 마다 위경도 변경, 장소명 변경됨
        3. 저장 시 제목과 좌표 저장됨
        4. 이 때, 제목과 좌표를 addMarker 에 넘겨주며 갱신
        5. 이러면 load 랑 관계없어짐 /
         */
        NaverMap NMap = KMS_MapFragment.NMap;
        CameraPosition cameraPosition = NMap.getCameraPosition(); //현재 위치 정보 반환하는 메소드
        LatLng addMarkerLatLng = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
        //현재 장소 위경도값 받아와서 좌표 추가
        Marker marker = new Marker();
        marker.setPosition(addMarkerLatLng);

        //마커 텍스트(캡션)
        marker.setCaptionText("향후 위경도 기반 주소나 지정 이름 올 것");
        marker.setCaptionRequestedWidth(200); //이름 최대 폭
        marker.setCaptionAligns(Align.Top); //텍스트를 마커 상단 배치
        marker.setCaptionTextSize(16); //마커의 텍스트 사이즈
        //마커 캡션 보이는 영역
        marker.setCaptionMinZoom(12); //12 부터
        marker.setCaptionMaxZoom(16); //16 줌 사이에서만 캡션 보임

        //마커 이미지
        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_design_pika2));
        marker.setWidth(120);
        marker.setHeight(160);

        //마커 이미지 , 심벌 겹칠 경우
        marker.setHideCollidedSymbols(true); //뒤 심벌이 사라짐 ( 대구광역시청)
        marker.setOnClickListener(new Overlay.OnClickListener() { //마커 클릭이벤트 추가
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Log.d("####addMarker","피카츄 클릭!!");
                return false;
            }
        });

        marker.setMap(NMap);
        Log.d("####addMarker","장소 추가");
    }

    // 마커들 위치 정의 (대충 1km 간격 동서남북 방향으로 만개씩, 총 4만개)

    public static void InitPosition(Vector<LatLng> markersPosition){
        NaverMap NMap = KMS_MapFragment.NMap;
        // 카메라 초기 위치 설정
        LatLng initialPosition = new LatLng(37.506855, 127.066242);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        NMap.moveCamera(cameraUpdate);

    //1. markerPosition 에 기존 가진 마커들을 모두 추가한다.
    markersPosition = new Vector<LatLng>();
        for (int x = 0; x < 100; ++x) {
        for (int y = 0; y < 100; ++y) {
            markersPosition.add(new LatLng(
                    initialPosition.latitude - (REFERANCE_LAT * x),
                    initialPosition.longitude + (REFERANCE_LNG * y)
            ));
            markersPosition.add(new LatLng(
                    initialPosition.latitude + (REFERANCE_LAT * x),
                    initialPosition.longitude - (REFERANCE_LNG * y)
            ));
            markersPosition.add(new LatLng(
                    initialPosition.latitude + (REFERANCE_LAT * x),
                    initialPosition.longitude + (REFERANCE_LNG * y)
            ));
            markersPosition.add(new LatLng(
                    initialPosition.latitude - (REFERANCE_LAT * x),
                    initialPosition.longitude - (REFERANCE_LNG * y)
            ));
        }
    }
    }
}
