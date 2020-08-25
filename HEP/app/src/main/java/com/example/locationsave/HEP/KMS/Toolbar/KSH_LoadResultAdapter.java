package com.example.locationsave.HEP.KMS.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.HEP.KMS.Map.KMS_CameraManager;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;

import java.util.ArrayList;

public class KSH_LoadResultAdapter extends RecyclerView.Adapter<KSH_LoadResultAdapter.CustomViewHolder>{
    private ArrayList<hep_Location> hep_locationArrayList;
    KMS_CameraManager kms_cameraManager = KMS_CameraManager.getInstanceCameraManager();

    public KSH_LoadResultAdapter(ArrayList<hep_Location> list) {
        this.hep_locationArrayList = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView Title;

        public CustomViewHolder(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        double latitude = hep_locationArrayList.get(pos).latitude;
                        double longitude = hep_locationArrayList.get(pos).longitude;
                        if(latitude != 0 && longitude != 0 ){
                            kms_cameraManager.MoveCameraOnLatlngPosition(latitude, longitude, KMS_MapFragment.NMap);
                        }
                        else{
                            Toast.makeText(KMS_MainActivity.mainContext, "저장된 위치가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            this.Title = (TextView) view.findViewById(R.id.load_title);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ksh_load_result_list, parent, false);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.Title.setText(hep_locationArrayList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return (null != hep_locationArrayList ? hep_locationArrayList.size() : 0);
    }
}
