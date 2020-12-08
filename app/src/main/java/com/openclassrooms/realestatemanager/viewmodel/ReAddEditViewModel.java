package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.ReLocationAdress;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.repository.ReRepository;

import java.util.concurrent.Executor;

public class ReAddEditViewModel extends ViewModel {

    private ReRepository mReRepo;
    private Executor mExecutor;
    private long mReId;

    public ReAddEditViewModel(ReRepository pReRepo, Executor pExecutor) {
        mReRepo = pReRepo;
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

    public void insertReLocation(ReLocationAdress pReLocationAdress) {
        mExecutor.execute(() -> mReRepo.insertReLocation(pReLocationAdress));
    }

    public LiveData<ReLocationAdress> selectReLocation(long pLocReId) { return mReRepo.selectReLocation(pLocReId); }

}