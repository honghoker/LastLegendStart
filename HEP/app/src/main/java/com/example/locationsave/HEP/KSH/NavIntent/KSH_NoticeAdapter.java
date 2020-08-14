package com.example.locationsave.HEP.KSH.NavIntent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.KSH.KSH_RecyAdapter;
import com.example.locationsave.R;

public class KSH_NoticeAdapter extends RecyclerView.Adapter<KSH_NoticeAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noticeDay;
        TextView noticeTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeDay = itemView.findViewById(R.id.notice_day);
            noticeTitle = itemView.findViewById(R.id.notice_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("6","notice click");
                }
            });
//            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public KSH_NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ksh_notice_list, parent, false);
        KSH_NoticeAdapter.ViewHolder holder = new KSH_NoticeAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KSH_NoticeAdapter.ViewHolder holder, int position) {
        holder.noticeTitle.setText("공지사항입니다");
        holder.noticeDay.setText("2020-08-15");
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}
