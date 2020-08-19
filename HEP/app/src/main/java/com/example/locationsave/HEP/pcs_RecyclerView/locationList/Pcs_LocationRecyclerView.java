package com.example.locationsave.HEP.pcs_RecyclerView.locationList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Image;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationImage;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSaveActivity;
import com.example.locationsave.HEP.KMS_MainActivity;

import com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList.Pcs_PopupRecyclerview;

import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.locationsave.HEP.KMS_MainActivity.directoryid;


public class Pcs_LocationRecyclerView extends Fragment {
    private final static String DEFAULT_FILED = "name";
//    private final static Query.Direction DEFAULT_QUERY_DIRECTION = Query.Direction.ASCENDING;

    private FirebaseDatabase db1 = new hep_FireBase().getFireBaseDatabaseInstance();
    private RecyclerView recyclerView;
    private Pcs_RecyclerviewAdapter adapter;
    KMS_MainActivity kms_activity;
    hep_LocationSaveActivity hep_locationSaveActivity;
    private ViewGroup rootView;
    private Pcs_RecyclerViewSwipeHelper recyclerViewSwipeHelper;
    LappingDismissData lappingDismissData = null;

    KSH_SwipeHelper ksh_swipeHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.pcs_location_recyclerview, container, false);
        super.onCreate(savedInstanceState);
        //Display Menu
        setHasOptionsMenu(true);
        setUpRecyclerView();
        setUpSwipeHelper();
        return rootView;
    }



    //xml pcs_recyclerview_menu connection
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pcs_recyclerview_menu, menu);
    }

    //when menu item selection,
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.sorting_time:
                onStop();
                adapter = getFirebaseData("time");
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                break;
            case R.id.sorting_title:
                onStop();
                adapter = getFirebaseData("title");
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        onStart();
        return true;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        if (kms_activity instanceof KMS_MainActivity)
