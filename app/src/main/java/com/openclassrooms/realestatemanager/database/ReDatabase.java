package com.openclassrooms.realestatemanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.openclassrooms.realestatemanager.database.dao.ReDao;
import com.openclassrooms.realestatemanager.database.dao.ReLocationDao;
import com.openclassrooms.realestatemanager.database.dao.RePhotoDao;
import com.openclassrooms.realestatemanager.database.dao.RePoiDao;
import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RePhoto;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.utils.DateConverter;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
@Database(entities = {RealEstate.class, RePoi.class, ReLocation.class, RePhoto.class},
        version = 2 , exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class ReDatabase extends RoomDatabase {
    private static ReDatabase INSTANCE;
    public abstract ReDao ReDao();
    public abstract RePoiDao RePoiDao();
    public abstract ReLocationDao ReLocationDao();
    public abstract RePhotoDao RePhotoDao();

    public static ReDatabase getInstance(Context pContext) {
        if (INSTANCE == null) {
            synchronized (ReDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(pContext.getApplicationContext(),
                            ReDatabase.class, "RealEstate.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
