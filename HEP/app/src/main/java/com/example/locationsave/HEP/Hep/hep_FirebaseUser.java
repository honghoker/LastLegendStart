package com.example.locationsave.HEP.Hep;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.locationsave.HEP.CJH.CJH_SignIn;
import com.example.locationsave.HEP.CJH.CJH_SignInFacebook;
import com.example.locationsave.HEP.CJH.CJH_SignInGoogle;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.MultiFactor;
import com.google.firebase.auth.MultiFactorInfo;
import com.google.firebase.auth.UserInfo;

import java.util.List;

import static com.example.locationsave.HEP.KMS_MainActivity.mainContext;

public class hep_FirebaseUser {
    private static FirebaseAuth firebaseAuth = null;
    private static FirebaseUser firebaseUser = null;

    public FirebaseAuth getFirebaseAuthInstance(){
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public FirebaseUser getFirebaseUserInstance() {
        if (firebaseUser == null) {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
        }
        return firebaseUser;
    }

    public void logout(){
        //파이어베이스 로그아웃
        firebaseAuth.signOut();
        //구글 로그아웃
        new CJH_SignInGoogle(mainContext , mainContext.getString(R.string.default_web_client_id)).signOutGoogle();
        //페이스북 로그아웃
        new CJH_SignInFacebook().signOutFacebook();

        //로그인 화면으로 되돌아감
        Intent intent = new Intent(mainContext.getApplicationContext(), CJH_SignIn.class);
        mainContext.startActivity(intent);
    }
}
