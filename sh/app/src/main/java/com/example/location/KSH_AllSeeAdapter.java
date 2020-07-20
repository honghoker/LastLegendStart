package com.example.location;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KSH_AllSeeAdapter extends RecyclerView.Adapter<KSH_AllSeeAdapter.ViewHolder> {
    Context mcontext;
    private ArrayList<KSH_TestEntity> arrayList;
    DatabaseReference databaseReference;
    private ArrayList<String> arrayKey;
    private View updateView;
    private View itemView;
    private Map<String,Object> testMap = new HashMap<String, Object>();
    private View view;

    public KSH_AllSeeAdapter(Context context, ArrayList<KSH_TestEntity> arrayList, ArrayList<String> arrayKey) {
        mcontext = context;
        this.arrayList = arrayList;
        this.arrayKey = arrayKey;
        // 싱글톤
        KSH_FireBase firebaseDatabase = KSH_FireBase.getInstance(mcontext);
        databaseReference = firebaseDatabase.databaseReference;
    }

    @NonNull
    @Override
    public KSH_AllSeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ksh_allsee_item, parent, false);
        updateView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ksh_allsee_update, parent,false);
        holder = new ViewHolder(itemView);

        return (ViewHolder) holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final KSH_AllSeeAdapter.ViewHolder holder, final int position) {
        String Title = String.valueOf(arrayList.get(position).getTitle());
        holder.recy_test_title.setText(Title);

        holder.allseebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(mcontext, holder.allseebtn);
                popupMenu.inflate(R.menu.ksh_allsee_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.allsee_delete:
                                // 밑에 child() 안에 key 가 들어가면 delete 되는데 key를 못 받아오겠음
                                // arraylist에 담아서 참조하려하는데 title은 제대로 뜨는데 key값은 null 로 나옴
                                databaseReference.child(arrayKey.get(position)).removeValue();
//                                databaseReference.getRef().getKey();
                                Log.d("1",String.valueOf(databaseReference.child("Test").getKey()));
                                Log.d("1",String.valueOf(arrayKey.get(position)));
                                Log.d("1",String.valueOf(arrayList.get(position).getTitle()));
                                break;
                            case R.id.allsee_change:
                                final AlertDialog.Builder dialog = new AlertDialog.Builder(mcontext);
                                dialog.setTitle("directory 이름 변경");
                                final EditText updateName = updateView.findViewById(R.id.allsee_update);
                                // view는 하나의 부모 view에만 추가 가능, 여러번 다이어로그 띄우는 순간 중복으로 view가 참조되어 오류남
                                    if(updateView.getParent()!=null){
                                        ((ViewGroup) updateView.getParent()).removeView(updateView);
                                        updateName.setText("");
                                    }
                                dialog.setView(updateView);
                                dialog.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        testMap.put(arrayKey.get(position)+"/title",String.valueOf(updateName.getText()));
                                        databaseReference.updateChildren(testMap);
                                    }
                                });
                                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                dialog.show();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("1","allseeItem click");
                }
            });

        }
    }
}
