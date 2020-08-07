package com.example.locationsave.HEP.Hep;

import androidx.annotation.NonNull;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Callback;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Recent;
import com.example.locationsave.HEP.KSH.KSH_Date;
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;


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

    // 태그 저장(hep_LocationSaveActivity)
    public void insertTag(final String tag, final hep_Callback callback){
        Query tagQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("tag").orderByChild("name").equalTo(tag);
        tagQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hep_LocationTag hep_locationTag = new hep_LocationTag();

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
                    directoryQuery.addValueEventListener(new ValueEventListener() {
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
