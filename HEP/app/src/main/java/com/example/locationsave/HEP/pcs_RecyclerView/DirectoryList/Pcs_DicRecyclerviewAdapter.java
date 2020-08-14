package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Pcs_DicRecyclerviewAdapter extends FirebaseRecyclerAdapter<hep_Location, Pcs_DicRecyclerviewAdapter.Listholder> {
    private DatabaseReference databaseReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference();
    private static RadioButton lastCheckedRB = null;
    private static int lastCheckedPos = 0;
    private String selectDirectoryKey;
    public int mSelectedItem = -1;


    public Pcs_DicRecyclerviewAdapter(@NonNull FirebaseRecyclerOptions<hep_Location> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final Listholder holder, final int position, @NonNull hep_Location hep_location) {

//        holder.directoryTextView.setText(getSnapshots().getSnapshot(position).getValue(KSH_DirectoryEntity.class).getName());
        holder.directoryTextView.setText(hep_location.getName());
//        if(getSnapshots().getSnapshot(position).getKey() == selectDirectoryKey) {
//            holder.selectRadioButton.setChecked(true);
//            lastCheckedPos = position;
//            lastCheckedRB = holder.selectRadioButton;
//        }
//        else
//            holder.selectRadioButton.setChecked(false);

//        holder.selectRadioButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                RadioButton radioButton = (RadioButton)v;
//                int clickedPos = ((Integer)radioButton.getTag()).intValue();
//                if(radioButton.isChecked()){
//                    if(lastCheckedRB != null){
//                        lastCheckedRB.setChecked(false);
//                        getItemId()
//                    }
//                }
//            }
//        });

    }

    @NonNull
    @Override
    public Listholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pcs_directory_cardview, parent, false);
        return new Listholder(v);
    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


    class Listholder extends RecyclerView.ViewHolder{
        private RadioButton selectRadioButton;
        private RadioGroup selectRadioGroup;
        private TextView directoryTextView;

        public Listholder(@NonNull View itemView) {
            super(itemView);
            directoryTextView = itemView.findViewById(R.id.pcs_directoryTextview);
            selectRadioButton = itemView.findViewById(R.id.pcs_directoryRadiobutton);
//            View.OnClickListener clickListener = new View.OnClickListener(){
//                @Override
//                public void onClick(View v) {
//                    mSelectedItem = getAdapterPosition();
//                    notifyDataSetChanged();
//                }
//            };
//            itemView.setOnClickListener(clickListener);
//            selectRadioButton.setOnClickListener(clickListener);
        }
    }

}
