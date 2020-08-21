package com.example.locationsave.HEP.Hep;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Callback;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_DirectoryTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationImage;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Recent;
import com.example.locationsave.HEP.KSH.KSH_Date;
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.example.locationsave.HEP.pcs_RecyclerView.locationList.Pcs_Callback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.locationsave.HEP.KMS_MainActivity.directoryid;


public class hep_FireBase {
    private static FirebaseDatabase firebaseDatabase = null;
    private static FirebaseStorage firebaseStorage = null;

    public FirebaseDatabase getFireBaseDatabaseInstance(){
        if(firebaseDatabase == null)
            firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase;
    }

    public FirebaseStorage getFirebaseStorageInstance(){
        if(firebaseStorage == null)
            firebaseStorage = FirebaseStorage.getInstance();
        return firebaseStorage;
    }

    // 이미지 업데이트 bitmap(hep_locationupdateactivity)
    public void updateImageBitmap(Bitmap bitmap, final hep_Callback hep_callback){
        StorageReference LocationImagesRef = new hep_FireBase().getFirebaseStorageInstance().getReference().child("locationimages/" + UUID.randomUUID().toString()); // 랜덤 이름 생성
        Bitmap b = bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = LocationImagesRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                hep_callback.onSuccess(taskSnapshot);
            }
        });
    }

    // 이미지 업데이트 Uri(hep_locationupdateactivity)
    public void updateImageUri(String key, final hep_Callback hep_callback){
        Query locationImageQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("locationimage").orderByChild("locationid").equalTo(key);
        locationImageQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(final DataSnapshot dataSnapshot : snapshot.getChildren()){
                    hep_LocationImage hep_locationImage = dataSnapshot.getValue(hep_LocationImage.class);
                    DatabaseReference imageRef = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("image").child(hep_locationImage.imageid);
                    imageRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(final DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                hep_callback.onSuccess(dataSnapshot, dataSnapshot1);
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


    // 태그 가져오기(Pcsc_Re)
    public void getTag(Pcs_Callback pcs_callback){

    }

    // 태그 저장(hep_LocationSaveActivity)
    public void insertTag(final String tag, final hep_Callback callback){
        Query tagQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("tag").orderByChild("name").equalTo(tag);
        tagQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final hep_LocationTag hep_locationTag = new hep_LocationTag();

                if(snapshot.exists()){ // tag가 있으면 getkey
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        hep_locationTag.tagid = dataSnapshot.getKey();
                }
                else{  // tag가 없으면 tag 삽입 후 getkey
                    Map<String, Object> hashMapTag = new HashMap<>();
                    hashMapTag.put("name", tag);
                    DatabaseReference tagReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("tag").push();
                    tagReference.setValue(hashMapTag); // tag 저장
                    hep_locationTag.tagid = tagReference.getKey();
                }

                Query directorytagQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directorytag").orderByChild("tagid").equalTo(hep_locationTag.tagid);
                directorytagQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Object> hashDirectoryTag = new HashMap<>();
                        boolean flag = false;

                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                hep_DirectoryTag hep_directoryTag = dataSnapshot.getValue(hep_DirectoryTag.class);
                                if (hep_directoryTag.directoryid.equals(directoryid)) {
                                    hashDirectoryTag.put("directoryid", hep_directoryTag.directoryid);
                                    hashDirectoryTag.put("tagid", hep_directoryTag.tagid);
                                    hashDirectoryTag.put("count", hep_directoryTag.count + 1);

                                    new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directorytag").child(dataSnapshot.getKey()).updateChildren(hashDirectoryTag);
                                    flag = true;
                                }
                            }
                        }

                        if(!flag){
                            hashDirectoryTag.put("directoryid", directoryid);
                            hashDirectoryTag.put("tagid", hep_locationTag.tagid);
                            hashDirectoryTag.put("count", 1);
                            new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directorytag").push().setValue(hashDirectoryTag);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                callback.onSuccess(hep_locationTag);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // 최근 지역, 디렉토리 뽑기(KMS_MainActivity)
    public void getRecentData(final hep_Callback callback){
        String token = new hep_FirebaseUser().getFirebaseUserInstance().getUid();

        Query recentQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").orderByChild("token").equalTo(token);
        recentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        callback.onSuccess(dataSnapshot.getValue(hep_Recent.class));
                    }
                }
                else{
                    Query directoryQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directory").orderByChild("token").equalTo(new hep_FirebaseUser().getFirebaseUserInstance().getUid());
                    directoryQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            hep_Recent hep_recent = new hep_Recent();
                            if(snapshot.exists()){
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    hep_recent.directoryid = dataSnapshot.getKey();
                                }
                            }
                            else{
                                KSH_DirectoryEntity ksh_directoryEntity = new KSH_DirectoryEntity("test", new KSH_Date().nowDate(), new KSH_Date().nowDate());
                                DatabaseReference databaseReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directory").push();
                                databaseReference.setValue(ksh_directoryEntity);
                                hep_recent.directoryid = databaseReference.getKey();
                            }
                            callback.onSuccess(hep_recent);
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
