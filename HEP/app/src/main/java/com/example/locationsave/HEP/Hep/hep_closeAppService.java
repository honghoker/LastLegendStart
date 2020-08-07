package com.example.locationsave.HEP.Hep;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class hep_closeAppService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) { //핸들링 하는 부분

        Toast.makeText(this, "onTaskRemoved ", Toast.LENGTH_SHORT).show();
        stopSelf(); //서비스 종료
    }
}