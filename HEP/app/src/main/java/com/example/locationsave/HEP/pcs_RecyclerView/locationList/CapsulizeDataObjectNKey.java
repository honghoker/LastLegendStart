package com.example.locationsave.HEP.pcs_RecyclerView.locationList;

public class CapsulizeDataObjectNKey {
    private Object firebaseData;
    private String dataKey;

    public CapsulizeDataObjectNKey(Object firebaseData, String dataKey) {
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
