package com.example.locationsave.HEP.KSH.NavIntent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.locationsave.R;


public class KSH_HelpIntent extends AppCompatActivity{
    private Toolbar toolbar;
    DrawerLayout drawerLayout;
    TextView helpBug;
    TextView helpReview;
    Context mcontext;
    TextView location;
    TextView personal;
    TextView service;

    public void init(){
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.intent_toolbar);
        toolbar.setTitle("도움말");
        setSupportActionBar(toolbar);
        helpBug = findViewById(R.id.help_bug);
        helpReview = findViewById(R.id.help_reView);
        location = findViewById(R.id.help_location);
        personal = findViewById(R.id.help_personal);
        service = findViewById(R.id.help_service);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksh_intent_help);
        init();

        mcontext = this;
        // 뒤로가기 버튼 생성
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helpBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("6","버그 클릭");
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setTitle("버그 및 문제신고").setMessage("버그 및 일어난 문제를 적어주세요");
                final EditText editText = new EditText(mcontext);
                editText.setSingleLine(true);
                builder.setView(editText);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("6","버그 확인 "+editText.getText().toString());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        helpReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("6","리뷰 클릭");
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setTitle("리뷰").setMessage("리뷰를 작성해주세요");
                final EditText editText = new EditText(mcontext);
                editText.setSingleLine(true);
                builder.setView(editText);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("6","리뷰 확인 "+editText.getText().toString());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),KSH_TermsInfo.class);
                intent.putExtra("code",2);
                startActivity(intent);
            }
        });
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),KSH_TermsInfo.class);
                intent.putExtra("code",3);
                startActivity(intent);
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),KSH_TermsInfo.class);
                intent.putExtra("code",1);
                startActivity(intent);
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
