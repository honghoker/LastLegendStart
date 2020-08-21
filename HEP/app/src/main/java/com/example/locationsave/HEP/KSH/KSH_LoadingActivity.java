package com.example.locationsave.HEP.KSH;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;

import com.example.locationsave.R;


public class KSH_LoadingActivity extends Activity {
    ImageView imageView;
    Animation animation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksh_loading);

//        imageView = findViewById(R.id.loading_image);
//        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.loading);
//        imageView.setAnimation(animation);

        startLoading();
    }

    private void startLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);
    }
}
