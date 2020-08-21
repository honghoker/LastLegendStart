package com.example.locationsave.HEP.CJH;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locationsave.R;

public class CJH_Terms extends AppCompatActivity implements View.OnClickListener{
    TextView buttonService,buttonPersonal,buttonLocation;
    CheckBox checkBoxService,checkBoxPersonal,checkBoxLocation;
    Button agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면 풀스크린실행
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cjh_terms_activity);
        //상단바 숨기기
        getSupportActionBar().hide();

        checkBoxLocation = findViewById(R.id.checkbox_location);
        checkBoxPersonal = findViewById(R.id.checkbox_prosonal);
        checkBoxService = findViewById(R.id.checkbox_service);

        buttonLocation = findViewById(R.id.button_location);
        buttonPersonal = findViewById(R.id.button_prosonal);
        buttonService = findViewById(R.id.button_service);

        ((CheckBox)findViewById(R.id.checkbox_location)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ((CheckBox)findViewById(R.id.checkbox_all)).setChecked(getAllCheck());
            }
        });

        ((CheckBox)findViewById(R.id.checkbox_service)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ((CheckBox)findViewById(R.id.checkbox_all)).setChecked(getAllCheck());
            }
        });

        ((CheckBox)findViewById(R.id.checkbox_prosonal)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ((CheckBox)findViewById(R.id.checkbox_all)).setChecked(getAllCheck());
            }
        });

        ((CheckBox)findViewById(R.id.checkbox_all)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ((CheckBox)findViewById(R.id.checkbox_location)).setChecked(b);
                ((CheckBox)findViewById(R.id.checkbox_service)).setChecked(b);
                ((CheckBox)findViewById(R.id.checkbox_prosonal)).setChecked(b);
            }
        });

        agree=findViewById(R.id.button_agree);

        buttonLocation.setOnClickListener(this);
        buttonPersonal.setOnClickListener(this);
        buttonService.setOnClickListener(this);

        agree.setOnClickListener(this);
    }

    private boolean getAllCheck(){
        if(((CheckBox)findViewById(R.id.checkbox_service)).isChecked() && ((CheckBox)findViewById(R.id.checkbox_location)).isChecked() && ((CheckBox)findViewById(R.id.checkbox_prosonal)).isChecked()) return true;
        else return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.button_location){
            Intent intent = new Intent(getApplicationContext(), CJH_TermsInfo.class);
            intent.putExtra("code",2);
            startActivity(intent);
        }else if(i == R.id.button_prosonal){
            Intent intent = new Intent(getApplicationContext(), CJH_TermsInfo.class);
            intent.putExtra("code",3);
            startActivity(intent);
        }else if(i == R.id.button_service){
            Intent intent = new Intent(getApplicationContext(), CJH_TermsInfo.class);
            intent.putExtra("code",1);
            startActivity(intent);
        }else  if(i == R.id.button_agree) {
            if (checkBoxService.isChecked() && checkBoxPersonal.isChecked() && checkBoxLocation.isChecked()) {
                /*
                다음 화면으로 이동시키며 DB에 파이어베이스 UID기록 등을 기록
                 */
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "모든 약관에 동의하셔야 합니다.", Toast.LENGTH_SHORT).show();
                /*
                어떠한 이동이나 DB기록 금지
                 */
            }
        }
    }
}