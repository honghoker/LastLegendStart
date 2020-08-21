package com.example.locationsave.HEP.Hep.hep_LocationDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Image;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationImage;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_FlowLayout;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_HashTagArr;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_ImageData;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_locationImageDataArr;
import com.example.locationsave.HEP.Hep.hep_LocationUpdate.hep_LocationUpdateActivity;
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
    hep_Location hep_Location;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hep_locationdetailactivity);
        singletonArrClear();
        setInit();
        setData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.hep_locationdetail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    final int updateFlag = 3000;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.locationdetail_btnUpdate:
                Intent intent = new Intent(this, hep_LocationUpdateActivity.class);
                intent.putExtra("hep_Location", hep_Location);
                intent.putExtra("key", key);
                startActivityForResult(intent, updateFlag);
                break;
            case R.id.locationdetail_btndelete:
                singletonArrClear();
                new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("location").child(key).removeValue();
                finish();
                break;
            case android.R.id.home:
                singletonArrClear();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case updateFlag:
                if(resultCode == RESULT_OK){
                    Intent intent = new Intent(getIntent());
                    finish();
                    startActivity(intent);
                }
                else if(resultCode == RESULT_CANCELED){

                }
                break;
        }
    }

    public void singletonArrClear(){
        new hep_HashTagArr().getHashTagArr().clear();
        new hep_locationImageDataArr().getImageDataArrayInstance().clear();
    }

    public void setInit(){
        Toolbar toolbar = findViewById(R.id.locationdetailToolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                            hep_Location = issue.getValue(hep_Location.class);
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
        locationTagQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    hep_LocationTag hep_locationTag = dataSnapshot.getValue(hep_LocationTag.class);

                    Query tagQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("tag").child(hep_locationTag.tagid);
                    tagQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                String name = (String) dataSnapshot.getValue();
                                hep_FlowLayout.LayoutParams params = new hep_FlowLayout.LayoutParams(20, 0);
                                TextView textView = new TextView(hep_LocationDetailActivity.this);
                                textView.setText(name);
                                textView.setBackgroundResource(R.drawable.hep_locationsave_hashtagborder);
                                textView.setTextColor(android.graphics.Color.parseColor("#3F729B"));
                                textView.setLayoutParams(params);

                                ((hep_FlowLayout) findViewById(R.id.locationDetailhashtagFlowLayout)).addView(textView);
                                new hep_HashTagArr().getHashTagArr().add(name);
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

    public void ContactCallMessage(View v){
        if(!((TextView)findViewById(R.id.locationDetailViewContact)).getText().toString().trim().equals("")) {
            Intent intent = null;

            String Contact = ((TextView)findViewById(R.id.locationDetailViewContact)).getText().toString();

            switch (v.getId()){
                case R.id.locationDetailCall:
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Contact));
                    startActivity(intent);
                    break;

                case R.id.locationDetailMessage:
                    intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + Contact));
                    startActivity(intent);
                    break;
            }
        }
        else{
            Toast.makeText(this, "저장된 연락처가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}