package com.openclassrooms.realestatemanager.service;

import com.openclassrooms.realestatemanager.model.RealEstateComplete;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 16/12/2020
 */
public class REMApiService implements REMApi {

    private List<RealEstateComplete> mReCompList;

    @Override
    public void setSearchResult(List<RealEstateComplete> pReCompList) {
        mReCompList = pReCompList;
    }

    @Override
    public List<RealEstateComplete> getSearchResult() {
        return mReCompList;
    }
}
