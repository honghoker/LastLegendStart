package com.example.location.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.KSH_TestEntity;
import com.example.location.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class KSH_RecyAdapter extends RecyclerView.Adapter<KSH_RecyAdapter.ViewHolder> {
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    Context mcontext;
    private ArrayList<KSH_TestEntity> arrayList;
    DatabaseReference databaseReference;

    public KSH_RecyAdapter(Context context, ArrayList<KSH_TestEntity> arrayList, DatabaseReference databaseReference) {
        mcontext = context;
        this.arrayList = arrayList;
        this.databaseReference = databaseReference;
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
        if(holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        }
        else{
            String Title = String.valueOf(arrayList.get(position-1).getTitle());
            Log.d("1","123123123   "+Title);
            holder.recy_test_title.setText(Title);
        }
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size()+1 : 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recy_test_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recy_test_title = itemView.findViewById(R.id.recy_test_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos == 0){
                        //AlterDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                        builder.setTitle("Title").setMessage("directory의 이름을 적어주세요");

                        final EditText editText = new EditText(mcontext);
                        editText.setSingleLine(true);
                        builder.setView(editText);

                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.child("seq1").child("title").setValue("확인");
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
            });
        }
    }
}
