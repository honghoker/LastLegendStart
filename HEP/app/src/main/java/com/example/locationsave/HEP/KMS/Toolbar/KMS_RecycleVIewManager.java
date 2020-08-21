package com.example.locationsave.HEP.KMS.Toolbar;

public class KMS_RecycleVIewManager {
    private static final KMS_RecycleVIewManager recyclevIewInstance = new KMS_RecycleVIewManager(); //정적 변수에 인스턴스를 만들어 바로 초기화

    private KMS_RecycleVIewManager() {}

    public static KMS_RecycleVIewManager getInstanceRecycleView(){
        return recyclevIewInstance;
    }

    boolean recycleViewFlag = false;

    public boolean flagCheckRecycleView(){
        return recycleViewFlag;
    }

    public void flagSetTrueRecycleView(){
        recycleViewFlag = true;
    }

    public void flagSetFalseRecycleView(){
        recycleViewFlag = false;
    }
}