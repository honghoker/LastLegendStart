package com.example.locationsave.HEP.Hep.hep_LocationSave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

@SuppressLint("AppCompatCustomView")
public class hep_LocationSave_AutoCompleteTextView extends AutoCompleteTextView {

    public hep_LocationSave_AutoCompleteTextView(Context context) {
        super(context);
    }

    public hep_LocationSave_AutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {

    }

    @Override
    public void onFilterComplete(int count) {

    }
}
