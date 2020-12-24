package com.openclassrooms.realestatemanager.repository;

import com.openclassrooms.realestatemanager.database.dao.ReLocationDao;
import com.openclassrooms.realestatemanager.model.ReLocation;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class ReLocationRepository {

    private final ReLocationDao mReLocationDao;

    public ReLocationRepository(ReLocationDao pReLocationDao) {mReLocationDao = pReLocationDao;}

    public void insertReLocation(ReLocation pReLocation) {mReLocationDao.insertReLocation(pReLocation);}

    public void updateReLocation(ReLocation pReLocation) {mReLocationDao.updateReLocation(pReLocation);}

}
