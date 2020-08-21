package com.example.locationsave.HEP.KSH.NavIntent;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.locationsave.HEP.Hep.hep_FirebaseUser;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_FragmentFlagManager;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.HEP.KMS.Map.KMS_MapOption;
import com.example.locationsave.R;

import static com.example.locationsave.HEP.KMS_MainActivity.LocationFragmet;
import static com.example.locationsave.HEP.KMS_MainActivity.fragmentManager;
import static com.example.locationsave.HEP.KMS_MainActivity.mapFragment;

public class KSH_SetIntent extends AppCompatActivity {
    private Toolbar toolbar;
    DrawerLayout drawerLayout;
    TextView setThemeLight;
    TextView setThemeNight;

    public void init(){
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.intent_toolbar);
        toolbar.setTitle("설정");
        setSupportActionBar(toolbar);
    }

    public void mapOptionInit(){
        final KMS_MapOption kms_mapOption = KMS_MapOption.getInstanceMapOption();
        setThemeLight = findViewById(R.id.setThemeLight);
        setThemeLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kms_mapOption.setOnLightMap();
                //setThemeLight.setBackground(R.layout.);
                Toast.makeText(getApplicationContext(),"주간 모드로 변경되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        setThemeNight = findViewById(R.id.setThemeNight);
        setThemeNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kms_mapOption.setOnNightMap();
                Toast.makeText(getApplicationContext(),"야간 모드로 변경되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksh_intent_set);
        init();
        mapOptionInit();
        // 뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((TextView)findViewById(R.id.logout)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new hep_FirebaseUser().logout();
                return false;
            }
        });
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
