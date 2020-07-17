package com.example.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class KSH_AllSeeAdapter extends RecyclerView.Adapter<KSH_AllSeeAdapter.ViewHolder> {
    Context mcontext;
    private ArrayList<KSH_TestEntity> arrayList;
    DatabaseReference databaseReference;

    public KSH_AllSeeAdapter(Context context, ArrayList<KSH_TestEntity> arrayList, DatabaseReference databaseReference) {
        mcontext = context;
        this.arrayList = arrayList;
        this.databaseReference = databaseReference;
//        Log.d("1","allsee arraylist size"   +arrayList.size());
    }

    @NonNull
    @Override
    public KSH_AllSeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ksh_allsee_item, parent, false);
        holder = new ViewHolder(view);

        return (ViewHolder) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KSH_AllSeeAdapter.ViewHolder holder, int position) {
        String Title = String.valueOf(arrayList.get(position).getTitle());
        holder.recy_test_title.setText(Title);
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recy_test_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recy_test_title = itemView.findViewById(R.id.allsee_text_view_title);

        }
    }
}
