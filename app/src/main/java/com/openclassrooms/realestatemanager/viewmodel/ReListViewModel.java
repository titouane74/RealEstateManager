package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.repository.ReRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class ReListViewModel extends ViewModel {

    private ReRepository mReRepo;
    private Executor mExecutor;

    public ReListViewModel(ReRepository pReRepo, Executor pExecutor) {
        mReRepo = pReRepo;
        mExecutor = pExecutor;
    }

    public LiveData<List<RealEstate>> getAllRe() {
        return mReRepo.selectAllRealEstates();
    }

}