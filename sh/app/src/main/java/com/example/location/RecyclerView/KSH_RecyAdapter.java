package com.example.location.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;

public class KSH_RecyAdapter extends RecyclerView.Adapter<KSH_RecyAdapter.ViewHolder> {
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    Context mcontext;

    public KSH_RecyAdapter(Context context) {
        mcontext = context;
    }

    class HeaderViewHolder extends ViewHolder {

        HeaderViewHolder(View headerView) {
            super(headerView);
        }
    }

    @NonNull
    @Override
    public KSH_RecyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view;

        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ksh_recyclerview_addbtn, parent, false);
            holder = new HeaderViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ksh_recyclerview_item, parent, false);
            holder = new ViewHolder(view);
        }
        return (ViewHolder) holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }
        else{
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull KSH_RecyAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos == 0){
                        //AlterDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                        builder.setTitle("1").setMessage("1");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            });
        }
    }
}
