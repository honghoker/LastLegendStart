package com.example.locationsave.HEP.pcs_RecyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


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
    LappingDismissData lappingDismissData;

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
            case R.id.time_asc:
                onStop();
                adapter = getFirebaseData("time");
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                break;
            case R.id.time_desc:
                onStop();
                adapter = getFirebaseData("title");
                recyclerView.setAdapter(adapter);
                break;
            case R.id.title_asc:
                onStop();
                adapter = getFirebaseData("title");
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                break;
            case R.id.title_desc:
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
        if (kms_activity instanceof KMS_MainActivity)
            kms_activity = (KMS_MainActivity) getActivity();
        else if(hep_locationSaveActivity instanceof hep_LocationSaveActivity)
            hep_locationSaveActivity = (hep_LocationSaveActivity) hep_locationSaveActivity;
            // 여기 문제
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

    private void setUpRecyclerView(){
//        adapter = getFirebaseData(DEFAULT_FILED, DEFAULT_QUERY_DIRECTION);\
        adapter = getFirebaseData(DEFAULT_FILED);
        recyclerView = rootView.findViewById(R.id.locationRecyclcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    //Get firebase data and put into adapter
    private Pcs_RecyclerviewAdapter getFirebaseData(String field){
        Query query = db1.getReference().child("location").orderByChild(field);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<hep_Location>()
                .setQuery(query, hep_Location.class)
                .build();
        return new Pcs_RecyclerviewAdapter(options);
    }


    private void setUpSwipeHelper() {
        recyclerViewSwipeHelper = new Pcs_RecyclerViewSwipeHelper(getActivity(), new Pcs_RecyclerViewSwipeAction() {

            @Override
            public void onLeftClicked(int position) {

            }

            @Override
            public void onRightClicked(int position) {

                //When DeleteItem, Get Location Data and Key
                final CapsulizeData lappingDataNKey = adapter.deleteItem(position);
                //related dismiss data store and lapping
                lappingDismissData = new LappingDismissData(lappingDataNKey);

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
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                recyclerViewSwipeHelper.onDraw(c);
            }
        });
    }


}

//Under Two Class is help for Data Dismiss and Undo
//Constructor is Dismiss And Make Temp data
//Each Class contain data
//One LappingDismissData can contain several CapsulizationData
class LappingDismissData{

    private CapsulizeData hep_location;
    private CapsulizeData hep_locationTag;
    private CapsulizeData hep_locationImage;
    private CapsulizeData hep_image;

    private String key;
    private DatabaseReference databaseReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference();

    public LappingDismissData(CapsulizeData hep_location) {
        this.hep_location = hep_location;
        this.key = hep_location.getDataKey();
        onDataLapping_N_Dismiss();

    }

    //This Method is Capulization about Dismiss Data,
    // locationTag, locationImage, image
    private void onDataLapping_N_Dismiss() {
        //Lapping LocationTag
        databaseReference.child("locationtag").orderByChild("locationid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.getKey().equals(key)){
                            //Capsulization Data Using LappingDataNKey
                            setHep_locationTag(new CapsulizeData(dataSnapshot.getValue(hep_LocationTag.class), dataSnapshot.getKey()));
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
        //Lapping locationID
        databaseReference.child("locationimage").orderByChild("locationid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.getKey().equals(key)){
                            //Capsulization Data Using LapppingDataNKey
                            setHep_locationImage(new CapsulizeData(dataSnapshot.getValue(hep_LocationImage.class), dataSnapshot.getKey()));
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

        //Lapping Image
        databaseReference.child("image").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.getKey().equals(key)){
                            //Capsulization Data Using LapppingDataNKey
                            setHep_image(new CapsulizeData(dataSnapshot.getValue(hep_Image.class), dataSnapshot.getKey()));
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

    public void onUndo(){
        databaseReference.child("location").child(hep_location.getDataKey()).setValue(hep_location.getFirebaseData());
        //Error
        databaseReference.child("locationimage").child(hep_locationImage.getDataKey()).setValue(hep_locationImage.getFirebaseData());
        databaseReference.child("locationtag").child(hep_locationTag.getDataKey()).setValue(hep_locationTag.getFirebaseData());
        databaseReference.child("image").child(hep_image.getDataKey()).setValue(hep_image.getFirebaseData());
    }


    public void setHep_image(CapsulizeData hep_image) {
        this.hep_image = hep_image;
    }

    public void setHep_locationImage(CapsulizeData hep_locationImage) {
        this.hep_locationImage = hep_locationImage;
    }

    public void setHep_locationTag(CapsulizeData hep_locationTag) {
        this.hep_locationTag = hep_locationTag;
    }

    public CapsulizeData getHep_location() {
        return hep_location;
    }

    public CapsulizeData getHep_locationTag() {
        return hep_locationTag;
    }

    public CapsulizeData getHep_locationImage() {
        return hep_locationImage;
    }

    public CapsulizeData getHep_image() {
        return hep_image;
    }
}

class CapsulizeData{
    private Object firebaseData;
    private String dataKey;

    public CapsulizeData(Object firebaseData, String dataKey) {
        this.firebaseData = firebaseData;
        this.dataKey = dataKey;
    }

    public Object getFirebaseData() {
        return firebaseData;
    }

    public String getDataKey() {
        return dataKey;
    }
}
