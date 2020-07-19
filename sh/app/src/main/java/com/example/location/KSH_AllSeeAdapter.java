package com.example.location;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class KSH_AllSeeAdapter extends RecyclerView.Adapter<KSH_AllSeeAdapter.ViewHolder> {
    Context mcontext;
    private ArrayList<KSH_TestEntity> arrayList;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private ArrayList<String> arrayKey;

    public KSH_AllSeeAdapter(Context context, ArrayList<KSH_TestEntity> arrayList, DatabaseReference databaseReference, FirebaseDatabase firebaseDatabase, ArrayList<String> arrayKey) {
        mcontext = context;
        this.arrayList = arrayList;
        this.databaseReference = databaseReference;
        this.firebaseDatabase = firebaseDatabase;
        this.arrayKey = arrayKey;
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
    public void onBindViewHolder(@NonNull final KSH_AllSeeAdapter.ViewHolder holder, final int position) {
        String Title = String.valueOf(arrayList.get(position).getTitle());
        holder.recy_test_title.setText(Title);

        holder.allseebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mcontext, holder.allseebtn);
                popupMenu.inflate(R.menu.ksh_allsee_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.allsee_delete:
                                // 밑에 child() 안에 key 가 들어가면 delete 되는데 key를 못 받아오겠음
                                // arraylist에 담아서 참조하려하는데 title은 제대로 뜨는데 key값은 null 로 나옴
//                                databaseReference.child().removeValue();
//                                databaseReference.getRef().getKey();
                                Log.d("1",String.valueOf(databaseReference.child("Test").getKey()));
                                Log.d("1",String.valueOf(arrayKey.get(position)));
                                Log.d("1",String.valueOf(arrayList.get(position).getTitle()));
                                break;
                            case R.id.allsee_change:
                                Log.d("1","allsee_change");
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recy_test_title;
        TextView allseebtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recy_test_title = itemView.findViewById(R.id.allsee_text_view_title);
            this.allseebtn = itemView.findViewById(R.id.allsee_ViewOptions);

        }
    }
}
