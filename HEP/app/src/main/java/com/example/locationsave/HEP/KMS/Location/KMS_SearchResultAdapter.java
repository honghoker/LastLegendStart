package com.example.locationsave.HEP.KMS.Location;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.KMS.MainFragment.KMS_MapFragment;
import com.example.locationsave.HEP.KMS.Map.KMS_CameraManager;
import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;
import com.naver.maps.map.NaverMap;

import java.util.ArrayList;

public class KMS_SearchResultAdapter extends RecyclerView.Adapter<KMS_SearchResultAdapter.CustomViewHolder> {

    private ArrayList<KMS_LocationSearchResult> mList;

    public static int LastPosition = -1;
    ArrayList<KMS_LocationSearchResult> kms_locationSearchResults = new ArrayList<KMS_LocationSearchResult>();
    KMS_CameraManager kms_cameraManager = KMS_CameraManager.getInstanceCameraManager();
    NaverMap naverMap = KMS_MapFragment.NMap;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView Title;
        protected TextView RoadAddress;
        public CustomViewHolder(View view) {
            super(view);
            this.Title = (TextView) view.findViewById(R.id.title_listitem);
            this.RoadAddress = (TextView) view.findViewById(R.id.roadAddress_listitem);
        }
    }

    public KMS_SearchResultAdapter(ArrayList<KMS_LocationSearchResult> list) {
        this.mList = list;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.kms_search_result_list, viewGroup, false);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                LastPosition = position;

                kms_locationSearchResults = KMS_MainActivity.kms_locationSearchResults;

                double latitude = kms_locationSearchResults.get(position).getLatitude();
                double longitude = kms_locationSearchResults.get(position).getLongitude();
                kms_cameraManager.MoveCameraOnLatlngPosition(latitude, longitude, naverMap);

                notifyDataSetChanged();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        if (position == LastPosition)
            viewholder.itemView.setBackgroundColor(Color.parseColor("#D8DEE1"));
        else
            viewholder.itemView.setBackgroundColor(Color.WHITE);

        viewholder.Title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        viewholder.RoadAddress.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        viewholder.Title.setGravity(Gravity.CENTER);
        viewholder.RoadAddress.setGravity(Gravity.CENTER);

        viewholder.Title.setText(mList.get(position).getTitle());
        viewholder.RoadAddress.setText(mList.get(position).getRoadAddress());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}