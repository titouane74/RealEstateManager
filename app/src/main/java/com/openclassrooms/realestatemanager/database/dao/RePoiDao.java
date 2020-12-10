package com.openclassrooms.realestatemanager.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.openclassrooms.realestatemanager.model.RePoi;

/**
 * Created by Florence LE BOURNOT on 08/12/2020
 */
@Dao
public interface RePoiDao {

    @Insert
    void insertRePoi(RePoi pRePoi);

    @Delete
    void deleteRePoi(RePoi pRePoi);
}
