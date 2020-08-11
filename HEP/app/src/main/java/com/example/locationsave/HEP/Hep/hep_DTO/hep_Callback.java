package com.example.locationsave.HEP.Hep.hep_DTO;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.UploadTask;

public interface hep_Callback {
    void onSuccess(UploadTask.TaskSnapshot taskSnapshot);
    void onSuccess(DataSnapshot dataSnapshot, DataSnapshot dataSnapshot1);
    void onSuccess(hep_Recent hep_recent);
    void onSuccess(hep_LocationTag hep_locationTag);
}
