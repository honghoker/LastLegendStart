package com.example.locationsave.HEP.KSH;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.locationsave.HEP.KMS_MainActivity.directoryid;

public class KSH_AllSeeAdapter extends RecyclerView.Adapter<KSH_AllSeeAdapter.ViewHolder> {
    Context mcontext;
    private ArrayList<KSH_DirectoryEntity> arrayList;
    DatabaseReference databaseReference;
    private ArrayList<String> arrayKey;
    private View updateView;
    private View itemView;
    private Map<String,Object> testMap = new HashMap<String, Object>();
    KSH_Date ksh_date = new KSH_Date();

    public KSH_AllSeeAdapter(Context context, ArrayList<KSH_DirectoryEntity> arrayList, ArrayList<String> arrayKey) {
        mcontext = context;
        this.arrayList = arrayList;
        this.arrayKey = arrayKey;
        KSH_FireBase firebaseDatabase = KSH_FireBase.getInstance();
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
        String Title = String.valueOf(arrayList.get(position).getName());
        String createTime = String.valueOf(arrayList.get(position).getCreateTime());
        String updateTime = String.valueOf(arrayList.get(position).getUpdateTime());

        holder.recy_test_title.setText(Title);
        holder.createTime.setText(createTime);
        holder.updateTime.setText(updateTime);

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
                                Log.d("@@@", "directoryid = " + directoryid + ", arrayKey = " + arrayKey.get(position) + ", position = " + position);

                                if(directoryid.equals(arrayKey.get(position))) {
                                    Toast.makeText(mcontext, "현재 사용중인 directory는 삭제할 수 없습니다", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    databaseReference.child(arrayKey.get(position)).removeValue();
                                    arrayKey.remove(position);
                                }
                                break;

                            case R.id.allsee_change:
                                final AlertDialog.Builder dialog = new AlertDialog.Builder(mcontext);
                                dialog.setTitle("directory 이름 변경");
                                final EditText updateName = updateView.findViewById(R.id.allsee_update);
                                    if(updateView.getParent()!=null){
                                        ((ViewGroup) updateView.getParent()).removeView(updateView);
                                        updateName.setText("");
                                    }
                                dialog.setView(updateView);
                                dialog.setPositiveButton("변경", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        testMap.put(arrayKey.get(position)+"/name",String.valueOf(updateName.getText()));
                                        testMap.put(arrayKey.get(position)+"/updateTime",ksh_date.nowDate());
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
        TextView createTime;
        TextView updateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recy_test_title = itemView.findViewById(R.id.allsee_text_view_title);
            this.allseebtn = itemView.findViewById(R.id.allsee_ViewOptions);
            this.createTime = itemView.findViewById(R.id.allsee_createTime);
            this.updateTime = itemView.findViewById(R.id.allsee_updateTime);
        }
    }
}
