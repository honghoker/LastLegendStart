package com.example.locationsave.HEP.pcs_RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Pcs_RecyclerviewAdapter extends FirebaseRecyclerAdapter<hep_Location, Pcs_RecyclerviewAdapter.ListHolder> {


    public Pcs_RecyclerviewAdapter(@NonNull FirebaseRecyclerOptions<hep_Location> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListHolder listHolder, int i, @NonNull hep_Location location) {
        listHolder.textViewTitle.setText(location.getName());
        listHolder.textViewAddress.setText(location.getAddr());
        listHolder.textViewTag.setText(getTag(location));
        //listHolder.textViewTag.setText(location.getTag());
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pcs_location_recyclerview_cardview
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
    private String checkNull(String tag){
        if(tag != null)
            return tag + " ";
        else
            return "";
    }
    private String getTag(hep_Location location){
        return checkNull(location.getTag0()) + checkNull(location.getTag1()) +
                checkNull(location.getTag2()) + checkNull(location.getTag3())
                + checkNull(location.getTag4());
    }
}
