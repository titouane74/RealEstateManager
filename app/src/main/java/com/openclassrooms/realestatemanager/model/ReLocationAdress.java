package com.openclassrooms.realestatemanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Florence LE BOURNOT on 08/12/2020
 */
@Entity(tableName = "location",
        foreignKeys = @ForeignKey(entity = RealEstate.class,
        parentColumns = "reid",
        childColumns = "locreid"))

public class ReLocationAdress {

    @PrimaryKey(autoGenerate = true)
    private long locId;

    @ColumnInfo(name = "locreid",index = true)
    private long locReId;

    private String locStreet;
    private String locDistrict;
    private String locCity;
    private String locCounty;
    private int locZipCode;
    private String locCountry;

    public ReLocationAdress() {}

    public ReLocationAdress(long pLocReId, String pLocStreet, String pLocDistrict, String pLocCity, String pLocCounty, int pLocZipCode, String pLocCountry) {
        locReId = pLocReId;
        locStreet = pLocStreet;
        locDistrict = pLocDistrict;
        locCity = pLocCity;
        locCounty = pLocCounty;
        locZipCode = pLocZipCode;
        locCountry = pLocCountry;
    }

    public ReLocationAdress(long pLocId, long pLocReId, String pLocStreet, String pLocDistrict, String pLocCity, String pLocCounty, int pLocZipCode, String pLocCountry) {
        locId = pLocId;
        locReId = pLocReId;
        locStreet = pLocStreet;
        locDistrict = pLocDistrict;
        locCity = pLocCity;
        locCounty = pLocCounty;
        locZipCode = pLocZipCode;
        locCountry = pLocCountry;
    }

    public long getLocId() { return locId; }

    public void setLocId(long pLocId) { locId = pLocId; }

    public long getLocReId() { return locReId; }

    public void setLocReId(long pLocReId) { locReId = pLocReId; }

    public String getLocStreet() { return locStreet; }

    public void setLocStreet(String pLocStreet) { locStreet = pLocStreet; }

    public String getLocDistrict() { return locDistrict; }

    public void setLocDistrict(String pLocDistrict) { locDistrict = pLocDistrict; }

    public String getLocCity() { return locCity; }

    public void setLocCity(String pLocCity) { locCity = pLocCity; }

    public String getLocCounty() { return locCounty; }

    public void setLocCounty(String pLocCounty) { locCounty = pLocCounty; }

    public int getLocZipCode() { return locZipCode; }

    public void setLocZipCode(int pLocZipCode) { locZipCode = pLocZipCode; }

    public String getLocCountry() { return locCountry; }

    public void setLocCountry(String pLocCountry) { locCountry = pLocCountry; }

    @Override
    public String toString() {
        return "ReLocation{" +
                "locStreet='" + locStreet + '\'' +
                ", locDistrict='" + locDistrict + '\'' +
                ", locCity='" + locCity + '\'' +
                ", locCounty='" + locCounty + '\'' +
                ", locZipCode=" + locZipCode +
                ", locCountry='" + locCountry + '\'' +
                '}';
    }
}
