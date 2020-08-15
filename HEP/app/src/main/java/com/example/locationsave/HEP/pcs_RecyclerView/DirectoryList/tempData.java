package com.example.locationsave.HEP.pcs_RecyclerView.DirectoryList;

import com.example.locationsave.R;

import java.util.ArrayList;
import java.util.List;

public class tempData {
    public long id;
    public int iconRes;
    public String category;

    public tempData(long id, int iconRes, String category){
        super();
        this.id = id;
        this.iconRes = iconRes;
        this.category = category;
    }

    public static List<tempData> generateCategoryList(){
        List<tempData> categories = new ArrayList<>();
        String[] programming = {"C++", "JAVA", "JAVASCRIPT", "C#", "Objective C", "SWIFT"};

        for(int i = 0; i < programming.length; i++){
            categories.add(new tempData(i, R.drawable.ic_delete, programming[i]));
        }
        return categories;
    }
}
