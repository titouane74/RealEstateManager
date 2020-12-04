package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.model.RealEstate;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */

@Dao
public interface ReDao {

    @Query("SELECT * FROM RealEstate")
    LiveData<List<RealEstate>> selectAllRealEstates();

    @Insert
    long insertRealEstate(RealEstate pRealEstate);

    @Insert
    void insertRealEstateList(RealEstate... pRealEstates);
}
