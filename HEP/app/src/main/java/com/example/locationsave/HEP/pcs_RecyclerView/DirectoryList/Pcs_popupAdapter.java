package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class Pcs_popupAdapter extends FirebaseRecyclerAdapter<KSH_DirectoryEntity, Pcs_popupAdapter.ListViewHolder> {
    private List<tempData> categories;

    public Pcs_popupAdapter(@NonNull FirebaseRecyclerOptions<KSH_DirectoryEntity> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListViewHolder holder, int position, @NonNull KSH_DirectoryEntity model) {
        holder.tvCategory.setText(model.getName());
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pcs_directory_cardview, parent, false));
    }


    class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tvCategory;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.pcs_directoryTextview);
        }
    }


}
