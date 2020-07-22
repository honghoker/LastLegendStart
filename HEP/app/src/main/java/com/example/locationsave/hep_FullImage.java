package com.example.locationsave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class hep_FullImage extends AppCompatActivity {
    hep_viewPagerFixed viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hep_fullimageviewpager);
        viewPager = findViewById(R.id.fullImageViewPager);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        hep_FullImageViewPagerAdapter adapter = new hep_FullImageViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(new Intent(this.getIntent()).getIntExtra("CurrentItem", 0));
    }

    public void onButtonCloseFullImageOnclicked(View v){
        finish();
    }

}