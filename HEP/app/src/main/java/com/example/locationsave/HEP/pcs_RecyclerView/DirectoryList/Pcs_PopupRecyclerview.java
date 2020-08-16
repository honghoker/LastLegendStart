package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

import android.app.DownloadManager;
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
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.Query;

public class Pcs_PopupRecyclerview extends PopupWindow {
    private Context context;
    private RecyclerView recyclerView;
    private Pcs_popupAdapter recyclerviewAdapter;
    private String currentKeyOfDirectory;
    private hep_Location hep_location;
    private Button confirmButton;
    private Button cancelButton;
    public Pcs_PopupRecyclerview(Context context, hep_Location hep_location){
        super(context);
        this.context = context;
        this.hep_location = hep_location;
        setupView();
    }

    private void setupView() {
        View view = LayoutInflater.from(context).inflate(R.layout.pcs_directory_popupactivity, null);
        setButton(view);
        recyclerView = view.findViewById(R.id.pcs_directoryRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        Query query = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directory");
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<KSH_DirectoryEntity>()
                .setQuery(query, KSH_DirectoryEntity.class).build();

        recyclerviewAdapter = new Pcs_popupAdapter(options, hep_location);
        recyclerView.setAdapter(recyclerviewAdapter);
        recyclerviewAdapter.startListening();
        setContentView(view);
    }
    private void setButton(View view){
        confirmButton = view.findViewById(R.id.directory_confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cancelButton = view.findViewById(R.id.directory_cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}