package com.example.locationsave.HEP.Hep;

import com.google.firebase.database.FirebaseDatabase;

public class hep_FireBase {
    private static FirebaseDatabase firebaseDatabase = null;

    public FirebaseDatabase getFireBaseDatabaseInstance(){
        if(firebaseDatabase == null)
            firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase;
    }
}
