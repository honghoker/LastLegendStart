package com.example.locationsave.HEP.pcs_RecyclerView;

import com.example.locationsave.HEP.Hep.hep_DTO.hep_LocationTag;
import com.example.locationsave.HEP.Hep.hep_DTO.hep_Tag;

import java.util.ArrayList;

public interface Pcs_Callback {
    void onSuccessOFTag(String result);
    void onFail();

}
