package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Next extends AppCompatActivity implements View.OnClickListener{
    Button signOut,exit;
    TextView firebaseUID,userName,userType,userEmail;
    ImageView userPhoto,signOutIamge;
    LinearLayout signOut2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        signOut=findViewById(R.id.signOut);
        exit=findViewById(R.id.exit);
        firebaseUID=findViewById(R.id.firebaseUID);
        userName=findViewById(R.id.userName);
        userType=findViewById(R.id.userType);
        userEmail=findViewById(R.id.userEmail);
        userPhoto=findViewById(R.id.userPhoto);

        signOut.setOnClickListener(this);
        exit.setOnClickListener(this);

        CJH_UserInfo.reflash();

        Log.d("userInfo: ",CJH_UserInfo.getEmail().toString());
        Log.d("userInfo: ",CJH_UserInfo.getLoginType());
        Log.d("userInfo: ",CJH_UserInfo.getName().toString());
        Log.d("userInfo: ",CJH_UserInfo.getPhoneNumber().toString());
        Log.d("userInfo: ",CJH_UserInfo.getUid());

        firebaseUID.setText(getString(R.string.firebase_uid, CJH_UserInfo.getUid()));
        userName.setText(getString(R.string.user_name, CJH_UserInfo.getName()));
        userType.setText(getString(R.string.user_logintype,CJH_UserInfo.getLoginType()));
        userEmail.setText(getString(R.string.user_email,CJH_UserInfo.getEmail()));
        CJH_UserInfo.setPhoto(userPhoto,this);

        signOut2=findViewById(R.id.signOut2);
        signOutIamge=findViewById(R.id.signOutImage);
        if(CJH_UserInfo.getLoginType()=="google"){
            signOut2.setBackgroundColor(Color.rgb(Integer.parseInt("cf",16),Integer.parseInt("43",16),Integer.parseInt("33",16)));//cf4333
            signOutIamge.setImageDrawable(getResources().getDrawable(R.drawable.btn_logo_google));
        }else if(CJH_UserInfo.getLoginType()=="facebook"){
            signOut2.setBackgroundColor(Color.rgb(Integer.parseInt("41",16),Integer.parseInt("6b",16),Integer.parseInt("c1",16)));//#416BC1
            signOutIamge.setImageDrawable(getResources().getDrawable(R.drawable.btn_logo_facebook));
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signOut) {
            CJH_UserInfo.logOut(this);
            finish();
        }else if (i==R.id.exit){
            //앱 종료
            CJH_UserInfo.exit(this);
        }
    }
}