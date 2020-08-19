package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.example.locationsave.HEP.pcs_RecyclerView.locationList.CapsulizeDataObjectNKey;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Pcs_popupAdapter extends FirebaseRecyclerAdapter<KSH_DirectoryEntity, Pcs_popupAdapter.ListViewHolder> {
    private int lastCheckedPosition = -1;
    private String currentKeyOfDirectory;
    private String lastCheckedDirectoryKey = null;
    private hep_Location hep_location;
    private ViewGroup parent;
    private String currentLocationKey;

    public Pcs_popupAdapter(@NonNull FirebaseRecyclerOptions<KSH_DirectoryEntity> options, CapsulizeDataObjectNKey currentLocation) {
        super(options);
        this.hep_location = (hep_Location)currentLocation.getFirebaseData();
        this.currentLocationKey = currentLocation.getDataKey();
    }

    @Override
    protected void onBindViewHolder(@NonNull ListViewHolder holder, int position, @NonNull KSH_DirectoryEntity ksh_directoryEntity) {
        holder.directoryText.setText(ksh_directoryEntity.getName());
        if(getSnapshots().getSnapshot(position).getKey().equals(hep_location.getDirectoryid()))
            holder.cardviewLayout.setBackgroundResource(R.color.colorPrimary);
        Log.d("tag", "Location Directory " + hep_location.getDirectoryid());
        Log.d("tag", "current position directory "+ getSnapshots().getSnapshot(position).getKey());
        Log.d("tag", String.valueOf(hep_location.getDirectoryid() == getSnapshots().getSnapshot(position).getKey()));

//        Log.d("tag","compare  " + hep_location.getDirectoryid());
//        if(getSnapshots().getSnapshot(position).getKey() == hep_location.getDirectoryid())
//            Log.d("tag","TRUE");
//        else
//            Log.d("tag","FALSE " +getSnapshots().getSnapshot(position).getKey());
//        holder.directoryRadioButton.setChecked(getSnapshots().getSnapshot(position).getKey() == currentKeyOfDirectory);


    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pcs_directory_cardview, parent, false));
    }


    class ListViewHolder extends RecyclerView.ViewHolder{
        private TextView directoryText;
        private RelativeLayout cardviewLayout;

        public ListViewHolder(@NonNull final View itemView) {
            super(itemView);
            directoryText = itemView.findViewById(R.id.pcs_directoryTextview);
            cardviewLayout = itemView.findViewById(R.id.pcs_directoryLayout);
            cardviewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectDirectoryKey = getSnapshots().getSnapshot(getAdapterPosition()).getKey();
                    setDirectoryKey(selectDirectoryKey, itemView);
                }
            });
        }
    }
    private void setDirectoryKey(final String selectItemDirectoryKey, final View v){
        final DatabaseReference db= new hep_FireBase().getFireBaseDatabaseInstance().getReference();
        if(this.hep_location.getDirectoryid().equals(selectItemDirectoryKey)){
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pcs_location_recyclerview, parent, false);
            final Snackbar snackbar = Snackbar.make(v, "Snackbar", Snackbar.LENGTH_LONG);
            snackbar.setAction("이미 저장되어있는 곳입니다.", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }else{
            Log.d("tag", "current location Key " + currentLocationKey);
            db.child("location").orderByKey().equalTo(currentLocationKey)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        Log.d("tag", "here ");
                        db.child("location").child(currentLocationKey).child("directoryid").setValue(selectItemDirectoryKey);
                        notifyDataSetChanged(); //Recyclerview DataChange Refresh

                        //Remove directory recyclerview
                        //v.getParent().getParent().getParent().getParent() : popupwindow
                        //v.getParent().getParent().getParent() : Relative Layout in popupwindow
                        View popupWindowView = (ViewGroup)v.getParent().getParent().getParent();
                        ((ViewGroup)popupWindowView.getParent()).removeView((ViewGroup)popupWindowView);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

}
