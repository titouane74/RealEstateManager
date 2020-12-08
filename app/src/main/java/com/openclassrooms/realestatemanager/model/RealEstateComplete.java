package com.openclassrooms.realestatemanager.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 25/11/2020
 */
public class RealEstateComplete {

    @Embedded RealEstate mRealEstate;
    @Relation(parentColumn = "reId",entityColumn = "poireid")
    public List<RePoi> mPoiList;

    public RealEstate getRealEstate() {
        return mRealEstate;
    }

    public List<RePoi> getPoiList() {
        return mPoiList;
    }

    public void setRealEstate(RealEstate pRealEstate) {
        mRealEstate = pRealEstate;
    }

    public void setPoiList(List<RePoi> pPoiList) {
        mPoiList = pPoiList;
    }
}
