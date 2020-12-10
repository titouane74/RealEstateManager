package com.openclassrooms.realestatemanager.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

/**
 * Created by Florence LE BOURNOT on 25/11/2020
 */
public class RealEstateComplete {

    @Embedded RealEstate mRealEstate;
    @Relation(parentColumn = "reId",entityColumn = "poireid")
    public List<RePoi> mPoiList;
    @Relation(parentColumn = "reId", entityColumn = "locreid")
    public ReLocation mReLocation;

    @Override
    public boolean equals(Object lpO) {
        if (this == lpO) return true;
        if (!(lpO instanceof RealEstateComplete)) return false;
        RealEstateComplete lthat = (RealEstateComplete) lpO;
        return getRePhotoList().equals(lthat.getRePhotoList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRePhotoList());
    }

    @Relation(parentColumn = "reId", entityColumn = "phreid")
    public List<RePhoto> mRePhotoList;

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

    public ReLocation getReLocation() { return mReLocation; }

    public void setReLocation(ReLocation pReLocation) { mReLocation = pReLocation; }

    public List<RePhoto> getRePhotoList() { return mRePhotoList; }

    public void setRePhotoList(List<RePhoto> pRePhotoList) { mRePhotoList = pRePhotoList; }

}
