package com.example.locationsave.HEP.KMS.HashTag;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class KMS_HashTagCheckBoxFlagManager extends AppCompatActivity {
    private KMS_HashTag[] kms_hashTags;

    public KMS_HashTagCheckBoxFlagManager(KMS_HashTag[] kms_hashTags) {
        this.kms_hashTags = kms_hashTags;
    }

    boolean hashTagCheckBoxFlag = false;

    public boolean flagGethashTagCheckBoxFlag(){
        return hashTagCheckBoxFlag;
    }

    public void flagSetFalsehashTagCheckBoxFlag(){
        hashTagCheckBoxFlag = false;
    }

    public static List<String> hashTagText = new ArrayList();

    public static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }
}