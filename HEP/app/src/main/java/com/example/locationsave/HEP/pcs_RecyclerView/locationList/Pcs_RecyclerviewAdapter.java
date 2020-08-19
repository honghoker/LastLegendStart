package com.example.locationsave.HEP.pcs_RecyclerView.locationList;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
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
    protected void onBindViewHolder(@NonNull final ListHolder listHolder, int i, @NonNull hep_Location location) {
        listHolder.textViewTitle.setText(location.getName());
        listHolder.textViewAddress.setText(location.getAddr());
        listHolder.textViewTag.setText("");
        getTagFromLocationTag(getSnapshots().getSnapshot(i).getKey(), new Pcs_Callback() {

            @Override
            public void onSuccessOFTag(String result) {
                if(listHolder.textViewTag.getText() == "")
                    listHolder.textViewTag.setText(result);
                else
                    listHolder.textViewTag.setText(listHolder.textViewTag.getText() + " " + result);
            }

            @Override
            public void onFail() {
            }

        });
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pcs_location_recyclerview_cardview
                ,parent,false);

        return new ListHolder(v);
    }
    public CapsulizeDataObjectNKey deleteItem(int position) {
        DataSnapshot dataSnapshot = getSnapshots().getSnapshot(position);
        getSnapshots().getSnapshot(position).getRef().removeValue();
        return new CapsulizeDataObjectNKey(dataSnapshot.getValue(hep_Location.class), getSnapshots().getSnapshot(position).getKey());
    }

    public CapsulizeDataObjectNKey getLocation(int position) {
        DataSnapshot currentLocation = getSnapshots().getSnapshot(position);
        return new CapsulizeDataObjectNKey(currentLocation.getValue(hep_Location.class), currentLocation.getKey());
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
            //HEP
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
            textViewTag = itemView.findViewById(R.id.pcs_directoryTextview);
        }
    }

    private void getTagFromLocationTag(String locationKey, final Pcs_Callback pcs_callback){
        final ArrayList<String> tagKey = new ArrayList<>();
        databaseReference.getDatabase().getReference().child("locationtag").orderByChild("locationid").equalTo(locationKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(final DataSnapshot dataSnapshot : snapshot.getChildren()){
                        final hep_LocationTag hep_locationTag = dataSnapshot.getValue(hep_LocationTag.class);
                        new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("tag").child(hep_locationTag.tagid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                    String result = (String) dataSnapshot1.getValue();
                                    pcs_callback.onSuccessOFTag(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

}
