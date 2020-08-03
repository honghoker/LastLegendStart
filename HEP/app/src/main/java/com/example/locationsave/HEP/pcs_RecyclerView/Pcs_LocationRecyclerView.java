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
import com.example.locationsave.HEP.Hep.hep_DTO.hep_locationTag;
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
                final hep_Location dismissData = adapter.deleteItem(position);
                final String key = adapter.getIDByPosition(position);
                Snackbar.make(getActivity().findViewById(android.R.id.content),"삭제완료",Snackbar.LENGTH_LONG).setAction("되돌리기", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        db1.getReference().child("location").child(key).setValue(dismissData);
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

    private void onDismiss(int position){
        final hep_Location dismissLocationTable = adapter.deleteItem(position);


    }
    private void onUndo(){

    }

}
class LappingDismissData{
    private hep_Location hep_location;
    private hep_Image hep_image;
    private hep_LocationImage hep_locationImage;
    private String key;
    private hep_locationTag hep_locationTag;
    private FirebaseDatabase db;

    public LappingDismissData(final String key, FirebaseDatabase db) {
        this.key = key;
        this.db = db;
        db.getReference().child("location");
        DatabaseReference databaseReference = new hep_FireBase().getFireBaseDatabaseInstance().getReference();
        databaseReference.child("location").orderByChild("locationid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.getKey().equals(key)){
                            setHep_locationTag();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public hep_Location getHep_location() {
        return hep_location;
    }

    public hep_Image getHep_image() {
        return hep_image;
    }

    public hep_LocationImage getHep_locationImage() {
        return hep_locationImage;
    }

    public String getKey() {
        return key;
    }

    public com.example.locationsave.HEP.Hep.hep_DTO.hep_locationTag getHep_locationTag() {
        return hep_locationTag;
    }

    public void setHep_location(hep_Location hep_location) {
        this.hep_location = hep_location;
    }

    public void setHep_image(hep_Image hep_image) {
        this.hep_image = hep_image;
    }

    public void setHep_locationImage(hep_LocationImage hep_locationImage) {
        this.hep_locationImage = hep_locationImage;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setHep_locationTag(com.example.locationsave.HEP.Hep.hep_DTO.hep_locationTag hep_locationTag) {
        this.hep_locationTag = hep_locationTag;
    }

    public void setDb(FirebaseDatabase db) {
        this.db = db;
    }
}
