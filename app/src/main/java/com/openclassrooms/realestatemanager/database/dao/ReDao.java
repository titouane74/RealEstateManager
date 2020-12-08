package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */

@Dao
public interface ReDao {

    @Query("SELECT * FROM realestate")
    LiveData<List<RealEstate>> selectAllRealEstates();

    @Query("SELECT * FROM realestate WHERE reid = :pReId")
    LiveData<RealEstate> selectRealEstate(long pReId);

    @Query("SELECT max(reid) FROM realestate")
    LiveData<Integer> selectMaxReId();
/*
    @Transaction
    @Query("SELECT * FROM realestate WHERE reid = :pReId")
    public RealEstateComplete getReComplete(long pReId);
*/

    @Insert
    void insertRealEstate(RealEstate pRealEstate);

    @Insert
    void insertRealEstateList(RealEstate... pRealEstates);

    @Update
    void updateRealEstate(RealEstate pRealEstate);


}
