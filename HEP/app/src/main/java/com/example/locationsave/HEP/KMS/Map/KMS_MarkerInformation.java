package com.example.locationsave.HEP.KMS.Map;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.locationsave.HEP.KMS_MainActivity;

public class KMS_MarkerInformation {
    private static KMS_MarkerInformation markerInformationInstance = null;

    LinearLayout linearLayoutMakerInformation = KMS_MainActivity.linearLayoutMakerInformation;
    TextView textView = KMS_MainActivity.textViewMarkerInformationTitle;

    public KMS_MarkerInformation getInstanceMarkerInformation(){
        if(markerInformationInstance == null)
            markerInformationInstance = new KMS_MarkerInformation();
        return markerInformationInstance;
    }

    // title, address, img
    public void setMarkerInformation(String Title){
        Log.d("####마커인포",   "#####셋 마커 인포메이션" );

        textView.setText(Title);
        Log.d("####마커인포",   "#####셋 마커 인포메이션 종료" );

    }
}
