package com.example.locationsave.HEP.Hep;

import com.google.firebase.auth.FirebaseUser;

public class hep_FirebaseUser {
    private static FirebaseUser firebaseUser = null;

    public FirebaseUser getFirebaseUserInstance(){
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser){
        this.firebaseUser = firebaseUser;
    }
}
