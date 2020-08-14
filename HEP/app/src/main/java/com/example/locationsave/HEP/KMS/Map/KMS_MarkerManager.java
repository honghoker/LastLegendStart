package com.example.locationsave.HEP.KMS.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;


import java.util.ArrayList;

import static com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment.NMap;
import static com.example.locationsave.HEP.KMS_MainActivity.kms_locationSearchResults;


public class KMS_MarkerManager {
    private static KMS_MarkerManager markerManagerInstance = null;

    //public KMS_MarkerManager() {}

    public KMS_MarkerManager getInstanceMarkerManager(){
        if(markerManagerInstance == null)
            markerManagerInstance = new KMS_MarkerManager();
        return markerManagerInstance;
    }

    KMS_CameraManager cameraManager = KMS_CameraManager.getInstanceCameraManager();
    KMS_MarkerInformation kms_markerInformation = new KMS_MarkerInformation().getInstanceMarkerInformation();
    KMS_MarkerInformationFlagManager kms_markerInformationFlagManager = KMS_MarkerInformationFlagManager.getMarkerInformationFlagManagerInstance();
    KMS_CameraManager kms_cameraManager = KMS_CameraManager.getInstanceCameraManager();
    public ArrayList<Marker> markers = new ArrayList<>(); //모든 곳에서 사용할 마커 어레이 리스트
    public ArrayList<Marker> recyclerviewMarkers = new ArrayList<>(); //모든 곳에서 사용할 마커 어레이 리스트
    //TextView textView = KMS_MainActivity.textViewMarkerInformationTitle;

    public void initMarker() { //맵의 모든 마커 삭제, 초기화
        for(Marker marker : markers){
            marker.setMap(null);
        }
        markers.clear();
        Log.d("####initMarker",   "마커 삭제 완료!!" + markers.size());
    }

    public void loadMarker(final NaverMap naverMap){

        for (int i = 0; i < 6; ++i) {

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

    public void addMarker(ArrayList<Marker> markers, String name, double latitude, double longitude){
        final Marker marker = new Marker();

        marker.setCaptionText(name);

        LatLng addMarkerLatLng = new LatLng(latitude, longitude);
        marker.setPosition(addMarkerLatLng);

        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_design_pika2));
        marker.setWidth(120);
        marker.setHeight(160);
        marker.setHideCollidedSymbols(true); //겹치는 오버레이 제거
        marker.setOnClickListener(new Overlay.OnClickListener() { //마커 클릭이벤트 추가
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                cameraManager.MoveCameraOnMarkerPosition(marker, NMap); //카메라를 마커 위치로 이동
                setOffMarkerInformation(KMS_MainActivity.linearLayoutMakerInformation);
                setOnMarkerInformation(KMS_MainActivity.linearLayoutMakerInformation);
                Log.d("####마커인포",   "#####셋 마커 에드");
                //kms_markerInformation.setMarkerInformation(marker.getCaptionText());

                //레이아웃 업데이트
                Log.d("####마커인포",   "#####셋 마커 에드 전" + kms_markerInformationFlagManager.flagGetMarkerInformationFlag() );

                new KMS_MarkerInformation().setMarkerInformation(marker.getCaptionText());
                kms_markerInformationFlagManager.flagSetTrueMarkerInformation();
                KMS_MainActivity.floatingButton.hide();

                //textView.setText(marker.getCaptionText());
                Log.d("####마커인포",   "#####셋 마커 에드 후" + kms_markerInformationFlagManager.flagGetMarkerInformationFlag() );

                /*
                //장소 삭제
                marker.setMap(null);
                markers.remove(marker);
                Log.d("####addMarker",   "남은 피카츄 수" + markers.size());
                */
                return false;
            }
        });
        markers.add(marker);
        Log.d("@@@", markers.size() + "");

