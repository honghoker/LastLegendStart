package com.example.locationsave.HEP.Hep;

import android.net.Uri;
import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.MultiFactor;
import com.google.firebase.auth.MultiFactorInfo;
import com.google.firebase.auth.UserInfo;

import java.util.List;

public class hep_FirebaseUser {
    private static FirebaseUser firebaseUser = null;

    public FirebaseUser getFirebaseUserInstance(){
        if(firebaseUser == null) {
            firebaseUser = new FirebaseUser() {
                @NonNull
                @Override
                public String getUid() {
                    return "1";
                }

                @NonNull
                @Override
                public String getProviderId() {
                    return null;
                }

                @Override
                public boolean isAnonymous() {
                    return false;
                }

                @Nullable
                @Override
                public List<String> zza() {
                    return null;
                }

                @NonNull
                @Override
                public List<? extends UserInfo> getProviderData() {
                    return null;
                }

                @NonNull
                @Override
                public FirebaseUser zza(@NonNull List<? extends UserInfo> list) {
                    return null;
                }

                @Override
                public FirebaseUser zzb() {
                    return null;
                }

                @NonNull
                @Override
                public FirebaseApp zzc() {
                    return null;
                }

                @Nullable
                @Override
                public String getDisplayName() {
                    return null;
                }

                @Nullable
                @Override
                public Uri getPhotoUrl() {
                    return null;
                }

                @Nullable
                @Override
                public String getEmail() {
                    return null;
                }

                @Nullable
                @Override
                public String getPhoneNumber() {
                    return null;
                }

                @Nullable
                @Override
                public String zzd() {
                    return null;
                }

                @NonNull
                @Override
                public zzff zze() {
                    return null;
                }

                @Override
                public void zza(@NonNull zzff zzff) {

                }

                @NonNull
                @Override
                public String zzf() {
                    return null;
                }

                @NonNull
                @Override
                public String zzg() {
                    return null;
                }

                @Nullable
                @Override
                public FirebaseUserMetadata getMetadata() {
                    return null;
                }

                @NonNull
                @Override
                public MultiFactor getMultiFactor() {
                    return null;
                }

                @Override
                public void zzb(List<MultiFactorInfo> list) {

                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {

                }

                @Override
                public boolean isEmailVerified() {
                    return false;
                }
            };
        }
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser){
        this.firebaseUser = firebaseUser;
    }
}
