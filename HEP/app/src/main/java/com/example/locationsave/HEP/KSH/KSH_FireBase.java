package com.example.locationsave.HEP.KSH;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KSH_FireBase {
    private static KSH_FireBase instance;
    private FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public DatabaseReference TagdatabaseReference;
    private KSH_FireBase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("directory");
        TagdatabaseReference = firebaseDatabase.getReference().child("tag");
    }

    public static KSH_FireBase getInstance(){
        if(instance == null){
            instance = new KSH_FireBase();
        }
        return instance;
    }
}
