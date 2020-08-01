package com.example.locationsave.HEP.KSH;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class KSH_RecyclerviewAdapter extends FirebaseRecyclerAdapter<KSH_DirectoryEntity,KSH_RecyclerviewAdapter.ListHolder> {
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;

    class HeaderViewHolder extends ListHolder {
        HeaderViewHolder(View headerView) {
            super(headerView);
        }
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

    public KSH_RecyclerviewAdapter(@NonNull FirebaseRecyclerOptions<KSH_DirectoryEntity> options) {
        super(options);
    }



    @NonNull
    @Override
    public KSH_RecyclerviewAdapter.ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListHolder holder;
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ksh_recyclerview_addbtn, parent, false);
            holder = new HeaderViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ksh_recyclerview_item, parent, false);
            holder = new ListHolder(view);
        }
        return holder;
    }

    @Override
    protected void onBindViewHolder(@NonNull KSH_RecyclerviewAdapter.ListHolder holder, int position, @NonNull KSH_DirectoryEntity model) {
        if(holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        }
        else{
            holder.recy_test_title.setText(model.getName());
        }
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView recy_test_title;
        public ListHolder(@NonNull View itemView) {
            super(itemView);
            this.recy_test_title = itemView.findViewById(R.id.recy_test_title);
        }
    }
}
