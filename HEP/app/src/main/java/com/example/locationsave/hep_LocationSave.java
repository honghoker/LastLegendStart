package com.example.locationsave;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationsave.hep_DTO.hep_Location;
import com.example.locationsave.hep_DTO.hep_Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class hep_LocationSave extends AppCompatActivity {

    DatabaseReference TagReference;

    hep_AutoCompleteTextView hashEditText;
    ArrayList<hep_ImageData> imageDataArrayList;
    ViewPager viewPager;
    hep_ViewPagerAdapter viewPagerAdapter;

    ArrayList<String> tagDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hep_locationsave);
        setinit();
    }

    public void setinit(){
        hashEditText = findViewById(R.id.HashTagText);
        imageDataArrayList = new ArrayList<>();
        tagDataArrayList = new ArrayList<>();
        viewPager = findViewById(R.id.viewPager);

        TagReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("Tag");

        TagReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tagDataArrayList.clear();
                for(DataSnapshot tagSnapshot : dataSnapshot.getChildren()){
                    hep_Tag T = tagSnapshot.getValue(hep_Tag.class);
                    tagDataArrayList.add(T.name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void toastMake(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onButtonHashTagAddClicked(View v){
        hashTagAdd(hashEditText.getText().toString().trim());
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
            hep_HashTag hashTag = new hep_HashTag(this);
            hashTag.init(hash, "#3F729B", R.drawable.hep_hashtagborder, params);

            ((hep_FlowLayout) findViewById(R.id.flowLayout)).addView(hashTag);

            new hep_HashTagArr().getHashTagArr().add(hash);

            hashEditText.setText("");
        }
    }

    static final int contact = 0;
    static final int pickImage = 1;
    static final int captureImage = 2;

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

    public void onButtonContactAddOnClicked(View v){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, contact);
    }

    public void onButtonImageAddClicked(View v){
        permissionCheck();
        final CharSequence[] PhotoModels = {"갤러리", "카메라"};
        final AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setTitle("사진 가져오기");
        alt_bld.setSingleChoiceItems(PhotoModels, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    try {
                        new GligarPicker().requestCode(pickImage).withActivity(hep_LocationSave.this).limit(5).show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                } else {
                    // 카메라
                    try {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, captureImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                dialog.cancel();
            }
        });
        final AlertDialog alert = alt_bld.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case contact:
                if(resultCode == RESULT_OK){
                    Cursor cursor = getContentResolver().query(data.getData(),
                            new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
                    cursor.moveToFirst();
                    ((TextView)findViewById(R.id.locationName)).setText(cursor.getString(0));
                    ((TextView)findViewById(R.id.locationContact)).setText(cursor.getString(1));
                    cursor.close();
                }
                break;

            case pickImage:
                if(resultCode == RESULT_OK){
                    if(imageDataArrayList.size() < 5) {
                        String pathsList[] = data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT); // return list of selected images paths.

                        try {
                            for (int i = 0; i < pathsList.length; i++) {
                                try {
                                    File test = new File(pathsList[i].toString());
                                    if (test.exists()) {
                                        imageDataArrayList.add(new hep_ImageData(BitmapFactory.decodeFile(test.getAbsolutePath())));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
/*
                            LinearLayout layout = ((LinearLayout) findViewById(R.id.imageListLayout));
                            for (int i = 0; i < imageDataArrayList.size(); i++) {
                                ImageButton imageButton = (ImageButton) layout.getChildAt(i + 1);
                                Bitmap b = imageDataArrayList.get(i).bitmap;
                                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), b);
                                imageButton.setBackgroundDrawable(bitmapDrawable);
                            }*/

                            hep_FlowLayout.LayoutParams params = new hep_FlowLayout.LayoutParams(20, 20);

                            //hep_HashTag hashTag = new hep_HashTag(this);
                            //hashTag.init(hash, "#3F729B", R.drawable.hep_hashtagborder, params);
                            for (int i = 0; i < imageDataArrayList.size(); i++) {
                                ImageButton imageButton = new ImageButton(this);
                                Bitmap b = imageDataArrayList.get(i).bitmap;
                                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), b);
                                imageButton.setLayoutParams(params);
                                imageButton.setBackgroundDrawable(bitmapDrawable);
                                imageButton.setMaxWidth(60);
                                imageButton.setMaxHeight(60);
                                imageButton.setMinimumWidth(60);
                                imageButton.setMinimumHeight(60);
                                imageButton.setScaleType(ImageView.ScaleType.FIT_XY);


                                ((hep_FlowLayout) findViewById(R.id.imageFlowLayout)).addView(imageButton);
                            }


                            viewPagerAdapter = new hep_ViewPagerAdapter(this, imageDataArrayList);
                            viewPager.setAdapter(viewPagerAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        toastMake("사진은 5장까지 선택할 수 있습니다.");
                    }
                }
                break;

            case captureImage:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        if (bitmap != null) {
                            imageDataArrayList.add(new hep_ImageData(bitmap));
                            viewPagerAdapter = new hep_ViewPagerAdapter(this, imageDataArrayList);
                            viewPager.setAdapter(viewPagerAdapter);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void removeCurrentItem(){
        int position = viewPager.getCurrentItem();
        imageDataArrayList.remove(position);

        viewPagerAdapter.notifyDataSetChanged();
    }


    public void onButtonLocationSaveClicked(View v){
        if(!((EditText)findViewById(R.id.locationName)).getText().toString().trim().equals("")){
            DatabaseReference LocationReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("Locations").push();
            DatabaseReference ImageReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("LocationImages").push();

            hep_Location hep_Location = new hep_Location(((EditText)findViewById(R.id.locationName)).getText().toString(),
                    ((EditText)findViewById(R.id.locationAddr)).getText().toString(),
                    ((EditText)findViewById(R.id.locationDetailAddr)).getText().toString(),
                    ((EditText)findViewById(R.id.locationContact)).getText().toString(),
                    ((EditText)findViewById(R.id.locationMemo)).getText().toString()
                    /* 위도 경도 가져오기 */
            );

            Map<String, Object> hashMapLocation = hep_Location.toMap();

            for(int i = 0; i < new hep_HashTagArr().getHashTagArr().size(); i++) {
                hashMapLocation.put("Tag"+i, new hep_HashTagArr().getHashTagArr().get(i));
                if(!tagDataArrayList.contains(new hep_HashTagArr().getHashTagArr().get(i))){  // 태그 중복확인
                    Map<String, Object> hashMapTag = new hep_Tag(new hep_HashTagArr().getHashTagArr().get(i)).toMap();
                    TagReference.push().setValue(hashMapTag); // 태그 삽입
                }
            }

            LocationReference.setValue(hashMapLocation);

            if(!imageDataArrayList.isEmpty()){
                String key = LocationReference.getKey(); // 방금 삽입한 Location ID

                Map<String, Object> hashMapImage = new HashMap<>();
                hashMapImage.put("LocationId", key);
                for(int i = 0 ; i < imageDataArrayList.size(); i++){
                    hashMapImage.put("Image"+i, getImage64Data(imageDataArrayList.get(i).bitmap));
                }
                ImageReference.setValue(hashMapImage);
            }
        }
        else{
            toastMake("이름을 입력해주세요");
        }
    }

    public String getImage64Data(Bitmap bitmap){ // base64 encoding
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap from user image file
        bitmap.recycle();
        byte[] byteArray = bao.toByteArray();
        String imageB64 = Base64.encodeToString(byteArray, Base64.URL_SAFE);
        return imageB64;
    }

    public ArrayList<String> getTagDataArrayList(){
        return tagDataArrayList;
    }
}
