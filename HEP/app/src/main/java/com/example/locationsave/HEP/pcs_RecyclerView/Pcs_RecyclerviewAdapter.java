package com.example.locationsave.HEP.pcs_RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_LocationDetail.hep_LocationDetailActivity;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;


public class Pcs_RecyclerviewAdapter extends FirebaseRecyclerAdapter<hep_Location, Pcs_RecyclerviewAdapter.ListHolder> {


    public Pcs_RecyclerviewAdapter(@NonNull FirebaseRecyclerOptions<hep_Location> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListHolder listHolder, int i, @NonNull hep_Location location) {
        listHolder.textViewTitle.setText(location.getName());
//        listHolder.textViewAddress.setText(location.getAddr());
//        listHolder.textViewTag.setText(getTag(location));
        //listHolder.textViewTag.setText(location.getTag());
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pcs_location_recyclerview_cardview
                ,parent,false);

        return new ListHolder(v);
    }
    public CapsulizeData deleteItem(int position) {
        DataSnapshot dataSnapshot = getSnapshots().getSnapshot(position);
        getSnapshots().getSnapshot(position).getRef().removeValue();
        return new CapsulizeData(dataSnapshot.getValue(hep_Location.class), getSnapshots().getSnapshot(position).getKey());
    }

    class ListHolder extends RecyclerView.ViewHolder{
        public DataSnapshot getData(int position){
            return getSnapshots().getSnapshot(position);
        }


        TextView textViewTitle;
        TextView textViewAddress;
        TextView textViewTag;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    Intent intent = new Intent(v.getContext(), hep_LocationDetailActivity.class);
                    intent.putExtra("key", getSnapshots().getSnapshot(p).getRef().getKey());
                    v.getContext().startActivity(intent);
                }
            });

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
        return "찬섭아 수정해야한다..";
                /*checkNull(location.getTag0()) + checkNull(location.getTag1()) +
                checkNull(location.getTag2()) + checkNull(location.getTag3())
                + checkNull(location.getTag4());*/

    }

}
