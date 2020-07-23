package com.example.locationsave.HEP.Hep;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationImages;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Tag;
import com.example.locationsave.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class hep_LocationDetailView extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hep_locationdetailview);

        setData();
    }

    public void setData(){
        DatabaseReference locationReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("Locations");

        Query locationQuery = locationReference;
        locationQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot issue : dataSnapshot.getChildren()){
                        if(issue.getKey().equals("-MCuWRryVDq3ZFjsm8JM")) {
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

        Query imageQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("LocationImages").orderByChild("LocationId").equalTo("-MCuWRryVDq3ZFjsm8JM");
        imageQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("LENGH@@@@@@@@@@@@@@T", "" + dataSnapshot.getChildrenCount());
                if(dataSnapshot.exists()){
                    for(DataSnapshot issue : dataSnapshot.getChildren()){
                        hep_LocationImages hep_locationImages = issue.getValue(hep_LocationImages.class);
                        Log.d("IMAGE0@@@@@@@", "" + hep_locationImages.Image0);
                        ArrayList<Bitmap> bitmapArrayList = hep_locationImages.getImageBitmapArr();

                        hep_FlowLayout.LayoutParams params = new hep_FlowLayout.LayoutParams(20, 20);

                        for (int i = 0; i < bitmapArrayList.size(); i++) {
                            hep_FlowLayoutImageItem flowLayoutImageItem = new hep_FlowLayoutImageItem(getApplicationContext());
                            flowLayoutImageItem.setLayoutParams(params);
                            flowLayoutImageItem.setBackground(bitmapArrayList.get(i));

                            ((hep_FlowLayout) findViewById(R.id.imageFlowLayout)).addView(flowLayoutImageItem);
                        }
                        hep_ViewPagerAdapter viewPagerAdapter = new hep_ViewPagerAdapter(getApplicationContext());
                        ViewPager viewPager = findViewById(R.id.locationDetailViewViewPager);
                        viewPager.setAdapter(viewPagerAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
