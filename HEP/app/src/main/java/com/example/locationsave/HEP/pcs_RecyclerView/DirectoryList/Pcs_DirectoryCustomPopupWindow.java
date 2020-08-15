package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.example.locationsave.HEP.pcs_RecyclerView.locationList.Pcs_RecyclerviewAdapter;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class Pcs_DirectoryCustomPopupWindow extends PopupWindow {
    private PopupWindow popupWindow;
    private RecyclerView recyclerView;
    private Pcs_DicRecyclerviewAdapter adapter;
    private Button cancelButton;
    private Button confirmButton;
    private View popupView;
    private Context context;
    private DatabaseReference databaseReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference();

    public Pcs_DirectoryCustomPopupWindow(Context context, View v) {
        super(context);
        this.context = context;
        this.popupView = LayoutInflater.from(context).inflate(R.layout.pcs_directory_popupactivity, null);
        setPopupWindow();
        setInit();
        setRecyclerview();
    }
    public void show(View anchor, int x, int y, String directoryKey){
        showAtLocation(anchor, Gravity.CENTER, x, y);
    }


    private void setInit() {
        cancelButton = popupView.findViewById(R.id.directory_cancelButton);
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confirmButton = popupView.findViewById(R.id.directory_confirmButton);
        confirmButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setPopupWindow(){
        setContentView(popupView);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

        // Closes the popup window when touch outside of it - when looses focus
        setOutsideTouchable(true);
        setFocusable(true);
    }

    private void setRecyclerview(){

        Query query = databaseReference.child("directory");
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<KSH_DirectoryEntity>()
                .setQuery(query, KSH_DirectoryEntity.class)
                .build();


        adapter = new Pcs_DicRecyclerviewAdapter(options, "");
        ////////////////////////////ERROR
        recyclerView = popupView.findViewById(R.id.pcs_directoryRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }



}
