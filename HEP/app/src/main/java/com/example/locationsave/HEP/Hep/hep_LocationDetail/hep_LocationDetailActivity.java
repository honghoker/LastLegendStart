package com.example.locationsave.HEP.Hep.hep_LocationDetail;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationImages;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_FlowLayout;
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
import java.util.ArrayList;

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
        DatabaseReference locationReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("Locations");

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

        Query imageQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("LocationImages").orderByChild("LocationId").equalTo(key);
        imageQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                new hep_locationImageDataArr().getImageDataArrayInstance().clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot issue : dataSnapshot.getChildren()){
                        hep_LocationImages hep_locationImages = issue.getValue(hep_LocationImages.class);
                        ArrayList<String> bitmapArrayList = hep_locationImages.getImageBitmapArr();
                        new hep_locationImageDataArr().setImageDataArraySize(bitmapArrayList.size());

                        for (int i = 0; i < bitmapArrayList.size(); i++) {
                            final int finalI = i;

                            StorageReference s = new hep_FireBase().getFirebaseStorageInstance().getReference().child(bitmapArrayList.get(i));
                            s.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    try {
                                        hep_LocationDetail_FlowLayoutImageItem flowLayoutImageItem = new hep_LocationDetail_FlowLayoutImageItem(hep_LocationDetailActivity.this);
                                        hep_FlowLayout.LayoutParams params = new hep_FlowLayout.LayoutParams(20, 20);
                                        flowLayoutImageItem.setLayoutParams(params);
                                        flowLayoutImageItem.setBackgroundUri(uri);

                                        new hep_locationImageDataArr().getImageDataArrayInstance().get(finalI).path = uri;
                                        ((hep_FlowLayout) findViewById(R.id.locationDetailViewimageFlowLayout)).addView(flowLayoutImageItem);
                                        //((hep_FlowLayout) findViewById(R.id.locationDetailViewimageFlowLayout)).addView(flowLayoutImageItem, finalI);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }
                    }
                    hep_LocationDetail_ViewPagerAdapter viewPagerAdapter = new hep_LocationDetail_ViewPagerAdapter(hep_LocationDetailActivity.this);
                    viewPager = findViewById(R.id.locationDetailViewPager);
                    viewPager.setAdapter(viewPagerAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
