package com.example.locationsave.HEP.KMS.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class KSH_LoadLocation extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setOnSearchResultRecyclerView(Context context, RecyclerView recyclerView) {
        if (recyclerView.getVisibility() == View.GONE) {  //만약 셀렉트 로케이션이 보이지 않으면
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void setOffSearchResultRecyclerView2(Context context, RecyclerView recyclerView) {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        }
    }
}
