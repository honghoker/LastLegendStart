package com.example.locationsave.HEP.Hep.hep_DTO;

public interface hep_Callback {
    void onSuccess(hep_Recent hep_recent);
    void onSuccess(hep_LocationTag hep_locationTag);
    void onFail(String errorMessage);
}