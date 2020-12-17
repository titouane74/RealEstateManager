package com.openclassrooms.realestatemanager.service;

import android.location.Location;

import com.openclassrooms.realestatemanager.model.RealEstateComplete;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 16/12/2020
 */
public interface REMApi {

    void setSearchResult(List<RealEstateComplete> pReCompList);

    List<RealEstateComplete> getSearchResult();

    void saveLocationInSharedPreferences(Location pLocation);

    double getLocationFromSharedPreferences(String pTypeLocation);

    void setLocation(Location pLocation);

    Location getLocation();
}