//            kms_activity = (KMS_MainActivity) getActivity();
//        else if(hep_locationSaveActivity instanceof hep_LocationSaveActivity)
//            hep_locationSaveActivity = (hep_LocationSaveActivity) hep_locationSaveActivity;
        kms_activity = (KMS_MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        kms_activity = null;
        hep_locationSaveActivity = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void setUpRecyclerView(){
//        adapter = getFirebaseData(DEFAULT_FILED, DEFAULT_QUERY_DIRECTION);\
        adapter = getFirebaseData(DEFAULT_FILED);
        recyclerView = rootView.findViewById(R.id.locationRecyclcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    //Get firebase data and put into adapter
    private Pcs_RecyclerviewAdapter getFirebaseData(String field){
        //Query query = db1.getReference().child("location").orderByChild(field);
        Query query = db1.getReference().child("location").orderByChild("directoryid").equalTo(directoryid);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<hep_Location>()
                .setQuery(query, hep_Location.class)
                .build();
        if(options.getSnapshots() == null) return new Pcs_RecyclerviewAdapter(null);
        else return new Pcs_RecyclerviewAdapter(options);

    }


    private void setUpSwipeHelper() {
        recyclerViewSwipeHelper = new Pcs_RecyclerViewSwipeHelper(getActivity(), new Pcs_RecyclerViewSwipeAction() {

            @Override
            public void onLeftClicked(int position) {
                String currentKeyOfDirectory = ((hep_Location)adapter.getLocation(position).getFirebaseData()).getDirectoryid();
                Log.d("tag", "Swipe " + currentKeyOfDirectory);
//                final Pcs_PopupRecyclerview pcs_PopupRecyclerview = new Pcs_PopupRecyclerview(getContext(), adapter.getLocation(position));
                final Pcs_PopupRecyclerview pcs_PopupRecyclerview = new Pcs_PopupRecyclerview(getActivity(), adapter.getLocation(position));
                pcs_PopupRecyclerview.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                //pcs_PopupRecyclerview.setWidth((int)(200*getResources().getDisplayMetrics().density));
                pcs_PopupRecyclerview.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                pcs_PopupRecyclerview.setFocusable(true);


                try {
                    pcs_PopupRecyclerview.showAsDropDown(getView(), (int) recyclerView.getLayoutManager().findViewByPosition(position).getX(), (int) recyclerView.getLayoutManager().findViewByPosition(position).getY(), Gravity.CENTER);
                }catch (Exception e){
                    e.printStackTrace();
                }


//                popupWindow.show(getActivity().findViewById(R.id.drawer_layout),0, -250, hep_location.getDirectoryid());
//                startActivity(intent);
            }

            @Override
            public void onRightClicked(int position) {

                //When DeleteItem, Get Location Data and Key
                final CapsulizeDataObjectNKey lappingDataNKey = adapter.deleteItem(position);
                //related dismiss data store and lapping
                lappingDismissData = new LappingDismissData(lappingDataNKey);
                lappingDismissData.onDismiss();

                Snackbar.make(getActivity().findViewById(android.R.id.content),"삭제완료",Snackbar.LENGTH_LONG).setAction("되돌리기", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        lappingDismissData.onUndo();
                    }

                }).show();

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(recyclerViewSwipeHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
        Log.d("6","1111");
        ksh_swipeHelper = new KSH_SwipeHelper(getActivity(),recyclerView,200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MyButton(getActivity(),
                        "Delete",
                        30,
                        R.drawable.ic_delete,
                        Color.parseColor("#FF3c30"),
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Log.d("6","asdasd");
////                                Toast.makeText(getContext(),"Delete",Toast.LENGTH_SHORT).show();
//                            }
//                        }
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Log.d("6","asdasd");
                                Toast.makeText(getContext(),"Delete",Toast.LENGTH_SHORT).show();
                            }
                        }
                        ));

                buffer.add(new MyButton(getActivity(),
                        "Update",
                        30,
                        R.drawable.ic_edit_white,
                        Color.parseColor("#FF9502"),
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Log.d("6","asdasd");
////                                Toast.makeText(getContext(),"Delete",Toast.LENGTH_SHORT).show();
//                            }
//                        }
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(getContext(),"Update",Toast.LENGTH_SHORT).show();
                            }
                        }
                        ));
            }
        };

//        recyclerViewSwipeHelper = new Pcs_RecyclerViewSwipeHelper(getActivity(), new Pcs_RecyclerViewSwipeAction() {
//
//            @Override
//            public void onLeftClicked(int position) {
//                String currentKeyOfDirectory = ((hep_Location)adapter.getLocation(position).getFirebaseData()).getDirectoryid();
//                Log.d("tag", "Swipe " + currentKeyOfDirectory);
////                final Pcs_PopupRecyclerview pcs_PopupRecyclerview = new Pcs_PopupRecyclerview(getContext(), adapter.getLocation(position));
//                final Pcs_PopupRecyclerview pcs_PopupRecyclerview = new Pcs_PopupRecyclerview(getActivity(), adapter.getLocation(position));
//                pcs_PopupRecyclerview.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//                pcs_PopupRecyclerview.setWidth((int)(200*getResources().getDisplayMetrics().density));
//                pcs_PopupRecyclerview.setFocusable(true);
////                pcs_PopupRecyclerview.showAsDropDown(getView(), 0, 250);
//                pcs_PopupRecyclerview.showAsDropDown(getView(), 100, 100, Gravity.CENTER);
//
////                popupWindow.show(getActivity().findViewById(R.id.drawer_layout),0, -250, hep_location.getDirectoryid());
////                startActivity(intent);
//            }
//
//            @Override
//            public void onRightClicked(int position) {
//
//                //When DeleteItem, Get Location Data and Key
//                final CapsulizeDataObjectNKey lappingDataNKey = adapter.deleteItem(position);
//                //related dismiss data store and lapping
//                lappingDismissData = new LappingDismissData(lappingDataNKey);
//                lappingDismissData.onDismiss();
//
//                Snackbar.make(getActivity().findViewById(android.R.id.content),"삭제완료",Snackbar.LENGTH_LONG).setAction("되돌리기", new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        lappingDismissData.onUndo();
//                    }
//
//                }).show();
//
//            }
//        });
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(recyclerViewSwipeHelper);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                recyclerViewSwipeHelper.onDraw(c);
//            }
//        });
    }


}

