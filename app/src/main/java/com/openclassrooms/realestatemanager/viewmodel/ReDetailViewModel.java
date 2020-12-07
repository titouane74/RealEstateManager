package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.repository.ReRepository;

import java.util.concurrent.Executor;

public class ReDetailViewModel extends ViewModel {
    private ReRepository mReRepo;
    private Executor mExecutor;

    public ReDetailViewModel(ReRepository pReRepo, Executor pExecutor) {
        mReRepo = pReRepo;
        mExecutor = pExecutor;
    }

    public LiveData<RealEstate> getRealEstate(int pReId) {
        return mReRepo.selectRealEstate(pReId);
    }

}