package com.example.locationsave.HEP.KSH.NavIntent;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.locationsave.HEP.KMS.MainFragment.KMS_FragmentFlagManager;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.R;

import static com.example.locationsave.HEP.KMS_MainActivity.LocationFragmet;
import static com.example.locationsave.HEP.KMS_MainActivity.fragmentManager;
import static com.example.locationsave.HEP.KMS_MainActivity.mapFragment;

public class KSH_SetIntent extends AppCompatActivity {
    private Toolbar toolbar;
    DrawerLayout drawerLayout;

    public void init(){
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.intent_toolbar);
        toolbar.setTitle("설정");
        setSupportActionBar(toolbar);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksh_intent_set);
        init();

        // 뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
