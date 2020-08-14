package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

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

public class Pcs_DicRecyclerviewAdapter extends FirebaseRecyclerAdapter<KSH_DirectoryEntity, Pcs_DicRecyclerviewAdapter.Listholder> {
    private DatabaseReference databaseReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference();
    private static RadioButton lastCheckedRB = null;
    private static int lastCheckedPos = 0;
    private String selectDirectoryKey;
    public int mSelectedItem = -1;


    public Pcs_DicRecyclerviewAdapter(@NonNull FirebaseRecyclerOptions options) {
        super(options);
    }
    public Pcs_DicRecyclerviewAdapter(@NonNull FirebaseRecyclerOptions options, String selectDirectoryKey) {
        super(options);
        this.selectDirectoryKey = selectDirectoryKey;
    }


    @Override
    protected void onBindViewHolder(@NonNull final Listholder holder, final int position, @NonNull KSH_DirectoryEntity ksh_directoryEntity) {

//        holder.directoryTextView.setText(getSnapshots().getSnapshot(position).getValue(KSH_DirectoryEntity.class).getName());
        holder.directoryTextView.setText(getSnapshots().getSnapshot(position).getValue(hep_Location.class).getName());
        if(getSnapshots().getSnapshot(position).getKey() == selectDirectoryKey) {
            holder.selectRadioButton.setChecked(true);
            lastCheckedPos = position;
            lastCheckedRB = holder.selectRadioButton;
        }
        else
            holder.selectRadioButton.setChecked(false);

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
        return null;
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
            View.OnClickListener clickListener = new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            };
            itemView.setOnClickListener(clickListener);
            selectRadioButton.setOnClickListener(clickListener);
        }
    }

}
