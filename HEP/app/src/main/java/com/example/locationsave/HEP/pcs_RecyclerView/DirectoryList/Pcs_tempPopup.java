package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.pcs_RecyclerView.locationList.Pcs_RecyclerviewAdapter;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;


public class Pcs_tempPopup extends PopupWindow {
    private Context context;
    private RecyclerView rvCategory;
    private Pcs_DicRecyclerviewAdapter adapter;
    public Pcs_tempPopup(Context context){
        super(context);
        this.context = context;
        setupView();
    }
    public Pcs_tempPopup(Context context, Pcs_DicRecyclerviewAdapter adapter){
        super(context);
        this.context = context;
        this.adapter = adapter;
        setupView();
    }


    private void setupView() {
        View view = LayoutInflater.from(context).inflate(R.layout.pcs_temp_recyclerview, null);

        rvCategory = view.findViewById(R.id.tempRecyclerview);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        rvCategory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        rvCategory.setAdapter(adapter);

        setContentView(view);
    }

}
