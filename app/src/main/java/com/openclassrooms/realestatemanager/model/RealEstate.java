package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.openclassrooms.realestatemanager.utils.DateConverter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * Created by Florence LE BOURNOT on 25/11/2020
 */
@Entity(tableName="realestate")
public class RealEstate  {

    @PrimaryKey(autoGenerate = true)
    private long reId;
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
    private Date reSaleDate;
    private Date reOnMarketDate;

    public RealEstate() { }

    public RealEstate(String pReType, int pRePrice, int pReArea, int pReNbRooms, int pReNbBedrooms, int pReNbBathrooms, String pReDescription, boolean pReIsSold, String pReAgentFirstName, String pReAgentLastName,
                      Date pReSaleDate
            , Date pReOnMarketDate
    ) {
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
        reSaleDate = pReSaleDate;
        reOnMarketDate = pReOnMarketDate;
    }

    public RealEstate(long pReId, String pReType, int pRePrice, int pReArea, int pReNbRooms, int pReNbBedrooms,
                      int pReNbBathrooms, String pReDescription, boolean pReIsSold, String pReAgentFirstName,
                      String pReAgentLastName, Date pReSaleDate
            , Date pReOnMarketDate
    ) {
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
        reSaleDate = pReSaleDate;
        reOnMarketDate = pReOnMarketDate;
    }

    public long getReId() {
        return reId;
    }

    public void setReId(long pReId) {
        reId = pReId;
    }

    public String getReType() {
        return reType;
    }

    public void setReType(String pReType) { reType = pReType; }

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

    public Date getReSaleDate() { return reSaleDate; }

    public void setReSaleDate(Date pReSaleDate) { reSaleDate = pReSaleDate; }

    public Date getReOnMarketDate() { return reOnMarketDate; }

    public void setReOnMarketDate(Date pReOnMarketDate) { reOnMarketDate = pReOnMarketDate; }

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
                ", reSaleDate=" + reSaleDate +
//                ", reOnMarketDate=" + reOnMarketDate +
                '}';
    }

}
