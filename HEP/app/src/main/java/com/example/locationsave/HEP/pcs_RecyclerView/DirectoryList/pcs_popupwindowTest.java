package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

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
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

public class pcs_popupwindowTest extends PopupWindow {
    private Context context;
    private RecyclerView recyclerView;
    private Pcs_DicRecyclerviewAdapter recyclerviewAdapter;

    public pcs_popupwindowTest(Context context) {
        super(context);
        this.context = context;
        setupView();
    }

    private void setupView() {
        View view = LayoutInflater.from(context).inflate(R.layout.pcs_directory_popupactivity, null);
        recyclerView = view.findViewById(R.id.pcs_directoryRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        Query query = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("directory");
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<KSH_DirectoryEntity>()
                .setQuery(query, KSH_DirectoryEntity.class)
                .build();
        if(options.getSnapshots() == null)
            Log.d("tag", "directory options is empty");

        recyclerviewAdapter = new Pcs_DicRecyclerviewAdapter(options);
        recyclerView.setAdapter(recyclerviewAdapter);
        setContentView(view);
    }
}
