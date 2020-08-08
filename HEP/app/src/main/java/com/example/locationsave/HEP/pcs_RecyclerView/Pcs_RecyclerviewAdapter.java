package com.example.locationsave.HEP.pcs_RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Tag;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_LocationDetail.hep_LocationDetailActivity;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Pcs_RecyclerviewAdapter extends FirebaseRecyclerAdapter<hep_Location, Pcs_RecyclerviewAdapter.ListHolder> {
    private DatabaseReference databaseReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference();

    public Pcs_RecyclerviewAdapter(@NonNull FirebaseRecyclerOptions<hep_Location> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListHolder listHolder, int i, @NonNull hep_Location location) {

        listHolder.textViewTitle.setText(location.getName());
        String tagString = getTagFromLocationTag(this.getSnapshots().getSnapshot(i).getKey());
        if(tagString == null)
            tagString = "Empty";
        listHolder.textViewTag.setText(tagString);
        Log.d("tag", tagString+"TagString onBindViewHolder");
//        listHolder.textViewAddrzess.setText(location.getAddr());
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
    private String getTagFromLocationTag(String locationKey){
        Log.d("tag", locationKey+"GetTagFromLocationTag");
        final ArrayList<String> tagStringArray = new ArrayList<>();
        final String[] tagString = {null};
        databaseReference.getDatabase().getReference().child("locationtag").orderByChild("locationid").equalTo(locationKey).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("tag", "Inside onDataChange getTagFromLocationTag");
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Log.d("tag", getTagFromTag(dataSnapshot.getValue(hep_LocationTag.class).tagid) + "tagID from getTagFromLocation");
                        tagString[0] += tagStringArray.add(getTagFromTag(dataSnapshot.getValue(hep_LocationTag.class).tagid));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return tagString[0];
    }

    private String getTagFromTag(final String tagID) {
        final String[] tag = new String[1];
        databaseReference.getDatabase().getReference().child("tag").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("tag", "Inside onDataChange getTagFromTag");
                if(snapshot.exists()){
                    Log.d("tag",snapshot.getValue(hep_Tag.class).getName() + "Name from getTagFromTag");
                    tag[0] = snapshot.getValue(hep_Tag.class).getName();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return tag[0];
    }


}
