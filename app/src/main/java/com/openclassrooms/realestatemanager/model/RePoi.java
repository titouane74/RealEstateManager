package com.openclassrooms.realestatemanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Florence LE BOURNOT on 08/12/2020
 */
@Entity(tableName = "poi",
        foreignKeys = @ForeignKey(entity = RealEstate.class,
                parentColumns = "reid",
                childColumns = "poireid"))

public class RePoi {
    @PrimaryKey(autoGenerate = true)
    private long poiId;

    @ColumnInfo(name = "poireid",index = true)
    private long poiReId;

    private String poiName;

    public RePoi() {}

    public RePoi(long pPoiId, long pPoiReId, String pPoiName) {
        poiId = pPoiId;
        poiReId = pPoiReId;
        poiName = pPoiName;
    }

    public long getPoiId() { return poiId; }

    public void setPoiId(long pPoiId) { poiId = pPoiId; }

    public long getPoiReId() {
        return poiReId;
    }

    public void setPoiReId(long pPoiReId) { poiReId = pPoiReId; }

    public String getPoiName() { return poiName; }

    public void setPoiName(String pPoiName) { poiName = pPoiName; }
}
