package com.example.locationsave.HEP.Hep.hep_LocationUpdate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Callback;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_DirectoryTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Recent;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Tag;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_FlowLayout;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_FullImage;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_HashTagArr;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_ImageData;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_locationImageDataArr;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class hep_LocationUpdateActivity extends AppCompatActivity {
    hep_LocationUpdate_ViewpagerAdapter viewPagerAdapter;
    public ViewPager viewPager;
    hep_Location hep_Location;
    String key;
    ArrayList<String> tagDataArrayList;
    ArrayList<hep_ImageData> imagetempArrayList;
    ArrayList<String> temphashArrayList;
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
                setResult(RESULT_CANCELED);
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

        temphashArrayList = new ArrayList<>();
        temphashArrayList.addAll(new hep_HashTagArr().getHashTagArr());

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

    public void onButtonHashTagAddClicked(View v){
        hashTagAdd(((TextView) findViewById(R.id.locationupdate_locationTag)).getText().toString().trim());
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
        int delay;

        if(new hep_locationImageDataArr().getImageDataArrayInstance().size() > new hep_HashTagArr().getHashTagArr().size())
            delay = new hep_locationImageDataArr().getImageDataArrayInstance().size();
        else
            delay = new hep_HashTagArr().getHashTagArr().size();
        if(new hep_locationImageDataArr().getImageDataArrayInstance().size() == 0 && new hep_HashTagArr().getHashTagArr().size() == 0)
            delay = 3;

        firebaseImageInsert();
        firebaseTagInsert();
        firebaseLocationInsert();

        Log.d("@@@@@", "handler 생성");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("@@@@@", "handler run");
                setResult(RESULT_OK);
                finish();
            }
        },1000 * delay);
    }

    public void firebaseLocationInsert(){
        hep_Location hep_Location = new hep_Location(
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
    }

    public void firebaseTagInsert(){
        if(new hep_HashTagArr().getHashTagArr().containsAll(temphashArrayList) != temphashArrayList.containsAll(new hep_HashTagArr().getHashTagArr())){

            Query locatiotagQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationtag").orderByChild("locationid").equalTo(key);
            locatiotagQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        hep_LocationTag hep_locationTag = dataSnapshot.getValue(hep_LocationTag.class);
                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directorytag").orderByChild("tagid").equalTo(hep_locationTag.tagid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                    hep_DirectoryTag hep_directoryTag = dataSnapshot1.getValue(hep_DirectoryTag.class);
                                    if(hep_directoryTag.count == 1){
                                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directorytag").child(dataSnapshot1.getKey()).removeValue();
                                    }
                                    else
                                    {
                                        hep_directoryTag.count -= 1;
                                        Map<String, Object> hashDirectoryTag = new HashMap<>();
                                        hashDirectoryTag = hep_directoryTag.toMap();
                                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directorytag").child(dataSnapshot1.getKey()).updateChildren(hashDirectoryTag);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationtag").child(dataSnapshot.getKey()).removeValue();
                        Log.d("@@@@@", "locationtag 제거");
                    }

                    for(int i = 0; i < new hep_HashTagArr().getHashTagArr().size(); i++){
                        new hep_FireBase().insertTag(new hep_HashTagArr().getHashTagArr().get(i), new hep_Callback() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot, DataSnapshot dataSnapshot1) {

                            }

                            @Override
                            public void onSuccess(hep_Recent hep_recent) {

                            }

                            @Override
                            public void onSuccess(hep_LocationTag hep_locationTag) {
                                Map<String, Object> hashlocationtag = new HashMap<>();
                                hashlocationtag.put("locationid", key);
                                hashlocationtag.put("tagid", hep_locationTag.tagid);

                                new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationtag").push().setValue(hashlocationtag); // locationtag 저장
                                Log.d("@@@@@", "locationtag 삽입");
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

    public void firebaseImageInsert(){

        ArrayList<hep_ImageData> insertImageArr = new ArrayList<>();
        insertImageArr.addAll(new hep_locationImageDataArr().getImageDataArrayInstance());
        insertImageArr.removeAll(imagetempArrayList);
        for(int i = 0; i < insertImageArr.size(); i++){
            if(insertImageArr.get(i).bitmap != null){
                new hep_FireBase().updateImageBitmap(insertImageArr.get(i).bitmap, new hep_Callback() {
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

                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot, DataSnapshot dataSnapshot1) {

                    }

                    @Override
                    public void onSuccess(hep_Recent hep_recent) {

                    }

                    @Override
                    public void onSuccess(hep_LocationTag hep_locationTag) {

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

            new hep_FireBase().updateImageUri(key, new hep_Callback() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot dataSnapshot) {

                }

                @Override
                public void onSuccess(final DataSnapshot dataSnapshot, final DataSnapshot dataSnapshot1) {
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

                @Override
                public void onSuccess(hep_Recent hep_recent) {

                }

                @Override
                public void onSuccess(hep_LocationTag hep_locationTag) {

                }
            });
        }
        else{
            imagetempArrayList.addAll(new hep_locationImageDataArr().getImageDataArrayInstance());
        }
    }


    public void onbtnChangeAddrClicked(View v){
        KMS_MapFragment mapFragment = new KMS_MapFragment();
        hep_LocationUpdateActivity.this.getSupportFragmentManager().beginTransaction().add(R.id.locationupdate_fragmentContainer, mapFragment).commit();

//        fragmentManager.beginTransaction().show(LocationFragmet).commit();


//        hep_LocationUpdateActivity.(hep_LocationUpdateActivity.this, new KMS_MapFragment());
//        Object localObject = new Bundle();
//        ((Bundle)localObject).putString("calledFrom", "UpdateActivity");
//        ((Bundle)localObject).putString("query", v);
//        hep_LocationUpdateActivity.this.mapFragment.setArguments((Bundle)localObject);
        //hep_LocationUpdateActivity.access$1402(hep_LocationUpdateActivity.this, true);


    }

    public static class hep_LocationUpdate_ViewpagerAdapter extends PagerAdapter {
        private Context mContext;

        public hep_LocationUpdate_ViewpagerAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return new hep_locationImageDataArr().getImageDataArrayInstance().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.hep_locationsave_viewpageritem, container, false);

            PhotoView photoView = layout.findViewById(R.id.locationImage);

            Picasso.get()
                    .load(new hep_locationImageDataArr().getImageDataArrayInstance().get(position).path)
                    .fit()
                    .centerCrop()
                    .into(photoView);

            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, hep_FullImage.class);
                    intent.putExtra("CurrentItem", ((hep_LocationUpdateActivity) mContext).viewPager.getCurrentItem());
                    mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });

            ImageButton btnCloseLocationImage = layout.findViewById(R.id.btnCloseLocationImage);
            btnCloseLocationImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((hep_LocationUpdateActivity)mContext).removeCurrentItem();
                }
            });

            container.addView(layout);
            return layout;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
