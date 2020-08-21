package com.example.locationsave.HEP.Hep;

import android.content.Intent;

import com.example.locationsave.HEP.CJH.CJH_SignIn;
import com.example.locationsave.HEP.CJH.CJH_SignInFacebook;
import com.example.locationsave.HEP.CJH.CJH_SignInGoogle;
import com.example.locationsave.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        if (firebaseAuth == null || firebaseUser == null) {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
//            firebaseUser = new FirebaseUser() {
//                @NonNull
//                @Override
//                public String getUid() {
//                    return "1";
//                }
//
//                @NonNull
//                @Override
//                public String getProviderId() {
//                    return null;
//                }
//
//                @Override
//                public boolean isAnonymous() {
//                    return false;
//                }
//
//                @Nullable
//                @Override
//                public List<String> zza() {
//                    return null;
//                }
//
//                @NonNull
//                @Override
//                public List<? extends UserInfo> getProviderData() {
//                    return null;
//                }
//
//                @NonNull
//                @Override
//                public FirebaseUser zza(@NonNull List<? extends UserInfo> list) {
//                    return null;
//                }
//
//                @Override
//                public FirebaseUser zzb() {
//                    return null;
//                }
//
//                @NonNull
//                @Override
//                public FirebaseApp zzc() {
//                    return null;
//                }
//
//                @Nullable
//                @Override
//                public String getDisplayName() {
//                    return null;
//                }
//
//                @Nullable
//                @Override
//                public Uri getPhotoUrl() {
//                    return null;
//                }
//
//                @Nullable
//                @Override
//                public String getEmail() {
//                    return null;
//                }
//
//                @Nullable
//                @Override
//                public String getPhoneNumber() {
//                    return null;
//                }
//
//                @Nullable
//                @Override
//                public String zzd() {
//                    return null;
//                }
//
//                @NonNull
//                @Override
//                public zzff zze() {
//                    return null;
//                }
//
//                @Override
//                public void zza(@NonNull zzff zzff) {
//
//                }
//
//                @NonNull
//                @Override
//                public String zzf() {
//                    return null;
//                }
//
//                @NonNull
//                @Override
//                public String zzg() {
//                    return null;
//                }
//
//                @Nullable
//                @Override
//                public FirebaseUserMetadata getMetadata() {
//                    return null;
//                }
//
//                @NonNull
//                @Override
//                public MultiFactor getMultiFactor() {
//                    return null;
//                }
//
//                @Override
//                public void zzb(List<MultiFactorInfo> list) {
//
//                }
//
//                @Override
//                public void writeToParcel(Parcel dest, int flags) {
//
//                }
//
//                @Override
//                public boolean isEmailVerified() {
//                    return false;
//                }
//            };
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
