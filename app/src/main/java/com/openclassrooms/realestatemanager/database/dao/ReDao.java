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

    @Insert
    void insertRealEstate(RealEstate pRealEstate);

    @Update
    void updateRealEstate(RealEstate pRealEstate);

    @Transaction
    @Query("SELECT * FROM realestate")
    public List<RealEstateComplete> selectReCompleteList();

    @Transaction
    @Query("SELECT * FROM realestate WHERE reid=:pReId")
    LiveData<RealEstateComplete> selectReComplete(long pReId);

}
