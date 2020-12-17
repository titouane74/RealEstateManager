package com.openclassrooms.realestatemanager.repository;

import android.util.Log;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

    private static final String TAG = "TAG_ReRepository";
    private ReDao mReDao;
    private LiveData<List<RealEstateComplete>> mLDSearch;
    private long mReIdInserted;

    private MutableLiveData<List<RealEstateComplete>> mLDSearchResult = new MutableLiveData<>();

    public ReRepository(ReDao pReDao) {mReDao = pReDao;}

    public LiveData<List<RealEstate>> selectAllRealEstates() { return mReDao.selectAllRealEstates(); }

    public LiveData<RealEstate> selectRealEstate(long pReId) { return mReDao.selectRealEstate(pReId); }

    public LiveData<Integer> selectMaxReId() { return mReDao.selectMaxReId();}

    public void insertRealEstate(RealEstate pRealEstate) {
        mReIdInserted = mReDao.insertRealEstate(pRealEstate);
        Log.d(TAG, "insertRealEstate: " + mReIdInserted);
    }

    public long getReIdInserted() {
        Log.d(TAG, "getReIdInserted: "+mReIdInserted);
        return mReIdInserted;}

    public void updateRealEstate(RealEstate pRealEstate) {
        int lInt = mReDao.updateRealEstate(pRealEstate);
        Log.d(TAG, "updateRealEstate: long retour update : " + lInt);
    }

    public LiveData<RealEstateComplete> selectReComplete(long pReId) { return mReDao.selectReComplete(pReId);}

    public LiveData<List<RealEstateComplete>> selectAllReComplete() { return mReDao.selectAllReComplete();}

    public LiveData<List<RealEstateComplete>> selectSearch(SimpleSQLiteQuery pQuery) {
//        Log.d(TAG, "selectSearch: " + pQuery);
        mLDSearch = mReDao.selectSearch(pQuery);
/*        List<RealEstateComplete> lReList = mLDSearch.getValue();
        for(RealEstateComplete lReComp : lReList) {
            Log.d(TAG, "selectSearch: " + lReComp.getRealEstate().getReDescription());
        }
        mLDSearchResult.setValue(mLDSearch.getValue());
        Log.d(TAG, "selectSearch: mLDSearchResult set value");*/
        return mLDSearch;
    }

    public MutableLiveData<List<RealEstateComplete>> getSearchResult() {
//        Log.d(TAG, "getSearchResult: ");
        if (mLDSearch!= null) {
            Log.d(TAG, "getSearchResult: setvalue not null");
//            mLDSearchResult.setValue(mLDSearch.getValue());
        } else {
//            Log.d(TAG, "getSearchResult: mLDSearch null");
        }
        return mLDSearchResult;
    }
}
