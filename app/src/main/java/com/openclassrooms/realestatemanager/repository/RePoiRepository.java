package com.openclassrooms.realestatemanager.repository;

import com.openclassrooms.realestatemanager.database.dao.RePoiDao;
import com.openclassrooms.realestatemanager.model.RePoi;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
public class RePoiRepository {

    private RePoiDao mRePoiDao;

    public RePoiRepository(RePoiDao pRePoiDao) {mRePoiDao = pRePoiDao;}

    public void insertRePoi(RePoi pRePoi) { mRePoiDao.insertRePoi(pRePoi);}

}
