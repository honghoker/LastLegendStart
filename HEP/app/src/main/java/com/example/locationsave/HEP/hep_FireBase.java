package com.example.locationsave.HEP;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class hep_FireBase {
    private static FirebaseDatabase firebaseDatabase = null;

    public FirebaseDatabase getFireBaseDatabaseInstance(){
        if(firebaseDatabase == null)
            firebaseDatabase = FirebaseDatabase.getInstance();
        return firebaseDatabase;
    }
}