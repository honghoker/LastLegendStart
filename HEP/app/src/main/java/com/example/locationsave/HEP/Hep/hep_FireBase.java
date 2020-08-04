package com.example.locationsave.HEP.Hep;

import androidx.annotation.NonNull;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Callback;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Recent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


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

    // 최근 지역, 디렉토리 뽑기
    public void getRecentData(final hep_Callback callback){
        String token = new hep_FirebaseUser().getFirebaseUserInstance().getUid();

        Query recentQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").orderByChild("token").equalTo(token);
        recentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    callback.onSuccess(dataSnapshot.getValue(hep_Recent.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
