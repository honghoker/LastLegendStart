package com.example.locationsave.HEP.KMS.BackPressed;
import android.app.Activity;
import android.widget.Toast;

public class KMS_BackPressedForFinish {
    private long backKeyPressedTime = 0;
    private long TIME_INTERVAL = 2000;
    private Toast toast;
    private Activity activity;

    public KMS_BackPressedForFinish(Activity _activity) {
        this.activity = _activity;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + TIME_INTERVAL) {
            backKeyPressedTime = System.currentTimeMillis();
            showMessage();
        }else{
            toast.cancel();
            activity.finish();
        }
    }

    public void showMessage() {
        toast = Toast.makeText(activity, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}