        marker.setMap(NMap);
    } //add marker 종료

    public void addRecycleMarker(ArrayList<Marker> markers, String name, double latitude, double longitude){
        final Marker marker = new Marker();

        marker.setCaptionText(name);

        LatLng addMarkerLatLng = new LatLng(latitude, longitude);
        marker.setPosition(addMarkerLatLng);

        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_recycler));
        marker.setWidth(120);
        marker.setHeight(160);
        marker.setHideCollidedSymbols(true); //겹치는 오버레이 제거
        marker.setOnClickListener(new Overlay.OnClickListener() { //마커 클릭이벤트 추가
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                cameraManager.MoveCameraOnMarkerPosition(marker, NMap); //카메라를 마커 위치로 이동
                setOffMarkerInformation(KMS_MainActivity.linearLayoutMakerInformation);
                setOnMarkerInformation(KMS_MainActivity.linearLayoutMakerInformation);
                Log.d("####마커인포",   "#####셋 마커 에드");
                //kms_markerInformation.setMarkerInformation(marker.getCaptionText());

                //레이아웃 업데이트
                Log.d("####마커인포",   "#####셋 마커 에드 전" + kms_markerInformationFlagManager.flagGetMarkerInformationFlag() );

                new KMS_MarkerInformation().setMarkerInformation(marker.getCaptionText());
                kms_markerInformationFlagManager.flagSetTrueMarkerInformation();
                KMS_MainActivity.floatingButton.hide();

                //textView.setText(marker.getCaptionText());
                Log.d("####마커인포",   "#####셋 마커 에드 후" + kms_markerInformationFlagManager.flagGetMarkerInformationFlag() );

                /*
                //장소 삭제
                marker.setMap(null);
                markers.remove(marker);
                Log.d("####addMarker",   "남은 피카츄 수" + markers.size());
                */
                return false;
            }
        });
        markers.add(marker);
        Log.d("@@@", markers.size() + "");

        marker.setMap(NMap);
    } //add marker 종료


    public void setMarkerInformation(){
            if (KMS_MainActivity.linearLayoutMakerInformation.getVisibility() == View.GONE) {  //만약 셀렉트 로케이션이 보이지 않으면
                Log.d("####KMS_SelectLocation","SetResultRecyclerLayout 보임");
                //Toast.makeText(context, "검색 바 / 서브 툴바 출력", Toast.LENGTH_SHORT).show();
                KMS_MainActivity.linearLayoutMakerInformation.setVisibility(View.VISIBLE);
            } else if (KMS_MainActivity.linearLayoutMakerInformation.getVisibility() == View.VISIBLE) {
                Log.d("####KMS_SelectLocation","SetResultRecyclerLayout 숨김");
                //Toast.makeText(context, "검색 바 / 서브 툴바 미출력", Toast.LENGTH_SHORT).show();
                KMS_MainActivity.linearLayoutMakerInformation.setVisibility(View.GONE);
            }
    }

    public void initRecyclerMarker() { //맵의 모든 마커 삭제, 초기화
        for(Marker marker : recyclerviewMarkers){
            marker.setMap(null);
        }
        recyclerviewMarkers.clear();
        Log.d("####initMarker",   "마커 삭제 완료!!" + recyclerviewMarkers.size());
    }

    public void AddRecyclerViewMarker(){
        for(int i = 0; i < kms_locationSearchResults.size(); i++){
            String title = kms_locationSearchResults.get(i).getTitle();
            double latitude = kms_locationSearchResults.get(i).getLatitude();
            double longitude = kms_locationSearchResults.get(i).getLongitude();
            addRecycleMarker(recyclerviewMarkers, title, latitude, longitude);

            if(i == 0){ //만약 첫 값이 있다면 카메라 이동해줌
                LatLng firstLatLng = new LatLng(latitude, longitude);
                kms_cameraManager.MoveCameraOnLatlngPosition(firstLatLng, NMap);
            }
        }
    }

    public void setOffMarkerInformation(LinearLayout linearLayout){
        if (linearLayout.getVisibility() == View.VISIBLE) {
            Log.d("####KMS_SelectLocation","SetResultRecyclerLayout 숨김");
            //Toast.makeText(context, "검색 바 / 서브 툴바 미출력", Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.GONE);
        }
    }

    public void setOnMarkerInformation(LinearLayout linearLayout) {
        if (linearLayout.getVisibility() == View.GONE) {  //만약 셀렉트 로케이션이 보이지 않으면
            Log.d("####KMS_SelectLocation", "SetResultRecyclerLayout 보임");
            //Toast.makeText(context, "검색 바 / 서브 툴바 출력", Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.VISIBLE);
        }
    }
    // 마커들 위치 정의 (대충 1km 간격 동서남북 방향으로 만개씩, 총 4만개)
/*    public static void InitPosition(Vector<LatLng> markersPosition){
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
    } //init
    */


}
