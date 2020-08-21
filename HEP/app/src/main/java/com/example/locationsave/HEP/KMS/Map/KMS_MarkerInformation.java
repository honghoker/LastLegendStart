package com.example.locationsave.HEP.KMS.Map;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.locationsave.HEP.KMS_MainActivity;

public class KMS_MarkerInformation {
    private static KMS_MarkerInformation markerInformationInstance = null;

    TextView textView = KMS_MainActivity.textViewMarkerInformationTitle;

    public KMS_MarkerInformation getInstanceMarkerInformation(){
        if(markerInformationInstance == null)
            markerInformationInstance = new KMS_MarkerInformation();
        return markerInformationInstance;
    }

    public void setMarkerInformation(String Title){
        textView.setText(Title);
    }
}