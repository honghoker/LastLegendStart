package com.example.locationsave.HEP.Hep.hep_LocationUpdate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationImage;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Tag;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_FlowLayout;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_HashTagArr;
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
import com.google.firebase.storage.UploadTask;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class hep_LocationUpdateActivity extends AppCompatActivity {
    hep_LocationUpdate_ViewpagerAdapter viewPagerAdapter;
    public ViewPager viewPager;
    hep_Location hep_Location;
    String key;
    ArrayList<String> tagDataArrayList;
    ArrayList<hep_ImageData> imagetempArrayList;
    int imageSizeLimit = 5;
    double latitude, longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hep_locationupdateactivity);

        setInit();
        setData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setInit(){
        key = getIntent().getStringExtra("key");
        hep_Location = getIntent().getParcelableExtra("hep_Location");
        tagDataArrayList = new ArrayList<>();
        imagetempArrayList = new ArrayList<>();
        imagetempArrayList.addAll(new hep_locationImageDataArr().getImageDataArrayInstance());

        Toolbar toolbar = findViewById(R.id.locationupdate_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void setData(){
        ((TextView) findViewById(R.id.locationupdate_locationName)).setText(hep_Location.name);
        ((TextView) findViewById(R.id.locationupdate_locationAddr)).setText(hep_Location.addr);
        ((TextView) findViewById(R.id.locationupdate_locationDetailAddr)).setText(hep_Location.detailaddr);
        ((TextView) findViewById(R.id.locationupdate_locationContact)).setText(hep_Location.contact);
        ((TextView) findViewById(R.id.locationupdate_locationMemo)).setText(hep_Location.memo);

        hep_FlowLayout.LayoutParams params = new hep_FlowLayout.LayoutParams(20, 20);
        for (int i = 0; i < new hep_locationImageDataArr().getImageDataArrayInstance().size(); i++) {
            try {
                hep_LocationUpdate_FlowLayoutImageItem flowLayoutImageItem = new hep_LocationUpdate_FlowLayoutImageItem(this);
                flowLayoutImageItem.setLayoutParams(params);

                flowLayoutImageItem.setBackgroundUri(new hep_locationImageDataArr().getImageDataArrayInstance().get(i).path);
                ((hep_FlowLayout) findViewById(R.id.locationupdate_imageFlowLayout)).addView(flowLayoutImageItem);

                viewPagerAdapter = new hep_LocationUpdate_ViewpagerAdapter(this);
                viewPager = findViewById(R.id.locationupdate_viewPager);
                viewPager.setAdapter(viewPagerAdapter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageSizeLimit -= new hep_locationImageDataArr().getImageDataArrayInstance().size();

        for(String tag : new hep_HashTagArr().getHashTagArr()){
            hep_LocationUpdate_HashTag hashTag = new hep_LocationUpdate_HashTag(hep_LocationUpdateActivity.this);
            hashTag.init(tag, "#3F729B", R.drawable.hep_locationsave_hashtagborder, params);

            ((hep_FlowLayout) findViewById(R.id.locationupdate_hashtagFlowLayout)).addView(hashTag);
        }


        DatabaseReference duplicationTagReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("tag");
        duplicationTagReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tagDataArrayList.clear();
                for(DataSnapshot tagSnapshot : dataSnapshot.getChildren()){
                    hep_Tag hep_tag = tagSnapshot.getValue(hep_Tag.class);
                    tagDataArrayList.add(hep_tag.name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<String> getTagDataArrayList(){
        return tagDataArrayList;
    }

    public void toastMake(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void hashTagAdd(String hash){

        if(hash.equals("")){
            toastMake("내용을 입력해주세요.");
        }
        else if(new hep_HashTagArr().getHashTagArr().size() >= 5){
            toastMake("태그는 5개까지 추가할 수 있습니다.");
        }
        else if(new hep_HashTagArr().getHashTagArr().contains(hash)){
            toastMake("이미 추가한 태그입니다.");
        }
        else{
            hep_FlowLayout.LayoutParams params = new hep_FlowLayout.LayoutParams(20, 20);
            hep_LocationUpdate_HashTag hashTag = new hep_LocationUpdate_HashTag(this);
            hashTag.init(hash, "#3F729B", R.drawable.hep_locationsave_hashtagborder, params);

            ((hep_FlowLayout) findViewById(R.id.locationupdate_hashtagFlowLayout)).addView(hashTag);

            new hep_HashTagArr().getHashTagArr().add(hash);

            ((TextView)findViewById(R.id.HashTagText)).setText("");

            // 해시태그를 추가할 때 마다 스크롤 자동 맞춤
            View targetView = findViewById(R.id.locationupdate_hashtagFlowLayout);
            targetView.getParent().requestChildFocus(targetView, targetView);
        }
    }

    public void removeCurrentItem(){
        int position = viewPager.getCurrentItem();
        new hep_locationImageDataArr().getImageDataArrayInstance().remove(position);
        ((hep_FlowLayout)findViewById(R.id.locationupdate_imageFlowLayout)).removeViewAt(position);
        viewPagerAdapter.notifyDataSetChanged();
        imageSizeLimit += 1;
        setVisibilityInformationImage();
    }

    public void setVisibilityInformationImage() {
        if(viewPager.getChildCount() == 0)
            findViewById(R.id.locationupdate_linearlayoutInformationImageAdd).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.locationupdate_linearlayoutInformationImageAdd).setVisibility(View.INVISIBLE);
    }

    public void permissionCheck() {
        // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    static final int pickImage = 1;

    public void onButtonImageAddClicked(View v){
        permissionCheck();

        try {
            new GligarPicker().requestCode(pickImage).withActivity(hep_LocationUpdateActivity.this).limit(imageSizeLimit).show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case pickImage:
                if (resultCode == RESULT_OK) {
                    if (new hep_locationImageDataArr().getImageDataArrayInstance().size() < 5) {
                        String pathsList[] = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT); // return list of selected images paths.

                        try {
                            for (int i = 0; i < pathsList.length; i++) {
                                try {
                                    File test = new File(pathsList[i]);
                                    if (test.exists()) {
                                        new hep_locationImageDataArr().getImageDataArrayInstance().add(new hep_ImageData(BitmapFactory.decodeFile(test.getAbsolutePath()), Uri.fromFile(test)));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            hep_FlowLayout.LayoutParams params = new hep_FlowLayout.LayoutParams(20, 20);

                            ((hep_FlowLayout) findViewById(R.id.locationupdate_imageFlowLayout)).removeAllViews(); // flowlayout clear
                            for (int i = 0; i < new hep_locationImageDataArr().getImageDataArrayInstance().size(); i++) {
                                hep_LocationUpdate_FlowLayoutImageItem flowLayoutImageItem = new hep_LocationUpdate_FlowLayoutImageItem(this);
                                flowLayoutImageItem.setLayoutParams(params);

                                if(new hep_locationImageDataArr().getImageDataArrayInstance().get(i).bitmap == null)
                                    flowLayoutImageItem.setBackgroundUri(new hep_locationImageDataArr().getImageDataArrayInstance().get(i).path);
                                else
                                    flowLayoutImageItem.setBackgroundBitmap(new hep_locationImageDataArr().getImageDataArrayInstance().get(i).bitmap);

                                ((hep_FlowLayout) findViewById(R.id.locationupdate_imageFlowLayout)).addView(flowLayoutImageItem);
                            }

                            viewPagerAdapter = new hep_LocationUpdate_ViewpagerAdapter(this);
                            viewPager.setAdapter(viewPagerAdapter);
                            setVisibilityInformationImage();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        imageSizeLimit -= pathsList.length;
                    } else {
                        toastMake("사진은 5장까지 선택할 수 있습니다.");
                    }
                }
                break;
        }
    }

    public void onButtonLocationUpdateClicked(View v) {
        final hep_Location hep_Location = new hep_Location(
                ((EditText)findViewById(R.id.locationupdate_locationName)).getText().toString(),
                ((EditText)findViewById(R.id.locationupdate_locationAddr)).getText().toString(),
                ((EditText)findViewById(R.id.locationupdate_locationDetailAddr)).getText().toString(),
                ((EditText)findViewById(R.id.locationupdate_locationContact)).getText().toString(),
                ((EditText)findViewById(R.id.locationupdate_locationMemo)).getText().toString(),
                latitude,
                longitude
        );

        Map<String, Object> hashMap = hep_Location.toMap();
        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("location").child(key).setValue(hashMap);


        ArrayList<hep_ImageData> insertImageArr = new ArrayList<>();
        insertImageArr.addAll(new hep_locationImageDataArr().getImageDataArrayInstance());
        insertImageArr.removeAll(imagetempArrayList);
        for(int i = 0; i < insertImageArr.size(); i++){
            if(insertImageArr.get(i).bitmap != null){
                StorageReference LocationImagesRef = new hep_FireBase().getFirebaseStorageInstance().getReference().child("locationimages/" + UUID.randomUUID().toString()); // 랜덤 이름 생성
                Bitmap bitmap = insertImageArr.get(i).bitmap;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = LocationImagesRef.putBytes(data);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Map<String, Object> hashMapImage = new HashMap<>();
                        hashMapImage.put("path", taskSnapshot.getMetadata().getPath());
                        DatabaseReference imageReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("image").push();
                        imageReference.setValue(hashMapImage);

                        String imageid = imageReference.getKey();
                        HashMap<String, Object> hashLocationImage = new HashMap<>();
                        hashLocationImage.put("locationid", key);
                        hashLocationImage.put("imageid", imageid);
                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationimage").push().setValue(hashLocationImage);
                    }
                });
            }
        }

        imagetempArrayList.removeAll(new hep_locationImageDataArr().getImageDataArrayInstance()); // arraylist가 서로 같은지 비교
        if(!imagetempArrayList.isEmpty()){
            final ArrayList<Uri> pathList = new ArrayList<>();
            for(hep_ImageData hep_imageData : new hep_locationImageDataArr().getImageDataArrayInstance()){
                pathList.add(hep_imageData.path);
            }

            Query locationImageQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationimage").orderByChild("locationid").equalTo(key);
            locationImageQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(final DataSnapshot dataSnapshot : snapshot.getChildren()){
                        hep_LocationImage hep_locationImage = dataSnapshot.getValue(hep_LocationImage.class);
                        DatabaseReference imageRef = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("image").child(hep_locationImage.imageid);
                        imageRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(final DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                    String path = (String) dataSnapshot1.getValue();
                                    StorageReference storageReference = new hep_FireBase().getFirebaseStorageInstance().getReference().child(path);
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            if(!pathList.contains(uri)){
                                                new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationimage").child(dataSnapshot.getKey()).removeValue();
                                                new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("image").child(dataSnapshot1.getKey()).removeValue();
                                            }
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

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            imagetempArrayList.addAll(new hep_locationImageDataArr().getImageDataArrayInstance());
        }

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

//
//        for(int i = 0; i < new hep_locationImageDataArr().getImageDataArrayInstance().size(); i++) {
//            //image storage 저장
//            StorageReference LocationImagesRef = new hep_FireBase().getFirebaseStorageInstance().getReference().child("locationimages/" + UUID.randomUUID().toString()); // 랜덤 이름 생성
//            Bitmap bitmap = new hep_locationImageDataArr().getImageDataArrayInstance().get(i).bitmap;
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] data = baos.toByteArray();
//
//            UploadTask uploadTask = LocationImagesRef.putBytes(data);
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Map<String, Object> hashMapImage = new HashMap<>();
//                    hashMapImage.put("path", taskSnapshot.getMetadata().getPath());
//                    DatabaseReference imageReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("image").push();
//                    imageReference.setValue(hashMapImage);
//
//                    String imageid = imageReference.getKey();
//                    HashMap<String, Object> hashLocationImage = new HashMap<>();
//                    hashLocationImage.put("locationid", key);
//                    hashLocationImage.put("imageid", imageid);
//                    new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationimage").push().setValue(hashLocationImage);
//                }
//            });
//        }

    }
}
