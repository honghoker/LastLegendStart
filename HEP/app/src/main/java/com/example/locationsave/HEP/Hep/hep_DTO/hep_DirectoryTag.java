package com.example.locationsave.HEP.Hep.hep_DTO;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class hep_DirectoryTag {
    public String directoryid;
    public String tagid;
    public int count;

    public hep_DirectoryTag(){

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("directoryid", directoryid);
        result.put("tagid", tagid);
        result.put("count", count);

        return result;
    }
}

