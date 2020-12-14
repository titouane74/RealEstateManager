package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.RePhoto;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 08/12/2020
 */
@Dao
public interface RePhotoDao {

    //FOR ROOM
    @Query("SELECT * FROM photo WHERE phreid = :pReId")
    LiveData<List<RePhoto>> selectRePhoto(long pReId);

    @Insert
    void insertRePhoto(RePhoto pRePhoto);

    @Update
    void updateRePhoto(RePhoto pRePhoto);

    @Delete
    void deleteRePhoto(RePhoto pRePhoto);
}
