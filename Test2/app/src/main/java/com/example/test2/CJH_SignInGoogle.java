package com.example.test2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;

public class CJH_SignInGoogle{
    protected final String TAG_GOOGLE = "Google";

    protected GoogleSignInClient mGoogleSignInClient;

    private Context context;

    CJH_SignInGoogle(Context context, String client_id){
        this.context = context;

        //구글로그인시 전달받을것들
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(client_id)
                .requestEmail()
                .build();

        //구글 클라이언트 연결
        mGoogleSignInClient = GoogleSignIn.getClient(this.context, gso);
    }

    //구글 로그인
    protected void signInGoogle(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Log.d(TAG_GOOGLE, "firebaseAuthWithGoogle:" + account.getId());

            String idToken=account.getIdToken();
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

            //구글로 전달받은 토큰을 이용하여 로그인
            CJH_UserInfo.mAuth.signInWithCredential(credential)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG_GOOGLE, "signInWithCredential:success");

                                //로그인 성공시 다음으로

                                Intent intent = new Intent(context.getApplicationContext(), Next.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            } else {
                                Log.w(TAG_GOOGLE, "signInWithCredential:failure", task.getException());
                            }
                        }
                    });
        } catch (ApiException e) {
            Log.w(TAG_GOOGLE, "Google sign in failed", e);
        }
    }
    //구글 로그아웃
    protected void signOutGoogle(){
        mGoogleSignInClient.signOut().addOnCompleteListener((Activity) context,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}
