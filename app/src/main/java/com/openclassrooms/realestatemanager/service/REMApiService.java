package com.openclassrooms.realestatemanager.service;

import android.location.Location;

import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.utils.PreferencesHelper;

import java.util.List;
import java.util.Objects;

import static com.openclassrooms.realestatemanager.AppRem.mPreferences;

/**
 * Created by Florence LE BOURNOT on 16/12/2020
 */
public class REMApiService implements REMApi {

    public static final String PREF_KEY_LATITUDE = "PREF_KEY_LATITUDE";
    public static final String PREF_KEY_LONGITUDE = "PREF_KEY_LONGITUDE";
    private List<RealEstateComplete> mReCompList;
    private Location mLocation;

    @Override
    public void setSearchResult(List<RealEstateComplete> pReCompList) {
        mReCompList = pReCompList;
    }

    @Override
    public List<RealEstateComplete> getSearchResult() {
        return mReCompList;
    }

    public void saveLocationInSharedPreferences(Location pLocation) {
        PreferencesHelper.saveStringPreferences(PREF_KEY_LATITUDE, String.valueOf(pLocation.getLatitude()));
        PreferencesHelper.saveStringPreferences(PREF_KEY_LONGITUDE, String.valueOf(pLocation.getLongitude()));
    }

    @Override
    public double getLocationFromSharedPreferences(String pTypeLocation) {
        return  Double.parseDouble(Objects.requireNonNull(mPreferences.getString(pTypeLocation, "")));
    }

    @Override
    public void setLocation(Location pLocation) { mLocation = pLocation; }

    @Override
    public Location getLocation() { return mLocation; }
}
