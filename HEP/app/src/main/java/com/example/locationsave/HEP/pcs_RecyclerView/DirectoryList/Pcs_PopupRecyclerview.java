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
    private Activity activity;
    private RecyclerView recyclerView;
    private Pcs_popupAdapter recyclerviewAdapter;
    private CapsulizeDataObjectNKey currentSelectedLocationKey;
    private View view;

    public Pcs_PopupRecyclerview(Activity activity, CapsulizeDataObjectNKey currentSelectedLocationKey){
        super(activity);
        this.activity = activity;
        this.currentSelectedLocationKey = currentSelectedLocationKey;
        setupView();

    }

    private void setupView() {
        this.view = LayoutInflater.from(activity).inflate(R.layout.pcs_directory_popupactivity, null);

        recyclerView = view.findViewById(R.id.pcs_directoryRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(activity, LinearLayoutManager.VERTICAL));

        Query query = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directory");
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<KSH_DirectoryEntity>()
                .setQuery(query, KSH_DirectoryEntity.class).build();

        recyclerviewAdapter = new Pcs_popupAdapter(options, currentSelectedLocationKey, this);
        recyclerView.setAdapter(recyclerviewAdapter);
        recyclerviewAdapter.startListening();
        setContentView(view);
    }

    public void alterDismiss(final Pcs_alterdismiss pcs_alterdismiss){
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), "Snackbar", Snackbar.LENGTH_LONG).setAction("되돌리기",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcs_alterdismiss.UndoData();
            }
        });
        snackbar.show();
        dismiss();
    }
}