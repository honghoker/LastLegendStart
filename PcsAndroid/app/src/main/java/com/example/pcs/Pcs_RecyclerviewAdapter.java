package com.example.pcs;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Pcs_RecyclerviewAdapter extends RecyclerView.Adapter<Pcs_RecyclerviewAdapter.ListHolder> {

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ListHolder extends RecyclerView.ViewHolder{

        public ListHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
