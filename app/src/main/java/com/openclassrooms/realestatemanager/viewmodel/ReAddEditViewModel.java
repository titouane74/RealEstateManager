package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.repository.ReRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class ReAddEditViewModel extends ViewModel {

    private ReRepository mReRepo;
    private Executor mExecutor;

    public ReAddEditViewModel(ReRepository pReRepo, Executor pExecutor) {
        mReRepo = pReRepo;
        mExecutor = pExecutor;
    }

    public LiveData<List<RealEstate>> getAllRe() {
        return mReRepo.selectAllRealEstates();
    }

    public void insertRealEstate(RealEstate pRealEstate){
        mExecutor.execute(() -> mReRepo.insertRealEstate(pRealEstate));
    }
}