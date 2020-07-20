package com.example.location;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KSH_FireBase {
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    private KSH_FireBase(){
        firebaseDatabase = FirebaseDatabase.getInstance();  // firebase db 연동
        databaseReference = firebaseDatabase.getReference().child("Test");  // db table 연결
    }

    private static class DbHolder{
        private static final KSH_FireBase instance = new KSH_FireBase();
    }

    public static KSH_FireBase getInstance(Context context){
        return DbHolder.instance;
    }
}
