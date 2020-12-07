package com.openclassrooms.realestatemanager.repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.ReDao;
import com.openclassrooms.realestatemanager.model.RealEstate;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class ReRepository {

    private ReDao mReDao;

    public ReRepository(ReDao pReDao) {mReDao = pReDao;}

    public LiveData<List<RealEstate>> selectAllRealEstates() { return mReDao.selectAllRealEstates(); }

    public LiveData<RealEstate> selectRealEstate(int pReId) { return mReDao.selectRealEstate(pReId); }

    public void insertRealEstate(RealEstate pRealEstate) {mReDao.insertRealEstate(pRealEstate);}

    public void updateRealEstate(RealEstate pRealEstate) {mReDao.updateRealEstate(pRealEstate);}

}
