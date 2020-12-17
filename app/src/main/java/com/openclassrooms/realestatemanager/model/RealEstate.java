package com.openclassrooms.realestatemanager.model;

import android.content.ContentValues;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.openclassrooms.realestatemanager.utils.DateConverter;

import java.util.Date;


/**
 * Created by Florence LE BOURNOT on 25/11/2020
 */
@Entity(tableName="realestate")
public class RealEstate {

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
    @Nullable
    private Date reSaleDate;
    @Nullable
    private Date reOnMarketDate;
    private boolean reIsMandatoryDataComplete;
    private int reNbPhotos;

    public RealEstate() {
    }

    public RealEstate(String pReType, int pRePrice, int pReArea, int pReNbRooms, int pReNbBedrooms, int pReNbBathrooms, String pReDescription, boolean pReIsSold, String pReAgentFirstName, String pReAgentLastName,
                      @Nullable Date pReSaleDate, @Nullable Date pReOnMarketDate, boolean pReIsMandatoryDataComplete, int pReNbPhotos) {
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
        reIsMandatoryDataComplete = pReIsMandatoryDataComplete;
        reNbPhotos = pReNbPhotos;
    }

    public RealEstate(long pReId, String pReType, int pRePrice, int pReArea, int pReNbRooms, int pReNbBedrooms,
                      int pReNbBathrooms, String pReDescription, boolean pReIsSold, String pReAgentFirstName,
                      String pReAgentLastName, @Nullable Date pReSaleDate, @Nullable Date pReOnMarketDate,
                      boolean pReIsMandatoryDataComplete, int pReNbPhotos) {
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
        reIsMandatoryDataComplete = pReIsMandatoryDataComplete;
        reNbPhotos = pReNbPhotos;
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

    public String getReAgentFirstName() {
        return reAgentFirstName;
    }

    public void setReAgentFirstName(String pReAgentFirstName) {
        reAgentFirstName = pReAgentFirstName;
    }

    public String getReAgentLastName() {
        return reAgentLastName;
    }

    public void setReAgentLastName(String pReAgentLastName) {
        reAgentLastName = pReAgentLastName;
    }

    public Date getReSaleDate() {
        return reSaleDate;
    }

    public void setReSaleDate(Date pReSaleDate) {
        reSaleDate = pReSaleDate;
    }

    public Date getReOnMarketDate() {
        return reOnMarketDate;
    }

    public void setReOnMarketDate(Date pReOnMarketDate) {
        reOnMarketDate = pReOnMarketDate;
    }

    public boolean isReIsMandatoryDataComplete() {
        return reIsMandatoryDataComplete;
    }

    public void setReIsMandatoryDataComplete(boolean pReIsMandatoryDataComplete) {
        reIsMandatoryDataComplete = pReIsMandatoryDataComplete;
    }

    public int getReNbPhotos() {
        return reNbPhotos;
    }

    public void setReNbPhotos(int pReNbPhotos) {
        reNbPhotos = pReNbPhotos;
    }

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
                ", reOnMarketDate=" + reOnMarketDate +
                '}';
    }

    // FOR CONTENT PROVIDER
    public static RealEstate fromContentValues(ContentValues pValues) {
        final RealEstate lRe = new RealEstate();
        if (pValues.containsKey("reId")) lRe.setReId(pValues.getAsLong("reId"));
        if (pValues.containsKey("reType")) lRe.setReType(pValues.getAsString("reType"));
        if (pValues.containsKey("rePrice")) lRe.setRePrice(pValues.getAsInteger("rePrice"));
        if (pValues.containsKey("reArea")) lRe.setReArea(pValues.getAsInteger("reArea"));
        if (pValues.containsKey("reNbRooms")) lRe.setReNbRooms(pValues.getAsInteger("reNbRooms"));
        if (pValues.containsKey("reNbBedrooms")) lRe.setReNbBedrooms(pValues.getAsInteger("reNbBedrooms"));
        if (pValues.containsKey("reNbBathrooms")) lRe.setReNbBathrooms(pValues.getAsInteger("reNbBathrooms"));
        if (pValues.containsKey("reDescription")) lRe.setReDescription(pValues.getAsString("reDescription"));
        if (pValues.containsKey("reIsSold")) lRe.setReIsSold(pValues.getAsBoolean("reIsSold"));
        if (pValues.containsKey("reAgentFirstName")) lRe.setReAgentFirstName(pValues.getAsString("reAgentFirstName"));
        if (pValues.containsKey("reAgentLastName")) lRe.setReAgentLastName(pValues.getAsString("reAgentLastName"));
        if (pValues.containsKey("reSaleDate")) lRe.setReSaleDate(DateConverter.toDate(pValues.getAsLong("reSaleDate")));
        if (pValues.containsKey("reOnMarketDate")) lRe.setReOnMarketDate(DateConverter.toDate(pValues.getAsLong("reOnMarketDate")));
        if (pValues.containsKey("reIsMandatoryDataComplete")) lRe.setReIsMandatoryDataComplete(pValues.getAsBoolean("reIsMandatoryDataComplete"));
        if (pValues.containsKey("reNbPhotos")) lRe.setReNbPhotos(pValues.getAsInteger("reNbPhotos"));
        return lRe;
    }
}
