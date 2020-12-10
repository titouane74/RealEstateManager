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
    private long phImgId;

    public RePhoto() {}

    public RePhoto(String pPhDescription, String pPhPath, long pPhImgId) {
        phDescription = pPhDescription;
        phPath = pPhPath;
        phImgId = pPhImgId;
    }

    public RePhoto(long pPhId, String pPhDescription, String pPhPath, long pPhImgId) {
        phId = pPhId;
        phDescription = pPhDescription;
        phPath = pPhPath;
        phImgId = pPhImgId;
    }

    public RePhoto(long pPhId, long pPhReId, String pPhDescription, String pPhPath, long pPhImgId) {
        phId = pPhId;
        phReId = pPhReId;
        phDescription = pPhDescription;
        phPath = pPhPath;
        phImgId = pPhImgId;
    }

    public long getPhId() { return phId; }

    public void setPhId(long pPhId) { phId = pPhId; }

    public long getPhReId() { return phReId; }

    public void setPhReId(long pPhReId) { phReId = pPhReId; }

    public String getPhDescription() { return phDescription; }

    public void setPhDescription(String pPhDescription) { phDescription = pPhDescription; }

    public String getPhPath() { return phPath; }

    public void setPhPath(String pPhPath) { phPath = pPhPath; }

    public long getPhImgId() { return phImgId; }

    public void setPhImgId(long pPhImgId) { phImgId = pPhImgId; }
}
