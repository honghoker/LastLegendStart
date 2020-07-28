package com.example.locationsave.HEP;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locationsave.HEP.Address.GetAddress;
import com.example.locationsave.HEP.Address.ReverseGeocodingAsyncTask;
import com.example.locationsave.R;

import java.util.concurrent.ExecutionException;


public class TEST_Activity extends AppCompatActivity {
private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ksh_test);

        Toast.makeText(this,"인텐트 환영합니다.",Toast.LENGTH_SHORT).show();

        toolbar = findViewById(R.id.intent_toolbar);
        toolbar.setTitle("test");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*
        ReverseGeocodingAsyncTask asyncTask = new ReverseGeocodingAsyncTask();
        GetAddress getAddress = new GetAddress();
        try {
            getAddress.getJsonString(asyncTask.execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}


