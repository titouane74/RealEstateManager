package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.repository.ReRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class MapViewModel extends ViewModel {

    private ReRepository mReRepo;

    public MapViewModel(ReRepository pReRepo, Executor pExecutor) {
        mReRepo = pReRepo;
    }

    public LiveData<List<RealEstateComplete>> selectAllReCompleteMandatoryDataComplete() { return mReRepo.selectAllReCompleteMandatoryDataComplete(); }

}