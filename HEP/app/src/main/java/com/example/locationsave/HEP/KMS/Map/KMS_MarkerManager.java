package com.example.locationsave.HEP.KMS.Map;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationImage;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.KMS.Location.KMS_LocationSearchResult;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import static com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment.NMap;
import static com.example.locationsave.HEP.KMS_MainActivity.kms_locationSearchResults;
import static com.example.locationsave.HEP.KMS_MainActivity.mainContext;


public class KMS_MarkerManager {
    private static KMS_MarkerManager markerManagerInstance = null;

    public KMS_MarkerManager getInstanceMarkerManager(){
        if(markerManagerInstance == null)
            markerManagerInstance = new KMS_MarkerManager();
        return markerManagerInstance;
    }

    KMS_CameraManager cameraManager = KMS_CameraManager.getInstanceCameraManager();
    KMS_MarkerInformationFlagManager kms_markerInformationFlagManager = KMS_MarkerInformationFlagManager.getMarkerInformationFlagManagerInstance();
    KMS_CameraManager kms_cameraManager = KMS_CameraManager.getInstanceCameraManager();
    public ArrayList<Marker> markers = new ArrayList<>();
    public ArrayList<Marker> recyclerviewMarkers = new ArrayList<>();

    public void initMarker() {
        for(Marker marker : markers){
            marker.setMap(null);
        }
        markers.clear();
    }

    public void addRecycleMarker(final ArrayList<Marker> markers, String name, double latitude, double longitude, final NaverMap naverMap){
        final Marker marker = new Marker();

        marker.setCaptionText(name);

        LatLng addMarkerLatLng = new LatLng(latitude, longitude);
        marker.setPosition(addMarkerLatLng);

        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_recycler));
        marker.setWidth(120);
        marker.setHeight(160);
        marker.setHideCollidedSymbols(true);
        marker.setHideCollidedCaptions(true);
        marker.setHideCollidedMarkers(true);

        marker.setOnClickListener(new Overlay.OnClickListener() { //마커 클릭이벤트 추가
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                cameraManager.MoveCameraOnMarkerPosition(marker, naverMap); //카메라를 마커 위치로 이동
                setMarkerLank();

                return false;
            }
        });
        markers.add(marker);
        marker.setMap(naverMap);
    }

    public void setMarkerLank(){
        int i = 0;
        for(Marker marker : recyclerviewMarkers){
            if(marker == recyclerviewMarkers.get(i)){
            }
            i++;
        }
    }

    public void initRecyclerMarker() {
        for(Marker marker : recyclerviewMarkers){
            marker.setMap(null);
        }
        recyclerviewMarkers.clear();
    }

    public void AddRecyclerViewMarker(NaverMap naverMap){
        for(int i = 0; i < kms_locationSearchResults.size(); i++){
            String title = kms_locationSearchResults.get(i).getTitle();
            double latitude = kms_locationSearchResults.get(i).getLatitude();
            double longitude = kms_locationSearchResults.get(i).getLongitude();
            addRecycleMarker(recyclerviewMarkers, title, latitude, longitude, naverMap);

            if(i == 0){
                kms_cameraManager.MoveCameraOnLatlngPosition(latitude, longitude, naverMap);
            }
        }
    }

    public void AddUpdateRecyclerViewMarker(NaverMap naverMap, ArrayList<KMS_LocationSearchResult> kms_locationUpdateResults){
        for(int i = 0; i < kms_locationUpdateResults.size(); i++){
            String title = kms_locationUpdateResults.get(i).getTitle();
            double latitude = kms_locationUpdateResults.get(i).getLatitude();
            double longitude = kms_locationUpdateResults.get(i).getLongitude();
            addRecycleMarker(recyclerviewMarkers, title, latitude, longitude, naverMap);

            if(i == 0){
                kms_cameraManager.MoveCameraOnLatlngPosition(latitude, longitude, naverMap);
            }
        }
    }



    public void setOffMarkerInformation(LinearLayout linearLayout){
        if (linearLayout.getVisibility() == View.VISIBLE) {
            linearLayout.setVisibility(View.GONE);
        }
    }

    public void setOnMarkerInformation(LinearLayout linearLayout) {
        if (linearLayout.getVisibility() == View.GONE) {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    public void addMarker(ArrayList<Marker> markers, final hep_Location hep_location, final String key) {
        final Marker marker = new Marker();

        marker.setCaptionText(hep_location.name);

        LatLng addMarkerLatLng = new LatLng(hep_location.latitude, hep_location.longitude);
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

                new KMS_MarkerInformation().setMarkerInformation(marker.getCaptionText());
                kms_markerInformationFlagManager.flagSetTrueMarkerInformation();
                KMS_MainActivity.floatingButton.hide();

                ((TextView)((KMS_MainActivity)mainContext).findViewById(R.id.titleTextView)).setText(hep_location.name);
                ((TextView)((KMS_MainActivity)mainContext).findViewById(R.id.addressTextView)).setText(hep_location.addr);
                ((TextView)((KMS_MainActivity)mainContext).findViewById(R.id.detailaddressTextView)).setText(hep_location.detailaddr);

                new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationimage").orderByChild("locationid").equalTo(key).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final ImageView imageView = ((KMS_MainActivity)mainContext).findViewById(R.id.imageView8);
                        if(snapshot.exists()){
                            for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                hep_LocationImage hep_locationImage = dataSnapshot1.getValue(hep_LocationImage.class);
                                new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("image").child(hep_locationImage.imageid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot dataSnapshot2 : snapshot.getChildren()){
                                            String path = dataSnapshot2.getValue().toString();
                                            StorageReference storageReference = new hep_FireBase().getFirebaseStorageInstance().getReference().child(path);
                                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Picasso.get()
                                                            .load(uri)
                                                            .fit()
                                                            .centerCrop()
                                                            .into(imageView);
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                        else{
                            imageView.setImageResource(android.R.color.transparent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return false;
            }
        });
        markers.add(marker);
        marker.setMap(NMap);
    }
}