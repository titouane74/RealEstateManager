package com.openclassrooms.realestatemanager.repository;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.ReDao;
import com.openclassrooms.realestatemanager.database.dao.ReLocationDao;
import com.openclassrooms.realestatemanager.database.dao.RePhotoDao;
import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class RePhotoRepository {

    private RePhotoDao mRePhotoDao;

    public RePhotoRepository(RePhotoDao pRePhotoDao) {mRePhotoDao = pRePhotoDao;}


}
