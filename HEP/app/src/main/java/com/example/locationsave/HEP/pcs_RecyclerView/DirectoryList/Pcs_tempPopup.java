//package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.PopupWindow;
//
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.locationsave.HEP.pcs_RecyclerView.locationList.Pcs_RecyclerviewAdapter;
//import com.example.locationsave.R;
//
//public class Pcs_tempPopup extends PopupWindow {
//    private Context context;
//    private RecyclerView rvCategory;
//    private Pcs_RecyclerviewAdapter dropdownAdapter;
//
//    public Pcs_tempPopup(Context context){
//        super(context);
//        this.context = context;
//        setupView();
//    }
//
//    public void setCategorySelectedListener(Pcs_RecyclerviewAdapter pcs_recyclerviewAdapter) {
//        dropdownAdapter.setCategorySelectedListener(categorySelectedListener);
//    }
//
//    private void setupView() {
//        View view = LayoutInflater.from(context).inflate(R.layout.popup_category, null);
//
//        rvCategory = view.findViewById(R.id.rvCategory);
//        rvCategory.setHasFixedSize(true);
//        rvCategory.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        rvCategory.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
//
//        dropdownAdapter = new CategoryDropdownAdapter(Category.generateCategoryList());
//        rvCategory.setAdapter(dropdownAdapter);
//
//        setContentView(view);
//    }
//
//}
