package com.example.locationsave.HEP.CJH;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.locationsave.HEP.Hep.hep_FirebaseUser;
import com.example.locationsave.HEP.KMS_MainActivity;
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
    protected GoogleSignInClient mGoogleSignInClient;

    private Context context;

    public CJH_SignInGoogle(Context context, String client_id){
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

            String idToken=account.getIdToken();
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

            //구글로 전달받은 토큰을 이용하여 로그인
            new hep_FirebaseUser().getFirebaseAuthInstance().signInWithCredential(credential)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //로그인 성공시 다음으로
                                Intent intent = new Intent(context.getApplicationContext(), KMS_MainActivity.class);
                                context.startActivity(intent);
                                ((Activity) context).finish();
                            } else {
                            }
                        }
                    });
        } catch (ApiException e) {
        }
    }
    //구글 로그아웃
    public void signOutGoogle(){
        mGoogleSignInClient.signOut().addOnCompleteListener((Activity) context,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}
