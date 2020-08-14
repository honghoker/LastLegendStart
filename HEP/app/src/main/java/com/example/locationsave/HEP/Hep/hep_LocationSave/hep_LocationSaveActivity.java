package com.example.locationsave.HEP.Hep.hep_LocationSave;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Callback;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_DirectoryTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Recent;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Tag;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_LocationDetail.hep_LocationDetailActivity;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_AddLocationFragment;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_FragmentFlagManager;
import com.example.locationsave.HEP.KMS.Map.KMS_CameraManager;
import com.example.locationsave.HEP.KMS.Map.KMS_MapOption;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.HEP.pcs_RecyclerView.locationList.Pcs_LocationRecyclerView;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.HEP.KMS_MainActivity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class hep_LocationSaveActivity extends AppCompatActivity implements KMS_AddLocationFragment.OnTimePickerSetListener{

    private Pcs_LocationRecyclerView pcsFragment;

    public hep_LocationSave_AutoCompleteTextView hashEditText;
    public ViewPager viewPager;
    public hep_LocationSave_ViewPagerAdapter viewPagerAdapter;
    public ArrayList<String> tagDataArrayList;

    String addr;
    double latitude, longitude;
    int imageSizeLimit = 5; // imagepicker 최대 이미지 수

    public static final boolean LOCATION_RECYCLERVIEW_FRAGMENT = true;

    //public static FragmentManager LocationfragmentManager;
    public static Fragment LocationAddFragment = null;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    TextView locationaddrTextView;
    TextView locationnameTextView;

    KMS_CameraManager kms_cameraManager = KMS_CameraManager.getInstanceCameraManager();
    KMS_MapOption kms_mapOption = KMS_MapOption.getInstanceMapOption();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hep_locationsaveactivity);
        setinit();

        locationaddrTextView = findViewById(R.id.locationAddr);
        locationnameTextView = findViewById(R.id.locationName);
    }

    public void setinit(){
        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        addr = getIntent().getStringExtra("addr");

        if(addr == null || addr.equals(""))
            ((TextView)findViewById(R.id.locationAddr)).setText("저장된 주소가 없습니다.");
        else
            ((TextView)findViewById(R.id.locationAddr)).setText(addr);

        hashEditText = findViewById(R.id.HashTagText);
        tagDataArrayList = new ArrayList<>();
        viewPager = findViewById(R.id.viewPager);
        pcsFragment = new Pcs_LocationRecyclerView();

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
            hashTag.init(hash, "#3F729B", R.drawable.hep_locationsave_hashtagborder, params);

            ((hep_FlowLayout) findViewById(R.id.hashtagFlowLayout)).addView(hashTag);

            new hep_HashTagArr().getHashTagArr().add(hash);

            hashEditText.setText("");

            // 해시태그를 추가할 때 마다 스크롤 자동 맞춤
            View targetView = findViewById(R.id.hashtagFlowLayout);
            targetView.getParent().requestChildFocus(targetView, targetView);
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
        try {
            new GligarPicker().requestCode(pickImage).withActivity(hep_LocationSaveActivity.this).limit(imageSizeLimit).show();
        } catch (Exception e){
            e.printStackTrace();
        }
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
                    ((TextView)findViewById(R.id.locationDetailViewName)).setText(cursor.getString(0));
                    ((TextView)findViewById(R.id.locationContact)).setText(cursor.getString(1));
                    cursor.close();
                }
                break;

            case pickImage:
                if(resultCode == RESULT_OK){
                    if(new hep_locationImageDataArr().getImageDataArrayInstance().size() < 5) {
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

                            ((hep_FlowLayout) findViewById(R.id.imageFlowLayout)).removeAllViews(); // flowlayout clear
                            for (int i = 0; i < new hep_locationImageDataArr().getImageDataArrayInstance().size(); i++) {
                                hep_LocationSave_FlowLayoutImageItem flowLayoutImageItem = new hep_LocationSave_FlowLayoutImageItem(this);
                                flowLayoutImageItem.setLayoutParams(params);
                                flowLayoutImageItem.setBackgroundBitmap(new hep_locationImageDataArr().getImageDataArrayInstance().get(i).bitmap);

                                ((hep_FlowLayout) findViewById(R.id.imageFlowLayout)).addView(flowLayoutImageItem);
                            }

                            viewPagerAdapter = new hep_LocationSave_ViewPagerAdapter(this);
                            viewPager.setAdapter(viewPagerAdapter);
                            setVisibilityInformationImage();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        imageSizeLimit -= pathsList.length;
                    }
                    else{
                        toastMake("사진은 5장까지 선택할 수 있습니다.");
                    }
                }
                break;
        }
    }

    public void removeCurrentItem(){
        int position = viewPager.getCurrentItem();
        new hep_locationImageDataArr().getImageDataArrayInstance().remove(position);
        ((hep_FlowLayout)findViewById(R.id.imageFlowLayout)).removeViewAt(position);
        viewPagerAdapter.notifyDataSetChanged();
        imageSizeLimit += 1;
        setVisibilityInformationImage();
    }

    public void setVisibilityInformationImage() {
        if(viewPager.getChildCount() == 0)
            findViewById(R.id.linearlayoutInformationImageAdd).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.linearlayoutInformationImageAdd).setVisibility(View.INVISIBLE);
    }


    public void onButtonLocationSaveClicked(View v){
        if(!((EditText)findViewById(R.id.locationName)).getText().toString().trim().equals("")){

            DatabaseReference locationReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("location").push();

            hep_Location hep_Location = new hep_Location(
                    ((EditText)findViewById(R.id.locationName)).getText().toString(),
                    ((EditText)findViewById(R.id.locationAddr)).getText().toString(),
                    ((EditText)findViewById(R.id.locationDetailAddr)).getText().toString(),
                    ((EditText)findViewById(R.id.locationContact)).getText().toString(),
                    ((EditText)findViewById(R.id.locationMemo)).getText().toString(),
                    latitude,
                    longitude
            );

            Map<String, Object> hashMapLocation = hep_Location.toMap();
            locationReference.setValue(hashMapLocation);

            final String locationid = locationReference.getKey();

            for(int i = 0; i < new hep_HashTagArr().getHashTagArr().size(); i++) {
                // tag 중복 체크
                new hep_FireBase().insertTag(new hep_HashTagArr().getHashTagArr().get(i), new hep_Callback() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }

                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot, DataSnapshot dataSnapshot1) {

                    }

                    @Override
                    public void onSuccess(hep_Recent hep_recent) {

                    }

                    @Override
                    public void onSuccess(final hep_LocationTag hep_locationTag) {
                        Map<String, Object> hashlocationtag = new HashMap<>();
                        hashlocationtag.put("locationid", locationid);
                        hashlocationtag.put("tagid", hep_locationTag.tagid);

                        final String tagid = hep_locationTag.tagid;

                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationtag").push().setValue(hashlocationtag); // locationtag 저장

                        Query directorytagQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directorytag").orderByChild("directoryid").equalTo(KMS_MainActivity.directoryid);
                        directorytagQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Map<String, Object> directorytag = new HashMap<>();

                                if(snapshot.exists()) {
                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                                        hep_DirectoryTag hep_directoryTag = dataSnapshot.getValue(hep_DirectoryTag.class);

                                        if(hep_directoryTag.tagid.equals(tagid)){
                                            directorytag.put("directoryid", hep_directoryTag.directoryid);
                                            directorytag.put("tagid", hep_directoryTag.tagid);
                                            directorytag.put("count", hep_directoryTag.count + 1);

                                            new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directorytag").child(dataSnapshot.getKey()).updateChildren(directorytag);
                                        }
                                    }
                                }
                                else {
                                    directorytag.put("directoryid", KMS_MainActivity.directoryid);
                                    directorytag.put("tagid", hep_locationTag.tagid);
                                    directorytag.put("count", 1);

                                    new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directorytag").push().setValue(directorytag);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
            }

            for(int i = 0; i < new hep_locationImageDataArr().getImageDataArrayInstance().size(); i++) {
                //image storage 저장
                StorageReference LocationImagesRef = new hep_FireBase().getFirebaseStorageInstance().getReference().child("locationimages/" + UUID.randomUUID().toString()); // 랜덤 이름 생성
                Bitmap bitmap = new hep_locationImageDataArr().getImageDataArrayInstance().get(i).bitmap;
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
                        hashLocationImage.put("locationid", locationid);
                        hashLocationImage.put("imageid", imageid);
                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationimage").push().setValue(hashLocationImage);
                    }
                });
            }


            int delay;
            if(new hep_locationImageDataArr().getImageDataArrayInstance().size() > new hep_HashTagArr().getHashTagArr().size())
                delay = new hep_locationImageDataArr().getImageDataArrayInstance().size();
            else
                delay = new hep_HashTagArr().getHashTagArr().size();

            toastMake("저장 중");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toastMake("저장 완료");
                    new hep_HashTagArr().getHashTagArr().clear();
                    new hep_locationImageDataArr().getImageDataArrayInstance().clear();
                    setFragment();
                }
            },1000 * delay);

        }
        else{
            toastMake("이름을 입력해주세요");
        }
    }

    public ArrayList<String> getTagDataArrayList(){
        return tagDataArrayList;
    }

    private void setFragment(){
        setResult(RESULT_OK, new Intent().putExtra("result",LOCATION_RECYCLERVIEW_FRAGMENT));
        finish();
    }

    boolean addFragmentFlag = false;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(LocationAddFragment == null){
            toastMake("프래그먼트 생성조차 되지 않음");
            finish();
        }

        else if(addFragmentFlag == false){
            toastMake("프래그먼트 remove & flag false");
            //fragmentTransaction.remove(LocationAddFragment).commit();
            fragmentManager.beginTransaction().remove(LocationAddFragment).commit();

            //hep_LocationSaveActivity.this.getSupportFragmentManager().beginTransaction().remove(LocationAddFragment).commit();
            LocationAddFragment = null;
            finish();
        }
        else if (LocationAddFragment != null) { //프래그먼트 있을 때 누르면 숨김
            toastMake("프래그먼트 hide");
            //fragmentTransaction.hide(LocationAddFragment).commit();
            fragmentManager.beginTransaction().hide(LocationAddFragment).commit();
            hep_LocationSaveActivity.this.getSupportFragmentManager().beginTransaction().hide(LocationAddFragment).commit();
            addFragmentFlag = false;
        }
        else
            toastMake("프래그먼트 종료 오류");
    }



    public void onbtnChangeAddrClicked(View v){

/*
        KMS_MapFragment mapFragment = new KMS_MapFragment();
        hep_LocationSaveActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mapFragment).commit();
*/
        if (LocationAddFragment == null && addFragmentFlag == false) { //프래그먼트 있을 때 누르면 숨김
//            LocationAddFragment = new KMS_AddLocationFragment();

            fragmentManager = getSupportFragmentManager();
            LocationAddFragment = new KMS_AddLocationFragment();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, LocationAddFragment).commit();


            Bundle bundle = new Bundle(4); // 파라미터는 전달할 데이터 개수
            bundle.putDouble("latitude",kms_cameraManager.getCameraCurrentLatitued());
            bundle.putDouble("longitude",kms_cameraManager.getCameraCurrentlongitued());
            kms_mapOption.setFirstAddOptions(kms_cameraManager.getCameraCurrentLatitued(),kms_cameraManager.getCameraCurrentlongitued());

            bundle.putString("Title", locationnameTextView.getText().toString()); // key , value
            bundle.putString("Address",locationaddrTextView.getText().toString());
            LocationAddFragment.setArguments(bundle); //갱신?


            toastMake("프래그먼트 new");
            //fragmentTransaction.add(R.id.fragmentContainer, LocationAddFragment).commit();
            addFragmentFlag = true;


            //hep_LocationSaveActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, LocationAddFragment).commit();




        }


        else if(LocationAddFragment != null && addFragmentFlag == false){ //생성된 프레그먼트 있으면
            toastMake("프래그먼트 show");
            //fragmentTransaction.show(LocationAddFragment).commit();
            hep_LocationSaveActivity.this.getSupportFragmentManager().beginTransaction().show(LocationAddFragment).commit();
            addFragmentFlag = true;
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }


    int mHour;
    int mMin;

    @Override
    public void onTimePickerSet(int hour, int min, String s) {   //프레그먼트 데이터 받아오는 함수 오버라이드
        mHour = hour;
        mMin = min;
        Log.d("#####프레그먼트 -> 액티비티", mHour + " / " + mMin + " / int to string : " + (Integer)mMin);
        Log.d("#####프레그먼트 -> 액티비티", "스트링 값 : " + s);

    }
}
