package com.example.locationsave.HEP.KMS.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class KMS_SearchBarManager {
    public void setOffLoadLocationSearchBar(RelativeLayout relativeLayout){
        if (relativeLayout.getVisibility() == View.VISIBLE) {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public void setOnLoadLocationSearchBar(RelativeLayout relativeLayout) {
        if (relativeLayout.getVisibility() == View.GONE) {  //만약 셀렉트 로케이션이 보이지 않으면
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }
}
