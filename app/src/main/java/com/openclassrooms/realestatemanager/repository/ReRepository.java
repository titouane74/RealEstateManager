package com.openclassrooms.realestatemanager.repository;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.openclassrooms.realestatemanager.database.dao.ReDao;
import com.openclassrooms.realestatemanager.database.dao.ReLocationDao;
import com.openclassrooms.realestatemanager.database.dao.RePoiDao;
import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class ReRepository {

    private ReDao mReDao;
    private ReLocationDao mReLocationDao;

    public ReRepository(ReDao pReDao) {mReDao = pReDao;}

    public LiveData<List<RealEstate>> selectAllRealEstates() { return mReDao.selectAllRealEstates(); }

    public LiveData<RealEstate> selectRealEstate(long pReId) { return mReDao.selectRealEstate(pReId); }

    public LiveData<Integer> selectMaxReId() { return mReDao.selectMaxReId();}

    public void insertRealEstate(RealEstate pRealEstate) {mReDao.insertRealEstate(pRealEstate);}

    public void updateRealEstate(RealEstate pRealEstate) {mReDao.updateRealEstate(pRealEstate);}

    public void insertReLocation(ReLocation pReLocation) {mReLocationDao.insertReLocation(pReLocation);}

    public LiveData<ReLocation> selectReLocation(long pLocReId) {return mReLocationDao.selectReLocation(pLocReId); }

    public LiveData<RealEstateComplete> selectReComplete(long pReId) { return mReDao.selectReComplete(pReId);}

    public LiveData<List<RealEstateComplete>> selectAllReComplete() { return mReDao.selectAllReComplete();}

    public LiveData<List<RealEstateComplete>> selectSearch(SimpleSQLiteQuery pQuery) { return mReDao.selectSearch(pQuery);}
//    public LiveData<List<RealEstateComplete>> selectSearch(String pQuery) { return mReDao.selectSearch(pQuery);}
}
