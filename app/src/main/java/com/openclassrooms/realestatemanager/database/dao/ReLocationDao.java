package com.openclassrooms.realestatemanager.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.ReLocation;

/**
 * Created by Florence LE BOURNOT on 08/12/2020
 */
@Dao
public interface ReLocationDao {

    //FOR ROOM
    @Insert
    void insertReLocation(ReLocation pReLocation);

    @Update
    void updateReLocation(ReLocation pReLocation);

}
