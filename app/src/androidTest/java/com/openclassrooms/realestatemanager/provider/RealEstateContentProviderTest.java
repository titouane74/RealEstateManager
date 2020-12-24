package com.openclassrooms.realestatemanager.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.ReDatabase;

import com.openclassrooms.realestatemanager.utils.DateConverter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
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

    private ReDatabase mReDatabase;

    private ContentResolver mContentResolver;

    private Context mContext;

    private long mReId = 99999999;

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        mContext = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext();

        mContentResolver =  androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext().getContentResolver();

        mReDatabase = Room.inMemoryDatabaseBuilder(mContext,
                ReDatabase.class)
                .allowMainThreadQueries()
                .build();
        // : Adding demo real estate
        final Uri reUri = mContentResolver.insert(RealEstateContentProvider.URI_RE, generateRealEstate());

    }
    @After
    public void closeDb() {
        //Delete the demo real estate to avoid the insert unique constraint error
        final int reUriDel = mContentResolver.delete(ContentUris.withAppendedId(RealEstateContentProvider.URI_RE, mReId),null,null);
        mReDatabase.close();
    }
    @Test
    public void getRealEstate() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(RealEstateContentProvider.URI_RE, mReId), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("reType")), is("House"));

    }

    private ContentValues generateRealEstate(){
        Date lDate = new Date();

        final ContentValues lValues = new ContentValues();
        lValues.put("reId",mReId);
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
        lValues.put("reNbPhotos","1");
        return lValues;
    }

}
