package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
    void insertRealEstate(RealEstate pRealEstate);

    @Insert
    void insertRealEstateList(RealEstate... pRealEstates);

    @Query("SELECT * FROM RealEstate WHERE reId = :pReId")
    LiveData<RealEstate> selectRealEstate(int pReId);

    @Update
    void updateRealEstate(RealEstate pRealEstate);
}
