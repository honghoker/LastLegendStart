package com.example.locationsave.HEP.Hep;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.locationsave.HEP.KSH.KSH_RecyAdapter;
import com.naver.maps.map.CameraPosition;

import java.util.HashMap;

import static com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment.NMap;

public class ForecdTerminationService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) { //핸들링 하는 부분
        HashMap<String, Object> result = new HashMap<>();
        CameraPosition cameraPosition = NMap.getCameraPosition();

        // oauth 추가 필요
        result.put("latitude", cameraPosition.target.latitude);
        result.put("longitude", cameraPosition.target.longitude);
        result.put("directoryid", KSH_RecyAdapter.directoryid);

        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("recent").push().setValue(result);

        Log.e("Error","onTaskRemoved - 강제 종료 " + rootIntent);
        Toast.makeText(this, "onTaskRemoved ", Toast.LENGTH_SHORT).show();
        stopSelf(); //서비스 종료
    }
}