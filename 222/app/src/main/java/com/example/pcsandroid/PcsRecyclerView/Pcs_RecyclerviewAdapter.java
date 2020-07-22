package com.example.pcsandroid.PcsRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcsandroid.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Pcs_RecyclerviewAdapter extends FirestoreRecyclerAdapter<Location, Pcs_RecyclerviewAdapter.ListHolder> {

    public Pcs_RecyclerviewAdapter(@NonNull FirestoreRecyclerOptions<Location> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListHolder listHolder, int i, @NonNull Location location) {
        listHolder.textViewTitle.setText(location.getTitle());
        listHolder.textViewAddress.setText(location.getAddress());
        listHolder.textViewTag.setText(location.getTag());
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_recyclerview_cardview
                ,parent,false);

        return new ListHolder(v);
    }

    class ListHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewAddress;
        TextView textViewTag;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.cardView_title);
            textViewAddress = itemView.findViewById(R.id.cardView_address);
            textViewTag = itemView.findViewById(R.id.cardView_tag);
        }
    }
}
