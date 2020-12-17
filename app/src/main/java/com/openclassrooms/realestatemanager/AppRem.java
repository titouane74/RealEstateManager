package com.openclassrooms.realestatemanager;

import android.app.Application;
import android.content.SharedPreferences;
import android.location.Location;

import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.service.REMApi;
import com.openclassrooms.realestatemanager.utils.PreferencesHelper;
import com.openclassrooms.realestatemanager.utils.REMHelper;

/**
 * Created by Florence LE BOURNOT on 17/12/2020
 */
public class AppRem extends Application {

    public static final REMApi sApi = Injection.getREMApiService();
    public static SharedPreferences mPreferences;


    @Override
    public void onCreate() {
        super.onCreate();

        initializeSharedPreferences();

    }
    /**
     * Initialize and load Shared Preferences
     */
    private void initializeSharedPreferences() {

        PreferencesHelper.loadPreferences(this);
        String lValueString;
        int lValueInt;

        if (mPreferences == null) {

            double lLat = Double.parseDouble(getString(R.string.default_latitude_position));
            double lLng = Double.parseDouble(getString(R.string.default_longitude_position));
            Location lLocation = REMHelper.setCurrentLocation(lLat, lLng);
            sApi.setLocation(lLocation);
            sApi.saveLocationInSharedPreferences(lLocation);
        }
    }

}
