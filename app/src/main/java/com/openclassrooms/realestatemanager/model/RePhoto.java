package com.openclassrooms.realestatemanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Florence LE BOURNOT on 08/12/2020
 */
@Entity(tableName = "photo",
        foreignKeys = @ForeignKey(entity = RealEstate.class,
                parentColumns = "reId",
                childColumns = "phreid"))
public class RePhoto {
    @PrimaryKey(autoGenerate = true)
    private long phId;

    @ColumnInfo(name = "phreid",index = true)
    private long phReId;

    private String phDescription;
    private String phPath;
    private int phContentDescription;

    public RePhoto() {}

    public RePhoto(long pPhId, String pPhDescription, String pPhPath,int pPhContentDescription) {
        phId = pPhId;
        phDescription = pPhDescription;
        phPath = pPhPath;
        phContentDescription = pPhContentDescription;
    }

    public RePhoto(long pPhId, long pPhReId, String pPhDescription, String pPhPath,int pPhContentDescription) {
        phId = pPhId;
        phReId = pPhReId;
        phDescription = pPhDescription;
        phPath = pPhPath;
        phContentDescription = pPhContentDescription;
    }

    public long getPhId() { return phId; }

    public void setPhId(long pPhId) { phId = pPhId; }

    public long getPhReId() { return phReId; }

    public void setPhReId(long pPhReId) { phReId = pPhReId; }

    public String getPhDescription() { return phDescription; }

    public void setPhDescription(String pPhDescription) { phDescription = pPhDescription; }

    public String getPhPath() { return phPath; }

    public void setPhPath(String pPhPath) { phPath = pPhPath; }

    public int getPhContentDescription() { return phContentDescription; }

    public void setPhContentDescription(int pPhContentDescription) { phContentDescription = pPhContentDescription; }
}
