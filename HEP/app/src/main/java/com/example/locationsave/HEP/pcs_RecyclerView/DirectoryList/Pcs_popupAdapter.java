package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.KSH.KSH_DirectoryEntity;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class Pcs_popupAdapter extends FirebaseRecyclerAdapter<KSH_DirectoryEntity, Pcs_popupAdapter.ListViewHolder> {
    private int lastCheckedPosition = -1;
    private String currentKeyOfDirectory;
    private String lastCheckedDirectoryKey = null;
    private hep_Location hep_location;

    public Pcs_popupAdapter(@NonNull FirebaseRecyclerOptions<KSH_DirectoryEntity> options, hep_Location hep_location) {
        super(options);
        this.hep_location = hep_location;
    }

    @Override
    protected void onBindViewHolder(@NonNull ListViewHolder holder, int position, @NonNull KSH_DirectoryEntity ksh_directoryEntity) {
        holder.directoryText.setText(ksh_directoryEntity.getName());
        holder.directoryRadioButton.setChecked(position == lastCheckedPosition);
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
        return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pcs_directory_cardview, parent, false));
    }


    class ListViewHolder extends RecyclerView.ViewHolder{
        private TextView directoryText;
        private RadioButton directoryRadioButton;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            directoryText = itemView.findViewById(R.id.pcs_directoryTextview);
            directoryRadioButton = itemView.findViewById(R.id.pcs_directoryRadiobutton);
            directoryRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();

                    //Changed two button
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);
                }
            });
        }
    }
    private String getDirectoryKey(int position){
        return getSnapshots().getSnapshot(position).getKey();
    }
}
