package com.openclassrooms.realestatemanager.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.openclassrooms.realestatemanager.utils.REMHelper;

/**
 * Created by Florence LE BOURNOT on 08/12/2020
 */
@Entity(tableName = "location",
        foreignKeys = @ForeignKey(entity = RealEstate.class,
                parentColumns = "reId",
                childColumns = "locreid"))

public class ReLocation {

    @PrimaryKey(autoGenerate = true)
    private long locId;

    @ColumnInfo(name = "locreid", index = true)
    private long locReId;

    private String locStreet;
    private String locDistrict;
    private String locCity;
    private String locCounty;
    private String locZipCode;
    private String locCountry;
    private double locLatitude;
    private double locLongitude;

    public ReLocation() {
    }

    public ReLocation(String pLocStreet, String pLocDistrict, String pLocCity, String pLocCounty,
                      String pLocZipCode, String pLocCountry, double pLocLatitude, double pLocLongitude) {
        locStreet = pLocStreet;
        locDistrict = pLocDistrict;
        locCity = pLocCity;
        locCounty = pLocCounty;
        locZipCode = pLocZipCode;
        locCountry = pLocCountry;
        locLatitude = pLocLatitude;
        locLongitude = pLocLongitude;
    }

    public ReLocation(long pLocReId, String pLocStreet, String pLocDistrict, String pLocCity,
                      String pLocCounty, String pLocZipCode, String pLocCountry, double pLocLatitude, double pLocLongitude) {
        locReId = pLocReId;
        locStreet = pLocStreet;
        locDistrict = pLocDistrict;
        locCity = pLocCity;
        locCounty = pLocCounty;
        locZipCode = pLocZipCode;
        locCountry = pLocCountry;
        locLatitude = pLocLatitude;
        locLongitude = pLocLongitude;
    }

    public ReLocation(long pLocId, long pLocReId, String pLocStreet, String pLocDistrict, String pLocCity, String pLocCounty,
                      String pLocZipCode, String pLocCountry, double pLocLatitude, double pLocLongitude) {
        locId = pLocId;
        locReId = pLocReId;
        locStreet = pLocStreet;
        locDistrict = pLocDistrict;
        locCity = pLocCity;
        locCounty = pLocCounty;
        locZipCode = pLocZipCode;
        locCountry = pLocCountry;
        locLatitude = pLocLatitude;
        locLongitude = pLocLongitude;
    }

    public long getLocId() {
        return locId;
    }

    public void setLocId(long pLocId) {
        locId = pLocId;
    }

    public long getLocReId() {
        return locReId;
    }

    public void setLocReId(long pLocReId) {
        locReId = pLocReId;
    }

    public String getLocStreet() {
        return locStreet;
    }

    public void setLocStreet(String pLocStreet) {
        locStreet = pLocStreet;
    }

    public String getLocDistrict() {
        return locDistrict;
    }

    public void setLocDistrict(String pLocDistrict) {
        locDistrict = pLocDistrict;
    }

    public String getLocCity() {
        return locCity;
    }

    public void setLocCity(String pLocCity) {
        locCity = pLocCity;
    }

    public String getLocCounty() {
        return locCounty;
    }

    public void setLocCounty(String pLocCounty) {
        locCounty = pLocCounty;
    }

    public String getLocZipCode() {
        return locZipCode;
    }

    public void setLocZipCode(String pLocZipCode) {
        locZipCode = pLocZipCode;
    }

    public String getLocCountry() {
        return locCountry;
    }

    public void setLocCountry(String pLocCountry) {
        locCountry = pLocCountry;
    }

    public double getLocLatitude() { return locLatitude; }

    public void setLocLatitude(double pLocLatitude) { locLatitude = pLocLatitude; }

    public double getLocLongitude() { return locLongitude; }

    public void setLocLongitude(double pLocLongitude) { locLongitude = pLocLongitude; }

    @Override
    public String toString() {
        String lString = "";
        String lSeparator = "\n";

        if ((locStreet != null) && (!locStreet.equals(""))) {
            lString = REMHelper.addValueAndSeparatorToString(lString, lSeparator, locStreet);
        }
        if ((locDistrict != null) && (!locDistrict.equals(""))) {
            lString = REMHelper.addValueAndSeparatorToString(lString, lSeparator, locDistrict);
        }
        if ((locCity != null) && (!locCity.equals(""))) {
            lString = REMHelper.addValueAndSeparatorToString(lString, lSeparator, locCity);
        }
        if ((locCounty != null) && (!locCounty.equals(""))) {
            lString = REMHelper.addValueAndSeparatorToString(lString, lSeparator, locCounty);
        }
        if ((locZipCode != null) && (!locZipCode.equals(""))) {
            lString = REMHelper.addValueAndSeparatorToString(lString, lSeparator, locZipCode);
        }
        if ((locCountry != null) && (!locCountry.equals(""))) {
            lString = REMHelper.addValueAndSeparatorToString(lString, lSeparator, locCountry);
        }
        return lString;
    }
}
