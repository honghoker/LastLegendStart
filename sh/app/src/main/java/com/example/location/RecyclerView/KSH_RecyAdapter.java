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

import com.example.location.KSH_FireBase;
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
    String directoryKey;

    public KSH_RecyAdapter(Context context, ArrayList<KSH_TestEntity> arrayList, String directoryKey) {
        mcontext = context;
        this.arrayList = arrayList;
        this.directoryKey = directoryKey;
        // 싱글톤
        KSH_FireBase firebaseDatabase = KSH_FireBase.getInstance(mcontext);
        databaseReference = firebaseDatabase.databaseReference;
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
//                                databaseReference.child("seq"+(arrayList.size()+1)).setValue(editText.getText().toString());
//                                databaseReference.child("seq"+(arrayList.size()+1)).child("title").setValue(editText.getText().toString());

                                directoryKey = databaseReference.child("Test").push().getKey();
                                // 여기 child 안에 title 로 안적어주면 error 남 ㅡㅡ -> entity 이름이랑 같아야함
                                // 걍 main에 KSH_TestEntity ksh_testEntity = snapshot.getValue(KSH_TestEntity.class); arrayList.add(ksh_testEntity);
                                // 이부분 말고 다르게 add 하는 방법 찾아보기 class로 넣는거 말고 계속 오류남
                                databaseReference.child(directoryKey).child("title").setValue(editText.getText().toString());
                                databaseReference.child(directoryKey).child("count").setValue("ex");

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
                    else{
                        // addbtn 말고 recyclerView directory 클릭했을때
                        // 아마 main 쪽으로 값 넘겨서 처리해야할듯
                        Log.d("1","recyclerView directory");
                    }
                }
            });
        }
    }
}
