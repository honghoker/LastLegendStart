package com.example.locationsave.HEP.Hep.hep_DTO;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class hep_LocationTag {
    public String locationid;
    public String tagid;

    public hep_LocationTag() {
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getLocationid() {
        return locationid;
    }

    public String getTagid() {
        return tagid;
    }
}
