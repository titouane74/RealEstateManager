package com.openclassrooms.realestatemanager.repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.ReDao;
import com.openclassrooms.realestatemanager.database.dao.ReLocationDao;
import com.openclassrooms.realestatemanager.model.ReLocationAdress;
import com.openclassrooms.realestatemanager.model.RealEstate;

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

    public void insertReLocation(ReLocationAdress pReLocationAdress) {mReLocationDao.insertReLocation(pReLocationAdress);}

    public LiveData<ReLocationAdress> selectReLocation(long pLocReId) {return mReLocationDao.selectReLocation(pLocReId); }

}
