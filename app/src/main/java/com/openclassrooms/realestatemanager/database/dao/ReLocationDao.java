package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.model.ReLocationAdress;


/**
 * Created by Florence LE BOURNOT on 08/12/2020
 */
@Dao
public interface ReLocationDao {

    @Insert
    void insertReLocation(ReLocationAdress pReLocationAdress);

    @Query("SELECT * FROM ReLocationAdress WHERE locreid = :pLocReId")
    LiveData<ReLocationAdress> selectReLocation(long pLocReId);
}
