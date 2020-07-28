package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class Next extends AppCompatActivity implements View.OnClickListener{
    Button signOut,exit;
    TextView firebaseUID,userName,userPhonenumber,userEmail;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        signOut=findViewById(R.id.signOut);
        exit=findViewById(R.id.exit);
        firebaseUID=findViewById(R.id.firebaseUID);
        userName=findViewById(R.id.userName);
        userPhonenumber=findViewById(R.id.userPhonenumber);
        userEmail=findViewById(R.id.userEmail);

        signOut.setOnClickListener(this);
        exit.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();

        firebaseUID.setText(getString(R.string.firebase_uid, user.getUid()));
        userName.setText(getString(R.string.user_name, user.getDisplayName()));
        userPhonenumber.setText(getString(R.string.user_phonenumber, user.getPhoneNumber()));
        userEmail.setText(getString(R.string.user_email, user.getEmail()));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signOut) {
            //파이어베이스 로그아웃
            mAuth.signOut();

            //구글 로그아웃
            new CJH_SignInGoogle(this,getString(R.string.default_web_client_id)).signOutGoogle();
            //페이스북 로그아웃
            new CJH_SignInFacebook().signOutFacebook();

            //로그인 화면으로 되돌아감
            Intent intent = new Intent(this.getApplicationContext(),MainActivity.class);
            this.startActivity(intent);

            finish();
        }else if (i==R.id.exit){
            //앱 종료
            moveTaskToBack(true);
            finishAndRemoveTask();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}