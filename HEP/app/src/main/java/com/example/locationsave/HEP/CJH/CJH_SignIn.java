package com.example.locationsave.HEP.CJH;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locationsave.HEP.Hep.hep_FirebaseUser;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

public class CJH_SignIn extends AppCompatActivity implements View.OnClickListener{
    LoginButton signInFacebookOrigin;

    LinearLayout signInGoogle, signInKakao, signInNaver, signInFacebook;
    int logintype;

    protected static final int CJH_TERMS = 12500;
    protected static final int GOOGLE_SIGN_IN = 9001;
    protected static final int FACEBOOK_SIGN_IN = 64206;

    private CJH_SignInGoogle cjh_signInGoogle;
    private CJH_SignInFacebook cjh_signInFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //화면 풀스크린실행
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cjh_signin_activity);
        FacebookSdk.sdkInitialize(this);

        signInGoogle=findViewById(R.id.signInGoogle);
        signInFacebook=findViewById(R.id.signInFacebook);

        signInFacebookOrigin=findViewById(R.id.signInFacebookOrigin);

        signInGoogle.setOnClickListener(this);
        signInFacebook.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        //기존에 저장된 유저정보 있는지 확인하고 있으면 다음화면으로
        if (new hep_FirebaseUser().getFirebaseAuthInstance().getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), KMS_MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        logintype = v.getId();
        Intent intent = new Intent(getApplicationContext(), CJH_Terms.class);
        startActivityForResult(intent, CJH_TERMS);
    }

    private void signInGoogle() {
        cjh_signInGoogle = new CJH_SignInGoogle(this, getString(R.string.default_web_client_id));
        Intent signInIntent = cjh_signInGoogle.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    private void signInFacebook(){
        cjh_signInFacebook = new CJH_SignInFacebook(this,signInFacebookOrigin);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CJH_TERMS && resultCode == RESULT_OK){
            if (logintype == R.id.signInGoogle) {
                //구글로 로그인
                signInGoogle();
            } else if (logintype == R.id.signInFacebook) {
                //페이스북으로 로그인
                signInFacebook();
            }
        }

        if(requestCode==FACEBOOK_SIGN_IN){
            cjh_signInFacebook.mCallbackManager.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == GOOGLE_SIGN_IN){
            cjh_signInGoogle.signInGoogle(data);
        }
    }
}