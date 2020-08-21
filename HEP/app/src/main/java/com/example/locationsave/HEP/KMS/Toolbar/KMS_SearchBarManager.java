package com.example.locationsave.HEP.KMS.Toolbar;

import android.view.View;
import android.widget.RelativeLayout;

public class KMS_SearchBarManager {
    public void setOffLoadLocationSearchBar(RelativeLayout relativeLayout){
        if (relativeLayout.getVisibility() == View.VISIBLE) {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public void setOnLoadLocationSearchBar(RelativeLayout relativeLayout) {
        if (relativeLayout.getVisibility() == View.GONE) {
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }
}