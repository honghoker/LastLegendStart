package com.example.locationsave.HEP.CJH;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.locationsave.HEP.Hep.hep_FirebaseUser;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;

public class CJH_SignInFacebook {
    private final String TAG_FACEBOOK = "Facebook";

    private Context context;

    protected CallbackManager mCallbackManager;
    private FacebookCallback facebookCallback;

    public CJH_SignInFacebook(){

    };

    public CJH_SignInFacebook(Context context, LoginButton signInFacebookOrigin){
        this.context = context;

        //콜백 생성
        mCallbackManager = CallbackManager.Factory.create();

        //콜백 연결
        facebookCallback=new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG_FACEBOOK, "facebook:onSuccess:" + loginResult);
                signInFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG_FACEBOOK, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG_FACEBOOK, "facebook:onError", error);
            }
        };

        //콜백을 페이스북 버튼에 연결함
        signInFacebookOrigin.registerCallback(mCallbackManager,facebookCallback);
        signInFacebookOrigin.setReadPermissions("email", "public_profile");
        //페이스북 버튼을 누르는 이벤트 실행
        signInFacebookOrigin.performClick();
    }

    //페이스북 로그인
    private void signInFacebook(AccessToken token) {

        //페이스북의 토큰을 받아 로그인 실행
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        new hep_FirebaseUser().getFirebaseAuthInstance().signInWithCredential(credential)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(context.getApplicationContext(), KMS_MainActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        } else {
                            Toast.makeText(context, "로그인 실패",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //페이스북 로그아웃
    public void signOutFacebook(){
        LoginManager.getInstance().logOut();
        return;
    }
}
