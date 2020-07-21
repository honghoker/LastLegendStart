package com.example.locationsave;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class hep_FullImage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hep_fullimageviewpager);

        ViewPager viewPager = findViewById(R.id.fullImageViewPager);
        hep_FullImageViewPagerAdapter adapter = new hep_FullImageViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(new Intent(this.getIntent()).getIntExtra("CurrentItem", 1));
    }

    public void onButtonCloseFullImageOnclicked(View v){
        finish();
    }

}