package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RePhoto;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.repository.ReLocationRepository;
import com.openclassrooms.realestatemanager.repository.RePhotoRepository;
import com.openclassrooms.realestatemanager.repository.RePoiRepository;
import com.openclassrooms.realestatemanager.repository.ReRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class ReSearchViewModel extends ViewModel {

    private ReRepository mReRepo;
    private RePoiRepository mRePoiRepo;
    private ReLocationRepository mReLocRepo;
    private RePhotoRepository mRePhRepo;
    private Executor mExecutor;
    private long mReId;

    public ReSearchViewModel(ReRepository pReRepo, RePoiRepository pRePoiRepo,
                             ReLocationRepository pReLocRepo, RePhotoRepository pRePhRepo, Executor pExecutor) {
        mReRepo = pReRepo;
        mRePoiRepo = pRePoiRepo;
        mReLocRepo = pReLocRepo;
        mRePhRepo = pRePhRepo;
        mExecutor = pExecutor;
    }

    public LiveData<List<RealEstateComplete>> selectSearch(SimpleSQLiteQuery pQuery) { return mReRepo.selectSearch(pQuery); }
//    public LiveData<List<RealEstateComplete>> selectSearch(String pQuery) { return mReRepo.selectSearch(pQuery); }

}