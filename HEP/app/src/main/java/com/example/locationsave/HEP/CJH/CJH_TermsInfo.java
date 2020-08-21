package com.example.locationsave.HEP.CJH;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locationsave.R;

import java.io.InputStream;

public class CJH_TermsInfo extends AppCompatActivity {
    TextView title,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면 풀스크린실행
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cjh_termsinfo_activity);

        title=findViewById(R.id.terms_title);
        content=findViewById(R.id.terms_content);

        Intent intent=getIntent();
        int code = intent.getExtras().getInt("code");

        InputStream in = null;
        if(code==1){
            in = getResources().openRawResource(R.raw.service);
            title.setText(R.string.terms_service_title);
        } else if(code==2){
            in = getResources().openRawResource(R.raw.loacation);
            title.setText(R.string.terms_location_title);
        } else if(code==3){
            in = getResources().openRawResource(R.raw.personal);
            title.setText(R.string.terms_personal_title);
        } else{
            finish();
        }

        try {
            byte[] b = new byte[in.available()];
            in.read(b);
            String s =  new String(b) ;
            content.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
