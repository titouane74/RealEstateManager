package com.openclassrooms.realestatemanager.service;

import com.openclassrooms.realestatemanager.model.RealEstateComplete;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 16/12/2020
 */
public interface REMApi {

    void setSearchResult(List<RealEstateComplete> pReCompList);

    List<RealEstateComplete> getSearchResult();

}
