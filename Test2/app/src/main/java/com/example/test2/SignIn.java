package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.facebook.login.widget.LoginButton;

public class SignIn extends AppCompatActivity implements View.OnClickListener{
    LoginButton signInFacebookOrigin;

    LinearLayout signInGoogle,signInKakao, signInNaver,signInFacebook;


    protected static final int GOOGLE_SIGN_IN = 9001;
    protected static final int FACEBOOK_SIGN_IN = 64206;

    private CJH_SignInGoogle cjh_signInGoogle;
    private CJH_SignInFacebook cjh_signInFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면 풀스크린실행
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        //상단바 숨기기
        getSupportActionBar().hide();

        signInGoogle=findViewById(R.id.signInGoogle);
        signInFacebook=findViewById(R.id.signInFacebook);
       /* signInKakao=findViewById(R.id.signInKakao);
        signInNaver=findViewById(R.id.signInNaver);*/

        signInFacebookOrigin=findViewById(R.id.signInFacebookOrigin);

        signInGoogle.setOnClickListener(this);
        /*signInKakao.setOnClickListener(this);
        signInNaver.setOnClickListener(this);*/
        signInFacebook.setOnClickListener(this);
        //파이어베이스 객체
        CJH_UserInfo.reflash();
    }

    @Override
    public void onStart() {
        super.onStart();
        //기존에 저장된 유저정보 있는지 확인하고 있으면 다음화면으로

        if (CJH_UserInfo.user != null) {
            Intent intent = new Intent(getApplicationContext(), Next.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signInGoogle) {
            //구글로 로그인
            signInGoogle();
        }else if (i == R.id.signInFacebook) {
            //페이스북으로 로그인
            signInFacebook();
        }
        /*else if (i == R.id.signInKakao) {

        }else if (i == R.id.signInNaver) {

        }*/
    }

    private void signInGoogle() {
        cjh_signInGoogle=new CJH_SignInGoogle(this,getString(R.string.default_web_client_id));
        Intent signInIntent = cjh_signInGoogle.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void signInFacebook(){
        cjh_signInFacebook=new CJH_SignInFacebook(this,signInFacebookOrigin);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("resultCode", Integer.toString(resultCode));
        Log.d("requestCode", Integer.toString(requestCode));

        if(requestCode==FACEBOOK_SIGN_IN){
            cjh_signInFacebook.mCallbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode==GOOGLE_SIGN_IN){
            cjh_signInGoogle.signInGoogle(data);
        }
    }
}