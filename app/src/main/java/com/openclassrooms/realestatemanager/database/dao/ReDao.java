package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */

@Dao
public interface ReDao {

    //FOR CONTENT PROVIDER
    @Query("SELECT * FROM realestate WHERE reid = :pReId")
    Cursor selectRealEstateCursor(long pReId);

    @Query("DELETE FROM realestate WHERE reid = :pReId")
    int deleteRealEstate(long pReId);

    //FOR ROOM
    @Query("SELECT * FROM realestate WHERE reid = :pReId")
    LiveData<RealEstate> selectRealEstate(long pReId);

    @Query("SELECT max(reid) FROM realestate")
    LiveData<Long> selectMaxReId();

    @Insert
    long insertRealEstate(RealEstate pRealEstate);

    @Update
    int updateRealEstate(RealEstate pRealEstate);


    @Transaction
    @Query("SELECT * FROM realestate WHERE reid=:pReId")
    LiveData<RealEstateComplete> selectReComplete(long pReId);

    @Transaction
    @Query("SELECT * FROM realestate")
    LiveData<List<RealEstateComplete>> selectAllReComplete();

    @Transaction
    @Query("SELECT * FROM realestate WHERE reIsMandatoryDataComplete=1")
    LiveData<List<RealEstateComplete>> selectAllReCompleteMandatoryDataComplete();


    @RawQuery(observedEntities = RealEstateComplete.class)
    LiveData<List<RealEstateComplete>> selectSearch(SupportSQLiteQuery pQuery);
}
