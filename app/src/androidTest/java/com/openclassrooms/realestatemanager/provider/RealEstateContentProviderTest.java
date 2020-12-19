package com.openclassrooms.realestatemanager.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.ReDatabase;
import com.openclassrooms.realestatemanager.utils.DateConverter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Florence LE BOURNOT on 14/12/2020
 */
@RunWith(AndroidJUnit4.class)
public class RealEstateContentProviderTest {
    private static final String TAG = "TAG_RealEstateContentProvid";

    // FOR DATA
    private ContentResolver mContentResolver;

    // DATA SET FOR TEST
    private static long USER_ID = 50;

    @Before
    public void setUp() {
        Room.inMemoryDatabaseBuilder(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext(),
                ReDatabase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver =  androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext().getContentResolver();
    }

    @Test
    public void getRealEstate() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_RE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        Log.d(TAG, "getRealEstate: " + cursor.getCount());
        for (int i=0; i < cursor.getCount();i++){
            cursor.moveToPosition(i);
            Log.d(TAG, "getReWhenNoReInserted: =======================================");
            Log.d(TAG, "getReWhenNoReInserted: reId ="+cursor.getLong(cursor.getColumnIndexOrThrow("reId")));
            Log.d(TAG, "getReWhenNoReInserted: reType ="+cursor.getString(cursor.getColumnIndexOrThrow("reType")));
            Log.d(TAG, "getReWhenNoReInserted: rePrice ="+cursor.getInt(cursor.getColumnIndexOrThrow("rePrice")));
            Log.d(TAG, "getReWhenNoReInserted: reArea ="+cursor.getInt(cursor.getColumnIndexOrThrow("reArea")));
            Log.d(TAG, "getReWhenNoReInserted: reNbRooms ="+cursor.getInt(cursor.getColumnIndexOrThrow("reNbRooms")));
            Log.d(TAG, "getReWhenNoReInserted: reNbBedrooms ="+cursor.getInt(cursor.getColumnIndexOrThrow("reNbBedrooms")));
            Log.d(TAG, "getReWhenNoReInserted: reNbBathrooms ="+cursor.getInt(cursor.getColumnIndexOrThrow("reNbBathrooms")));
            Log.d(TAG, "getReWhenNoReInserted: reDescription ="+cursor.getString(cursor.getColumnIndexOrThrow("reDescription")));
            Log.d(TAG, "getReWhenNoReInserted: reIsSold ="+cursor.getString(cursor.getColumnIndexOrThrow("reIsSold")));
            Log.d(TAG, "getReWhenNoReInserted: reAgentFirstName ="+cursor.getString(cursor.getColumnIndexOrThrow("reAgentFirstName")));
            Log.d(TAG, "getReWhenNoReInserted: reAgentLastName ="+cursor.getLong(cursor.getColumnIndexOrThrow("reAgentLastName")));
            Log.d(TAG, "getReWhenNoReInserted: reSaleDate ="+cursor.getLong(cursor.getColumnIndexOrThrow("reSaleDate")));
            Log.d(TAG, "getReWhenNoReInserted: reOnMarketDate ="+cursor.getString(cursor.getColumnIndexOrThrow("reOnMarketDate")));
            Log.d(TAG, "getReWhenNoReInserted: reIsMandatoryDataComplete ="+cursor.getString(cursor.getColumnIndexOrThrow("reIsMandatoryDataComplete")));
        }
        cursor.close();
    }

    @Test
    public void insertAndGetRealEstate() {
        // BEFORE : Adding demo real estate
        final Uri userUri = mContentResolver.insert(RealEstateContentProvider.URI_RE, generateRealEstate());
        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_RE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("reType")), is("House"));
    }


    private ContentValues generateRealEstate(){
        Date lDate = new Date();

        final ContentValues lValues = new ContentValues();
        lValues.put("reId","102");
        lValues.put("reType","House");
        lValues.put("rePrice","650000");
        lValues.put("reArea","150");
        lValues.put("reNbRooms","5");
        lValues.put("reNbBedrooms","3");
        lValues.put("reNbBathrooms","2");
        lValues.put("reDescription","House with beautiful garden");
        lValues.put("reIsSold","true");
        lValues.put("reAgentFirstName","Agent first name");
        lValues.put("reAgentLastName","Agent last name");
        lValues.put("reSaleDate",DateConverter.fromDate(lDate));
        lValues.put("reOnMarketDate", DateConverter.fromDate(lDate));
        lValues.put("reIsMandatoryDataComplete","true");
        return lValues;
    }

}
