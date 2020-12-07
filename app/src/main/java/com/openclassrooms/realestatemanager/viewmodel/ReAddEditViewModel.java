package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.repository.ReRepository;

import java.util.concurrent.Executor;

public class ReAddEditViewModel extends ViewModel {

    private ReRepository mReRepo;
    private Executor mExecutor;

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

    public LiveData<RealEstate> getRealEstate(int pReId) {
        return mReRepo.selectRealEstate(pReId);
    }
}