//Under Two Class is help for Data Dismiss and Undo
//Constructor is Dismiss And Make Temp data
//Each Class contain data
//One LappingDismissData can contain several CapsulizationData
class LappingDismissData{
    private DatabaseReference databaseReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference();

    private CapsulizeDataObjectNKey hep_location;
    private ArrayList<CapsulizeDataObjectNKey> locationImageArrayList = new ArrayList<>(5);
    private ArrayList<CapsulizeDataObjectNKey> hep_imageArrayList = new ArrayList<>(5);
    private ArrayList<CapsulizeDataObjectNKey> hep_locationTagArrayList = new ArrayList<>(5);
    private String key;


    public LappingDismissData(CapsulizeDataObjectNKey hep_location) {
        this.hep_location = hep_location;
        this.key = hep_location.getDataKey();
    }
    public void onDismiss(){
        //This Method is Capulization about Dismiss Data,
        // locationTag, locationImage, image
        //Lapping LocationTag
        databaseReference.child("locationtag").orderByChild("locationid").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        //Capsulization Data Using LappingDataNKey
                        hep_locationTagArrayList.add(new CapsulizeDataObjectNKey(dataSnapshot.getValue(hep_LocationTag.class), dataSnapshot.getKey()));
                        //Delete
                        dataSnapshot.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Lapping LocationImage and Image
        //Dismiss And make data for undo
        databaseReference.child("locationimage").orderByChild("locationid").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        //Capsulization Data Using LapppingDataNKey
                        CapsulizeDataObjectNKey capsulizeDataObjectNKey = new CapsulizeDataObjectNKey(dataSnapshot.getValue(hep_LocationImage.class), dataSnapshot.getKey());
                        locationImageArrayList.add(capsulizeDataObjectNKey); //For Undo
                        //Delete LocationImage
                        dataSnapshot.getRef().removeValue();
                        final hep_LocationImage hep_locationImage = (hep_LocationImage) capsulizeDataObjectNKey.getFirebaseData();

                        //Delete Image
                        databaseReference.child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        if(dataSnapshot.getKey().equals(hep_locationImage.getImageid())){
                                            hep_imageArrayList.add(new CapsulizeDataObjectNKey(dataSnapshot.getValue(hep_Image.class), dataSnapshot.getKey())); //For Undo
                                            //Delete
                                            dataSnapshot.getRef().removeValue();
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onUndo(){
        databaseReference.child("location").child(hep_location.getDataKey()).setValue(hep_location.getFirebaseData());

        if(!locationImageArrayList.isEmpty()) {
            Log.d("tag","locationImage Undo");
            for(CapsulizeDataObjectNKey locationImage : locationImageArrayList){
                databaseReference.child("locationimage").child(locationImage.getDataKey()).setValue(locationImage.getFirebaseData());
            }
        }
        if(!hep_imageArrayList.isEmpty()) {
            Log.d("tag","image Undo");
            for(CapsulizeDataObjectNKey image : hep_imageArrayList){
                databaseReference.child("image").child(image.getDataKey()).setValue(image.getFirebaseData());
            }
        }
        if(!hep_locationTagArrayList.isEmpty()) {
            Log.d("tag","locationTag Undo");
            for(CapsulizeDataObjectNKey locationTag : hep_locationTagArrayList){
                databaseReference.child("locationtag").child(locationTag.getDataKey()).setValue(locationTag.getFirebaseData());
            }
        }
    }

}
//Data Capsulize
//This class contain FirebaseData(object) and key

