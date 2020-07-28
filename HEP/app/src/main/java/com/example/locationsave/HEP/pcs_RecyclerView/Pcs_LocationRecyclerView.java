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

import com.example.locationsave.HEP.Hep.hep_DTO.hep_Location;
import com.example.locationsave.HEP.Hep.hep_FireBase;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSaveActivity;
import com.example.locationsave.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Pcs_LocationRecyclerView extends Fragment {
    private final static String DEFAULT_FILED = "name";
//    private final static Query.Direction DEFAULT_QUERY_DIRECTION = Query.Direction.ASCENDING;

    private FirebaseDatabase db1 = new hep_FireBase().getFireBaseDatabaseInstance();


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference locationRef = db.collection("Locations");
    private RecyclerView recyclerView;
    private Pcs_RecyclerviewAdapter adapter;
    hep_LocationSaveActivity activity;
    private ViewGroup rootView;
    private Pcs_RecyclerViewSwipeHelper recyclerViewSwipeHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.pcs_location_recyclerview, container, false);
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
                adapter = getFirebaseData("title");
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
        activity =  (hep_LocationSaveActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
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
        Query query = db1.getReference().child("Locations").orderByChild(field);
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
                adapter.deleteItem(position);
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
