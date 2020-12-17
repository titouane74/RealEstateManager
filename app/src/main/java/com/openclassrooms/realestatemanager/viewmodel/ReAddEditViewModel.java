package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

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

public class ReAddEditViewModel extends ViewModel {

    private ReRepository mReRepo;
    private RePoiRepository mRePoiRepo;
    private ReLocationRepository mReLocRepo;
    private RePhotoRepository mRePhRepo;
    private Executor mExecutor;
    private long mReId;
    private LiveData<Long> mLDReId;

    public ReAddEditViewModel(ReRepository pReRepo, RePoiRepository pRePoiRepo,
                              ReLocationRepository pReLocRepo, RePhotoRepository pRePhRepo,Executor pExecutor) {
        mReRepo = pReRepo;
        mRePoiRepo = pRePoiRepo;
        mReLocRepo = pReLocRepo;
        mRePhRepo = pRePhRepo;
        mExecutor = pExecutor;
    }

    public void insertRealEstate(RealEstate pRealEstate){
        mExecutor.execute(() -> mReRepo.insertRealEstate(pRealEstate));
    }

    public long getReIdInserted() { return mReRepo.getReIdInserted();}

    public void updateRealEstate(RealEstate pRealEstate){
        mExecutor.execute(() -> mReRepo.updateRealEstate(pRealEstate));
    }

    public LiveData<RealEstate> getRealEstate(long pReId) {
        return mReRepo.selectRealEstate(pReId);
    }

    public LiveData<Integer> selectMaxReId() { return mReRepo.selectMaxReId();}

    public void insertReLocation(ReLocation pReLocation) { mExecutor.execute(() -> mReLocRepo.insertReLocation(pReLocation)); }

    public void updateReLocation(ReLocation pReLocation) { mExecutor.execute(() -> mReLocRepo.updateReLocation(pReLocation)); }

    public LiveData<ReLocation> selectReLocation(long pLocReId) { return mReLocRepo.selectReLocation(pLocReId); }

    public void insertRePoi(RePoi pRePoi) { mExecutor.execute(() -> mRePoiRepo.insertRePoi(pRePoi)); }

    public void deleteRePoi(RePoi pRePoi) { mExecutor.execute(() -> mRePoiRepo.deleteRePoi(pRePoi)); }

    public LiveData<RealEstateComplete> selectReComplete(long pReId) { return mReRepo.selectReComplete(pReId); }

    public LiveData<List<RePhoto>> selectRePhoto(long pReId) {return  mRePhRepo.selectRePhoto(pReId); }

    public void insertRePhoto(RePhoto pRePhoto) { mExecutor.execute(() -> mRePhRepo.insertRePhoto(pRePhoto)); }

    public void updateRePhoto(RePhoto pRePhoto) { mExecutor.execute(() -> mRePhRepo.updateRePhoto(pRePhoto)); }

    public void deleteRePhoto(RePhoto pRePhoto) { mExecutor.execute(() -> mRePhRepo.deleteRePhoto(pRePhoto)); }


}