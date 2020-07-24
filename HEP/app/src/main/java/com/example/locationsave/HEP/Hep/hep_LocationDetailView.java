package com.example.locationsave.HEP.Hep;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
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
                        if(issue.getKey().equals("-MCziMy1HJgVDyctVTB1")) {
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

        Query imageQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("LocationImages").orderByChild("LocationId").equalTo("-MCziMy1HJgVDyctVTB1");
        imageQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot issue : dataSnapshot.getChildren()){
                        hep_LocationImages hep_locationImages = issue.getValue(hep_LocationImages.class);
                        ArrayList<String> bitmapArrayList = hep_locationImages.getImageBitmapArr();

                        for (int i = 0; i < bitmapArrayList.size(); i++) {
                            StorageReference s = new hep_FireBase().getFirebaseStorageInstance().getReference().child(bitmapArrayList.get(i));

                            s.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.d("@@@@@ URI : ", uri.toString());
                                    try {
                                        hep_FlowLayoutImageItem flowLayoutImageItem = new hep_FlowLayoutImageItem(getApplicationContext());
                                        hep_FlowLayout.LayoutParams params = new hep_FlowLayout.LayoutParams(20, 20);
                                        flowLayoutImageItem.setLayoutParams(params);

                                        flowLayoutImageItem.setBackgroundUri(uri);

                                        ((hep_FlowLayout) findViewById(R.id.locationDetailViewimageFlowLayout)).addView(flowLayoutImageItem);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
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
