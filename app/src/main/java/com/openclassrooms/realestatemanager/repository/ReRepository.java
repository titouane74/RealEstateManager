package com.openclassrooms.realestatemanager.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.openclassrooms.realestatemanager.database.dao.ReDao;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class ReRepository {

    private static final String TAG = "TAG_ReRepository";
    private ReDao mReDao;
    private LiveData<List<RealEstateComplete>> mLDSearch;
    private long mReIdInserted;

    private MutableLiveData<List<RealEstateComplete>> mLDSearchResult = new MutableLiveData<>();

    public ReRepository(ReDao pReDao) {mReDao = pReDao;}

    public LiveData<List<RealEstate>> selectAllRealEstates() { return mReDao.selectAllRealEstates(); }

    public LiveData<RealEstate> selectRealEstate(long pReId) { return mReDao.selectRealEstate(pReId); }

    public LiveData<Long> selectMaxReId() { return mReDao.selectMaxReId();}

    public long insertRealEstate(RealEstate pRealEstate) {
        mReIdInserted = mReDao.insertRealEstate(pRealEstate);
        Log.d(TAG, "insertRealEstate: " + mReIdInserted);
        return mReIdInserted;
    }

    public void updateRealEstate(RealEstate pRealEstate) {
        int lInt = mReDao.updateRealEstate(pRealEstate);
        Log.d(TAG, "updateRealEstate: int retour update : " + lInt);
    }

    public void deleteRealEstate(RealEstate pRealEstate) {
        int lInt = mReDao.deleteRealEstate(pRealEstate.getReId());
        Log.d(TAG, "deleteRealEstate: int retour delete : " + lInt);
    }

    public LiveData<RealEstateComplete> selectReComplete(long pReId) { return mReDao.selectReComplete(pReId);}

    public LiveData<List<RealEstateComplete>> selectAllReComplete() { return mReDao.selectAllReComplete();}

    public LiveData<List<RealEstateComplete>> selectAllReCompleteMandatoryDataComplete() { return mReDao.selectAllReCompleteMandatoryDataComplete();}

    public LiveData<List<RealEstateComplete>> selectSearch(SimpleSQLiteQuery pQuery) {
        mLDSearch = mReDao.selectSearch(pQuery);
        return mLDSearch;
    }

    public MutableLiveData<List<RealEstateComplete>> getSearchResult() {
        if (mLDSearch!= null) {
            Log.d(TAG, "getSearchResult: setvalue not null");
        }
        return mLDSearchResult;
    }
}
