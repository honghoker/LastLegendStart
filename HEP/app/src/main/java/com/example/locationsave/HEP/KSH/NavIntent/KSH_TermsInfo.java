package com.example.locationsave.HEP.KSH.NavIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.locationsave.R;

import java.io.InputStream;

public class KSH_TermsInfo extends AppCompatActivity {
    TextView title, content;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ksh_terms_info);
        toolbar = findViewById(R.id.intent_toolbar);
        toolbar.setTitle("도움말");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                this.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
