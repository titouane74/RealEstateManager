package com.openclassrooms.realestatemanager.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.openclassrooms.realestatemanager.repository.ReLocationRepository;
import com.openclassrooms.realestatemanager.repository.RePhotoRepository;
import com.openclassrooms.realestatemanager.repository.RePoiRepository;
import com.openclassrooms.realestatemanager.repository.ReRepository;
import com.openclassrooms.realestatemanager.viewmodel.MapViewModel;
import com.openclassrooms.realestatemanager.viewmodel.ReAddEditViewModel;
import com.openclassrooms.realestatemanager.viewmodel.ReDetailViewModel;
import com.openclassrooms.realestatemanager.viewmodel.ReListViewModel;
import com.openclassrooms.realestatemanager.viewmodel.ReSearchViewModel;

import java.util.concurrent.Executor;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class ReViewModelFactory implements ViewModelProvider.Factory {

    private final ReRepository mReRepo;
    private final RePoiRepository mRePoiRepo;
    private final ReLocationRepository mReLocRepo;
    private final RePhotoRepository mRePhoRepo;
    private final Executor mExecutor;

    public ReViewModelFactory(ReRepository pReRepo, RePoiRepository pRePoiRepo,
                              ReLocationRepository pReLocRepo, RePhotoRepository pRePhRepo,
                              Executor pExecutor) {
        mReRepo = pReRepo;
        mRePoiRepo = pRePoiRepo;
        mReLocRepo = pReLocRepo;
        mRePhoRepo = pRePhRepo;
        mExecutor = pExecutor;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReAddEditViewModel.class)) {
            return (T) new ReAddEditViewModel(mReRepo, mRePoiRepo, mReLocRepo, mRePhoRepo, mExecutor);
        }
        if (modelClass.isAssignableFrom(ReListViewModel.class)) {
            return (T) new ReListViewModel(mReRepo, mExecutor);
        }
        if (modelClass.isAssignableFrom(ReDetailViewModel.class)) {
            return (T) new ReDetailViewModel(mReRepo, mExecutor);
        }
        if (modelClass.isAssignableFrom(ReSearchViewModel.class)) {
            return (T) new ReSearchViewModel(mReRepo, mRePoiRepo, mReLocRepo, mRePhoRepo, mExecutor);
        }
        if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(mReRepo, mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
