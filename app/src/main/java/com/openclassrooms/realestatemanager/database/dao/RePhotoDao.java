package com.openclassrooms.realestatemanager.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.openclassrooms.realestatemanager.model.RePhoto;
import com.openclassrooms.realestatemanager.model.RePoi;

/**
 * Created by Florence LE BOURNOT on 08/12/2020
 */
@Dao
public interface RePhotoDao {

    @Insert
    void insertRePhoto(RePhoto pRePhoto);

}
