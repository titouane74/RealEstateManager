package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.repository.ReLocationRepository;
import com.openclassrooms.realestatemanager.repository.RePhotoRepository;
import com.openclassrooms.realestatemanager.repository.RePoiRepository;
import com.openclassrooms.realestatemanager.repository.ReRepository;

import java.util.concurrent.Executor;

public class ReAddEditViewModel extends ViewModel {

    private ReRepository mReRepo;
    private RePoiRepository mRePoiRepo;
    private ReLocationRepository mReLocRepo;
    private RePhotoRepository mRePhoRepo;
    private Executor mExecutor;
    private long mReId;

    public ReAddEditViewModel(ReRepository pReRepo, RePoiRepository pRePoiRepo,
                              ReLocationRepository pReLocRepo, RePhotoRepository pRePhRepo,Executor pExecutor) {
        mReRepo = pReRepo;
        mRePoiRepo = pRePoiRepo;
        mReLocRepo = pReLocRepo;
        mRePhoRepo = pRePhRepo;
        mExecutor = pExecutor;
    }

    public void insertRealEstate(RealEstate pRealEstate){
        mExecutor.execute(() -> mReRepo.insertRealEstate(pRealEstate));
    }

    public void updateRealEstate(RealEstate pRealEstate){
        mExecutor.execute(() -> mReRepo.updateRealEstate(pRealEstate));
    }

    public LiveData<RealEstate> getRealEstate(long pReId) {
        return mReRepo.selectRealEstate(pReId);
    }

    public LiveData<Integer> selectMaxReId() { return mReRepo.selectMaxReId();}

    public void insertReLocation(ReLocation pReLocation) { mExecutor.execute(() -> mReLocRepo.insertReLocation(pReLocation)); }

    public LiveData<ReLocation> selectReLocation(long pLocReId) { return mReLocRepo.selectReLocation(pLocReId); }

    public void insertRePoi(RePoi pRePoi) { mExecutor.execute(() -> mRePoiRepo.insertRePoi(pRePoi)); }

    public LiveData<RealEstateComplete> selectReComplete(long pReId) { return mReRepo.selectReComplete(pReId); }

}