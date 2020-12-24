package com.openclassrooms.realestatemanager.repository;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.openclassrooms.realestatemanager.database.dao.ReDao;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class ReRepository {

    private final ReDao mReDao;

    public ReRepository(ReDao pReDao) {mReDao = pReDao;}

    public LiveData<Long> selectMaxReId() { return mReDao.selectMaxReId();}

    public long insertRealEstate(RealEstate pRealEstate) { return mReDao.insertRealEstate(pRealEstate); }

    public int updateRealEstate(RealEstate pRealEstate) { return mReDao.updateRealEstate(pRealEstate);}

    public void deleteRealEstate(RealEstate pRealEstate) { mReDao.deleteRealEstate(pRealEstate.getReId());}

    public LiveData<RealEstateComplete> selectReComplete(long pReId) { return mReDao.selectReComplete(pReId);}

    public LiveData<List<RealEstateComplete>> selectAllReComplete() { return mReDao.selectAllReComplete();}

    public LiveData<List<RealEstateComplete>> selectAllReCompleteMandatoryDataComplete() { return mReDao.selectAllReCompleteMandatoryDataComplete();}

    public LiveData<List<RealEstateComplete>> selectSearch(SimpleSQLiteQuery pQuery) { return mReDao.selectSearch(pQuery);
    }

}
