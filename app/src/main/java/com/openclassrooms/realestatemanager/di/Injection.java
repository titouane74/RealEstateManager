package com.openclassrooms.realestatemanager.di;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.ReDatabase;
import com.openclassrooms.realestatemanager.repository.ReLocationRepository;
import com.openclassrooms.realestatemanager.repository.RePhotoRepository;
import com.openclassrooms.realestatemanager.repository.RePoiRepository;
import com.openclassrooms.realestatemanager.repository.ReRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class Injection {

    public static ReRepository provideReRepository(Context pContext) {
        ReDatabase lDb = ReDatabase.getInstance(pContext);
        return new ReRepository(lDb.ReDao());
    }

    public static RePoiRepository provideRePoiRepository(Context pContext) {
        ReDatabase lDb = ReDatabase.getInstance(pContext);
        return new RePoiRepository(lDb.RePoiDao());
    }
    public static ReLocationRepository provideReLocationRepository(Context pContext) {
        ReDatabase lDb = ReDatabase.getInstance(pContext);
        return new ReLocationRepository(lDb.ReLocationDao());
    }
    public static RePhotoRepository provideRePhotoRepository(Context pContext) {
        ReDatabase lDb = ReDatabase.getInstance(pContext);
        return new RePhotoRepository(lDb.RePhotoDao());
    }
    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ReViewModelFactory reViewModelFactory(Context context) {
        ReRepository lReRepo = provideReRepository(context);
        RePoiRepository lRePoiRepo = provideRePoiRepository(context);
        ReLocationRepository lReLocationRepo = provideReLocationRepository(context);
        RePhotoRepository lRePhotoRepo = provideRePhotoRepository(context);
        Executor executor = provideExecutor();
        return new ReViewModelFactory(lReRepo, lRePoiRepo, lReLocationRepo, lRePhotoRepo, executor);
    }
}
