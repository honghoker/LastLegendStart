package com.example.locationsave;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class hep_LocationSave extends AppCompatActivity {

    hep_AutoCompleteTextView hashEditText;
    ArrayList<hep_ImageData> imageDataArrayList;
    ViewPager viewPager;
    hep_ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hep_locationsave);
        setinit();
        permissionCheck();
    }

    public void setinit(){
        hashEditText = findViewById(R.id.HashTagText);
        imageDataArrayList = new ArrayList<>();
        viewPager = findViewById(R.id.viewPager);
    }

    public void toastMake(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onButtonHashTagAddClicked(View v){
        hashTagAdd(hashEditText.getText().toString().trim());
    }

    public void hashTagAdd(String hash){
        hep_HashTag hashTag = new hep_HashTag(this);

        if(hash.equals("")){
            toastMake("내용을 입력해주세요.");
        }
        else if(hashTag.getHashTagArr().size() >= 5){
            toastMake("태그는 5개까지 추가할 수 있습니다.");
        }
        else if(hashTag.getHashTagArr().contains(hash)){
            toastMake("이미 추가한 태그입니다.");
        }
        else{
            hep_FlowLayout.LayoutParams params = new hep_FlowLayout.LayoutParams(20, 20);
            hashTag.init(hash, "#3F729B", R.drawable.hashtagborder, params);

            ((hep_FlowLayout) findViewById(R.id.flowLayout)).addView(hashTag);

            hashTag.getHashTagArr().add(hash);

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
        final CharSequence[] PhotoModels = {"갤러리", "카메라"};
        final AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setTitle("사진 가져오기");
        alt_bld.setSingleChoiceItems(PhotoModels, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    try {
                        new GligarPicker().requestCode(pickImage).withActivity(hep_LocationSave.this).show();
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
                    String pathsList[]= data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT); // return list of selected images paths.
                    String result = "Number of selected Images: " + pathsList.length;

                    try {
                        for(int i = 0; i < pathsList.length; i++){
                            try {
                                File test = new File(pathsList[i].toString());
                                if(test.exists()){
                                    imageDataArrayList.add(new hep_ImageData(BitmapFactory.decodeFile(test.getAbsolutePath())));
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        viewPagerAdapter = new hep_ViewPagerAdapter(this, imageDataArrayList);
                        viewPager.setAdapter(viewPagerAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case captureImage:
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    if(bitmap != null) {
                        imageDataArrayList.add(new hep_ImageData(bitmap));
                        viewPagerAdapter = new hep_ViewPagerAdapter(this, imageDataArrayList);
                        viewPager.setAdapter(viewPagerAdapter);
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
}
