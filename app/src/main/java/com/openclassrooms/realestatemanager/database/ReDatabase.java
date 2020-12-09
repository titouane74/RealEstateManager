package com.openclassrooms.realestatemanager.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.ReDao;
import com.openclassrooms.realestatemanager.database.dao.ReLocationDao;
import com.openclassrooms.realestatemanager.database.dao.RePhotoDao;
import com.openclassrooms.realestatemanager.database.dao.RePoiDao;
import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RePhoto;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstate;

/**
 * Created by Florence LE BOURNOT on 04/12/2020
 */
@Database(entities = {RealEstate.class, RePoi.class, ReLocation.class, RePhoto.class}, version = 4 , exportSchema = false)
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
//                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
/*
                Project[] projects = Project.getAllProjects();
                for (Project project : projects) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", project.getId());
                    contentValues.put("name", project.getName());
                    contentValues.put("color", project.getColor());
                    db.insert("project", OnConflictStrategy.IGNORE, contentValues);
                }
*/
            }
        };
    }
}
