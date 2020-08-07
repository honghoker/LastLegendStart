package com.example.locationsave.HEP.Hep.hep_LocationDetail;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Image;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationImage;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Tag;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_FlowLayout;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_ImageData;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_locationImageDataArr;
import com.example.locationsave.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class hep_LocationDetailActivity extends AppCompatActivity {

    public ViewPager viewPager;
    String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hep_locationdetailactivity);

        setData();
    }

    public void setData(){
        key = getIntent().getStringExtra("key");
        DatabaseReference locationReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("location");

        Query locationQuery = locationReference;
        locationQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot issue : dataSnapshot.getChildren()){
                        if(issue.getKey().equals(key)) {
                            hep_Location hep_Location = issue.getValue(hep_Location.class);
                            ((TextView) findViewById(R.id.locationDetailViewName)).setText(hep_Location.name);
                            ((TextView) findViewById(R.id.locationDetailViewAddr)).setText(hep_Location.addr);
                            ((TextView) findViewById(R.id.locationDetailViewDetailAddr)).setText(hep_Location.detailaddr);
                            ((TextView) findViewById(R.id.locationDetailViewContact)).setText(hep_Location.contact);
                            ((TextView) findViewById(R.id.locationDetailViewMemo)).setText(hep_Location.memo);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query locationImageQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationimage").orderByChild("locationid").equalTo(key);
        locationImageQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new hep_locationImageDataArr().getImageDataArrayInstance().clear();
                if(snapshot.exists()) {
                    for(DataSnapshot issue : snapshot.getChildren()){
                        hep_LocationImage hep_locationImage = issue.getValue(hep_LocationImage.class);

                        Query locationImageQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("image").orderByKey().equalTo(hep_locationImage.imageid);
                        locationImageQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        hep_Image hep_image = dataSnapshot.getValue(hep_Image.class);

                                        StorageReference storageReference = new hep_FireBase().getFirebaseStorageInstance().getReference().child(hep_image.path);
                                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                hep_LocationDetail_FlowLayoutImageItem flowLayoutImageItem = new hep_LocationDetail_FlowLayoutImageItem(hep_LocationDetailActivity.this);
                                                hep_FlowLayout.LayoutParams params = new hep_FlowLayout.LayoutParams(20, 20);
                                                flowLayoutImageItem.setLayoutParams(params);
                                                try {
                                                    flowLayoutImageItem.setBackgroundUri(uri);
                                                    ((hep_FlowLayout) findViewById(R.id.locationDetailViewimageFlowLayout)).addView(flowLayoutImageItem);

                                                    hep_ImageData hep_imageData = new hep_ImageData();
                                                    hep_imageData.path = uri;
                                                    new hep_locationImageDataArr().getImageDataArrayInstance().add(hep_imageData);
                                                    hep_LocationDetail_ViewPagerAdapter viewPagerAdapter = new hep_LocationDetail_ViewPagerAdapter(hep_LocationDetailActivity.this);
                                                    viewPager = findViewById(R.id.locationDetailViewPager);
                                                    viewPager.setAdapter(viewPagerAdapter);

                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query locationTagQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationtag").orderByChild("locationid").equalTo(key);
        locationTagQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    hep_LocationTag hep_locationTag = dataSnapshot.getValue(hep_LocationTag.class);

                    Query tagQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("tag").orderByKey().equalTo(hep_locationTag.tagid);
                    tagQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                hep_Tag hep_tag = dataSnapshot.getValue(hep_Tag.class);
                                ((TextView)findViewById(R.id.tagtext)).setText(((TextView)findViewById(R.id.tagtext)).getText() + ", " + hep_tag.name);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

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