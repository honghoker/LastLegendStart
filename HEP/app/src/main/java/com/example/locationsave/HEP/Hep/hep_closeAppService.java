package com.example.locationsave.HEP.Hep;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.map.CameraPosition;

import java.util.HashMap;

import static com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment.NMap;
import static com.example.locationsave.HEP.KMS_MainActivity.directoryid;

public class hep_closeAppService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) { //핸들링 하는 부분


        Query recentQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").orderByChild("token").equalTo(new hep_FirebaseUser().getFirebaseUserInstance().getUid());
        recentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> result = new HashMap<>();
                CameraPosition cameraPosition = NMap.getCameraPosition();
                result.put("latitude", cameraPosition.target.latitude);
                result.put("longitude", cameraPosition.target.longitude);
                result.put("directoryid", directoryid);

                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Log.d("Key : ", "" + dataSnapshot.getKey());
                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").child(dataSnapshot.getKey()).updateChildren(result);
                    }
                }
                else{
                    result.put("token", new hep_FirebaseUser().getFirebaseUserInstance().getUid());
                    new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").push().setValue(result);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.e("Error","onTaskRemoved - 강제 종료 " + rootIntent);
        Toast.makeText(this, "onTaskRemoved ", Toast.LENGTH_SHORT).show();
        stopSelf(); //서비스 종료
    }
}