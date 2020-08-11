package com.example.locationsave.HEP.KMS.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.KMS.Location.KMS_LocationSearchResult;
import com.example.locationsave.R;

import java.util.ArrayList;

public class KSH_LoadResultAdapter extends RecyclerView.Adapter<KSH_LoadResultAdapter.CustomViewHolder>{
    private ArrayList<String> a;

    public KSH_LoadResultAdapter(ArrayList<String> list) {
        a = list;
        Log.d("6","a size = "+a.size());
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView Title;

        public CustomViewHolder(View view) {
            super(view);
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
        holder.Title.setText(a.get(position));
    }

    @Override
    public int getItemCount() {
        return (null != a ? a.size() : 0);
    }
}
