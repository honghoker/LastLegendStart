package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;


import com.example.locationsave.HEP.pcs_RecyclerView.locationList.CapsulizeDataObjectNKey;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.Query;

public class Pcs_PopupRecyclerview extends PopupWindow {
    private Context context;
    private RecyclerView recyclerView;
    private Pcs_popupAdapter recyclerviewAdapter;
    private CapsulizeDataObjectNKey currentSelectedLocationKey;
    private View view;
    public Pcs_PopupRecyclerview(Activity parentActivity, CapsulizeDataObjectNKey currentSelectedLocationKey){
        super(parentActivity.getApplicationContext());
        this.context = context;
        this.currentSelectedLocationKey = currentSelectedLocationKey;
        setupView();

    }

    private void setupView() {
        this.view = LayoutInflater.from(context).inflate(R.layout.pcs_directory_popupactivity, null);
        recyclerView = view.findViewById(R.id.pcs_directoryRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        Query query = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directory");
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<KSH_DirectoryEntity>()
                .setQuery(query, KSH_DirectoryEntity.class).build();

        recyclerviewAdapter = new Pcs_popupAdapter(options, currentSelectedLocationKey);
        recyclerView.setAdapter(recyclerviewAdapter);
        recyclerviewAdapter.startListening();
        setContentView(view);
    }

    @Override
    public void dismiss() {

        Snackbar snackbar = Snackbar.make(view, "Snackbar", Snackbar.LENGTH_LONG);
        snackbar.show();
        super.dismiss();
    }
}