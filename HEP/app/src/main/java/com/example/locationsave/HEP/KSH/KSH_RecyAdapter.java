package com.example.locationsave.HEP.KSH;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.KMS.Map.KMS_MarkerManager;

import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.locationsave.HEP.KMS.Toolbar.KMS_ClearableEditText_LoadLocation_auto.mContext;
import static com.example.locationsave.HEP.KMS_MainActivity.LocationFragmet;
import static com.example.locationsave.HEP.KMS_MainActivity.autoCompleteLocationList;
import static com.example.locationsave.HEP.KMS_MainActivity.directoryid;

public class KSH_RecyAdapter extends RecyclerView.Adapter<KSH_RecyAdapter.ViewHolder> {
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    Context mcontext;
    private ArrayList<KSH_DirectoryEntity> arrayList;
    private ArrayList<String> arrayKey;
    DatabaseReference databaseReference;
    KSH_DirectoryEntity ksh_directoryEntity;
    KSH_Date ksh_date = new KSH_Date();
    private int selectView = 0;
    KMS_MarkerManager kms_markerManager = new KMS_MarkerManager().getInstanceMarkerManager();
    public static int LastPosition = -1; //단일 선택 위한 변수

    private sunghunTest mCallback;
    FloatingActionButton floatingButton = KMS_MainActivity.floatingButton;
    public KSH_RecyAdapter(Context context, ArrayList<KSH_DirectoryEntity> arrayList,ArrayList<String> arrayKey, KSH_DirectoryEntity ksh_directoryEntity, int selectView, sunghunTest listener) {
        mcontext = context;
        this.arrayList = arrayList;
        this.ksh_directoryEntity = ksh_directoryEntity;
        this.arrayKey = arrayKey;
        this.selectView = selectView;
//        Log.d("6","11 adapter selectView = "+selectView);
//        Log.d("6","11 adapter lastPosition = "+LastPosition);

        mCallback = listener;

        // 싱글톤
        KSH_FireBase firebaseDatabase = KSH_FireBase.getInstance();
        databaseReference = firebaseDatabase.databaseReference;
    }

    class HeaderViewHolder extends ViewHolder {
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
    public void onBindViewHolder(@NonNull KSH_RecyAdapter.ViewHolder holder, int position) {
//        holder.itemView.setBackgroundColor(Color.parseColor("#cccccc"));
//        Log.d("6","22 adapter selectView = "+selectView);
        if(LastPosition == -1 && selectView != 0)
            LastPosition = selectView;


        if(holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        }
        else{
//            Log.d("6","33 adapter selectView = "+selectView);
            if(LastPosition == -1)
                LastPosition = selectView;

//            Log.d("6","LastPosition = "+LastPosition);

            if (position == LastPosition && selectView != 0){
//                holder.layout.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.ksh_border_design));
                holder.imageView.setVisibility(View.VISIBLE);
//                holder.itemView.setBackgroundColor(Color.RED);
            }
            else{
//                holder.layout.setBackground(ContextCompat.getDrawable(mcontext,R.drawable.ksh_border_not_design));
                holder.imageView.setVisibility(View.GONE);
//                holder.itemView.setBackgroundColor(Color.parseColor("#cccccc"));
            }


            String Title = String.valueOf(arrayList.get(position-1).getName());
            String createTime = String.valueOf(arrayList.get(position-1).getCreateTime());
            holder.recy_test_title.setText(Title);
            holder.recy_createTime.setText(createTime);
        }
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size()+1 : 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recy_test_title;
        TextView recy_createTime;
        ConstraintLayout layout;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recy_test_title = itemView.findViewById(R.id.recy_test_title);
            this.recy_createTime = itemView.findViewById(R.id.recy_createTime);
            layout = itemView.findViewById(R.id.ksh_constraintlayout_test);
            imageView = itemView.findViewById(R.id.ksh_imageview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos == 0){
                        //AlterDialog
                        final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
//                        final AlertDialog.Builder builder_1 = new AlertDialog.Builder(mcontext);

                        builder.setTitle("Title").setMessage("directory의 이름을 적어주세요");

                        final EditText editText = new EditText(mcontext);
                        editText.setSingleLine(true);
                        builder.setView(editText);

                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(editText.getText().toString().equals("")){
                                    Toast.makeText(mcontext,"공백입니다",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else{
                                    KSH_DirectoryEntity ksh_directoryEntity = new KSH_DirectoryEntity(editText.getText().toString(),ksh_date.nowDate(),ksh_date.nowDate());
                                    databaseReference.push().setValue(ksh_directoryEntity);
                                    dialog.dismiss();
                                }
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
                        try {

                            directoryid = arrayKey.get(pos - 1);
                            Log.d("7","!!!!!!");
                            Log.d("7","id = " +directoryid);

                            new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("location").orderByChild("directoryid").equalTo(directoryid).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Log.d("@@@@", "getChildrenCount = " + snapshot.getChildrenCount());
                                    autoCompleteLocationList.clear();
                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        hep_Location hep_location = dataSnapshot.getValue(hep_Location.class);
                                        Log.d("@@@@", "getChildrenCount = " + dataSnapshot.getKey() + ", hep_location.nmae = " + hep_location.name);
                                        autoCompleteLocationList.add(hep_location);
                                        Log.d("7","??? "+autoCompleteLocationList.size());
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            LastPosition = pos;

                            String Title = String.valueOf(arrayList.get(pos-1).getName());
                            mCallback.onClick(Title, v);
                            Query latilonginameQuery = new hep_FireBase().getFireBaseDatabaseInstance().getReference().child("location").orderByChild("directoryid").equalTo(directoryid);
                            latilonginameQuery.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    new KMS_MarkerManager().getInstanceMarkerManager().initMarker();
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        hep_Location hep_location = dataSnapshot.getValue(hep_Location.class);
                                        new KMS_MarkerManager().getInstanceMarkerManager().addMarker(kms_markerManager.markers, hep_location, dataSnapshot.getKey());
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            if(LocationFragmet != null) {
                                FragmentTransaction transaction = LocationFragmet.getFragmentManager().beginTransaction();
                                transaction.detach(LocationFragmet).attach(LocationFragmet).commit();
                            }

                            notifyDataSetChanged();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
    }


}
