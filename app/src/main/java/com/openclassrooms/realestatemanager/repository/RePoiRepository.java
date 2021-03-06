package com.openclassrooms.realestatemanager.repository;

import com.openclassrooms.realestatemanager.database.dao.RePoiDao;
import com.openclassrooms.realestatemanager.model.RePoi;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class RePoiRepository {

    private final RePoiDao mRePoiDao;

    public RePoiRepository(RePoiDao pRePoiDao) {mRePoiDao = pRePoiDao;}

    public void insertRePoi(RePoi pRePoi) { mRePoiDao.insertRePoi(pRePoi);}

    public void deleteRePoi(RePoi pRePoi) { mRePoiDao.deleteRePoi(pRePoi);}

}
