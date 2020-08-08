package com.example.locationsave.HEP.KMS.Map;

public class KMS_MapPosition {
    private static final KMS_MapPosition MapPositionInstance = new KMS_MapPosition();

    private KMS_MapPosition() {}

    public static KMS_MapPosition getInstanceMapPosition(){
        return MapPositionInstance;
    }


    public void PositionMove(){

    }
}
