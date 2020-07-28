package com.example.locationsave.HEP.Hep;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


public class hep_FireBase {
    private static FirebaseDatabase firebaseDatabase = null;
    private static FirebaseStorage firebaseStorage = null;

    public FirebaseDatabase getFireBaseDatabaseInstance(){
        if(firebaseDatabase == null)
            firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase;
    }

    public FirebaseStorage getFirebaseStorageInstance(){
        if(firebaseStorage == null)
            firebaseStorage = FirebaseStorage.getInstance();
        return firebaseStorage;
    }


}
