package com.openclassrooms.realestatemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

/**
 * Created by Florence LE BOURNOT on 25/11/2020
 */
@Entity(tableName="realestate")
//public class RealEstate implements Parcelable {
public class RealEstate  {

    @PrimaryKey(autoGenerate = true)
    private int reId;
    private String reType;
    private int rePrice;
    private int reArea;
    private int reNbRooms;
    private int reNbBedrooms;
    private int reNbBathrooms;
    private String reDescription;
    private boolean reIsSold;
    private String reAgentFirstName;
    private String reAgentLastName;
//    private Timestamp reSaleDate;
//    private Timestamp reOnMarketDate;

    public RealEstate() { }

    public RealEstate(String pReType, int pRePrice, int pReArea, int pReNbRooms, int pReNbBedrooms, int pReNbBathrooms, String pReDescription, boolean pReIsSold, String pReAgentFirstName, String pReAgentLastName) {
        reType = pReType;
        rePrice = pRePrice;
        reArea = pReArea;
        reNbRooms = pReNbRooms;
        reNbBedrooms = pReNbBedrooms;
        reNbBathrooms = pReNbBathrooms;
        reDescription = pReDescription;
        reIsSold = pReIsSold;
        reAgentFirstName = pReAgentFirstName;
        reAgentLastName = pReAgentLastName;
    }

    public RealEstate(int pReId, String pReType, int pRePrice, int pReArea, int pReNbRooms, int pReNbBedrooms, int pReNbBathrooms, String pReDescription, boolean pReIsSold, String pReAgentFirstName, String pReAgentLastName) {
        reId = pReId;
        reType = pReType;
        rePrice = pRePrice;
        reArea = pReArea;
        reNbRooms = pReNbRooms;
        reNbBedrooms = pReNbBedrooms;
        reNbBathrooms = pReNbBathrooms;
        reDescription = pReDescription;
        reIsSold = pReIsSold;
        reAgentFirstName = pReAgentFirstName;
        reAgentLastName = pReAgentLastName;
    }

    /*
    protected RealEstate(Parcel in) {
        reId = in.readInt();
        reType = in.readString();
        rePrice = in.readInt();
        reArea = in.readInt();
        reNbRooms = in.readInt();
        reNbBedrooms = in.readInt();
        reNbBathrooms = in.readInt();
        reDescription = in.readString();
        reIsSold = in.readByte() != 0;
    }

    public static final Creator<RealEstate> CREATOR = new Creator<RealEstate>() {
        @Override
        public RealEstate createFromParcel(Parcel in) {
            return new RealEstate(in);
        }

        @Override
        public RealEstate[] newArray(int size) {
            return new RealEstate[size];
        }
    };*/

    public int getReId() {
        return reId;
    }

    public void setReId(int pReId) {
        reId = pReId;
    }

    public String getReType() {
        return reType;
    }

    public void setReType(String pReType) {
        reType = pReType;
    }

    public int getRePrice() {
        return rePrice;
    }

    public void setRePrice(int pRePrice) {
        rePrice = pRePrice;
    }

    public int getReArea() {
        return reArea;
    }

    public void setReArea(int pReArea) {
        reArea = pReArea;
    }

    public int getReNbRooms() {
        return reNbRooms;
    }

    public void setReNbRooms(int pReNbRooms) {
        reNbRooms = pReNbRooms;
    }

    public int getReNbBedrooms() {
        return reNbBedrooms;
    }

    public void setReNbBedrooms(int pReNbBedrooms) {
        reNbBedrooms = pReNbBedrooms;
    }

    public int getReNbBathrooms() {
        return reNbBathrooms;
    }

    public void setReNbBathrooms(int pReNbBathrooms) {
        reNbBathrooms = pReNbBathrooms;
    }

    public String getReDescription() {
        return reDescription;
    }

    public void setReDescription(String pReDescription) {
        reDescription = pReDescription;
    }

    public boolean isReIsSold() {
        return reIsSold;
    }

    public void setReIsSold(boolean pReIsSold) {
        reIsSold = pReIsSold;
    }

    public String getReAgentFirstName() { return reAgentFirstName; }

    public void setReAgentFirstName(String pReAgentFirstName) { reAgentFirstName = pReAgentFirstName; }

    public String getReAgentLastName() { return reAgentLastName; }

    public void setReAgentLastName(String pReAgentLastName) { reAgentLastName = pReAgentLastName; }

/*
    public Timestamp getReSaleDate() {
        return reSaleDate;
    }

    public void setReSaleDate(Timestamp pReSaleDate) {
        reSaleDate = pReSaleDate;
    }

    public Timestamp getReOnMarketDate() {
        return reOnMarketDate;
    }

    public void setReOnMarketDate(Timestamp pReOnMarketDate) {
        reOnMarketDate = pReOnMarketDate;
    }
*/

    @Override
    public String toString() {
        return "RealEstate{" +
                "reId=" + reId +
                ", reType='" + reType + '\'' +
                ", rePrice=" + rePrice +
                ", reArea=" + reArea +
                ", reNbRooms=" + reNbRooms +
                ", reNbBedrooms=" + reNbBedrooms +
                ", reNbBathrooms=" + reNbBathrooms +
                ", reDescription='" + reDescription + '\'' +
                ", reIsSold=" + reIsSold +
//                ", reSaleDate=" + reSaleDate +
//                ", reOnMarketDate=" + reOnMarketDate +
                '}';
    }

/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reId);
        dest.writeString(reType);
        dest.writeInt(rePrice);
        dest.writeInt(reArea);
        dest.writeInt(reNbRooms);
        dest.writeInt(reNbBedrooms);
        dest.writeInt(reNbBathrooms);
        dest.writeString(reDescription);
        dest.writeByte((byte) (reIsSold ? 1 : 0));
    }
*/
}
