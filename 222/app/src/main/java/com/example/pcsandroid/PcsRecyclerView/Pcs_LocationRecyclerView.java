package com.example.pcsandroid.PcsRecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcsandroid.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



public class Pcs_LocationRecyclerView extends Fragment {
    private final static String DEFAULT_FILED = "title";
    private final static Query.Direction DEFAULT_QUERY_DIRECTION = Query.Direction.ASCENDING;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference locationRef = db.collection("Location");
    private RecyclerView recyclerView;
    private Pcs_RecyclerviewAdapter adapter;
    MainActivity activity;
    private ViewGroup rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.pcs_location_recyclerview, container, false);
        //Display Menu
        setHasOptionsMenu(true);
        setUpRecyclerView();
        return rootView;
    }
    //xml pcs_menu connection
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pcs_menu,menu);
    }
    //when menu item selection,
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.time_asc:
                onStop();
                adapter = getFirebaseData("title",Query.Direction.ASCENDING);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.time_desc:
                onStop();
                adapter = getFirebaseData("title",Query.Direction.DESCENDING);
                recyclerView.setAdapter(adapter);
                break;
            case R.id.title_asc:
                onStop();
                adapter = getFirebaseData("title",Query.Direction.ASCENDING);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                break;
            case R.id.title_desc:
                onStop();
                adapter = getFirebaseData("title",Query.Direction.DESCENDING);
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
        activity =  (MainActivity) getActivity();
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
        adapter = getFirebaseData(DEFAULT_FILED, DEFAULT_QUERY_DIRECTION);
        recyclerView = rootView.findViewById(R.id.locationRecyclcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    //Get firebase data and put into adapter
    private Pcs_RecyclerviewAdapter getFirebaseData(String field, Query.Direction direction){
        Query query = locationRef.orderBy(field, direction);
        FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Location>()
                .setQuery(query, Location.class)
                .build();
        return new Pcs_RecyclerviewAdapter(options);
    }
}
