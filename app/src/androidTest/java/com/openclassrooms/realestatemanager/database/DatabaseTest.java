package com.openclassrooms.realestatemanager.database;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;

import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RePhoto;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.utils.REMHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Florence LE BOURNOT on 21/12/2020
 */
public class DatabaseTest {

    private ReDatabase mReDatabase;
    private Context mContext;

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    private long mReId;

    private RealEstate mRealEstate = new RealEstate("House", 850000, 120, 5,
            3, 2, "House near the river", false,
            "Anne-Marie", "Agent",
            null, REMHelper.convertStringToDate("01/12/2020"),
            false, 0);
    private ReLocation mReLocation = new ReLocation( "4 rue Nocard", "", "Chanrenton-Le-Pont", "",
            "94220", "", 0, 0);
    private RePoi mRePoiPark = new RePoi(0, "Park");
    private RePoi mRePoiRestaurant = new RePoi(0, "Restaurant");
    private RePoi mRePoiHealth = new RePoi(0, "Health");
    private RePoi mRePoiFood = new RePoi(0, "Food");
    private RePhoto mRePhoto1 = new RePhoto( "description photo 1", "path", 9999);
    private RePhoto mRePhoto2 = new RePhoto( "description photo 2", "path", 8888);
    private RePhoto mRePhoto3 = new RePhoto( "description photo 3", "path", 7777);


    @Before
    public void setUp() {

        mContext = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext();

        mReDatabase = Room.inMemoryDatabaseBuilder(mContext,
                ReDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        mReDatabase.close();
    }

    @Test
    public void insertAndGetRealEstate() throws InterruptedException {
        RealEstate lReReturned;

        //Prepare data for test
        mReDatabase.ReDao().insertRealEstate(mRealEstate);
        mReId = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectMaxReId());

        //Test
        lReReturned = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectRealEstate(mReId));

        assertEquals(mRealEstate.getReType(), lReReturned.getReType());

        //Clear data after test
        mReDatabase.ReDao().deleteRealEstate(mReId);
    }

    @Test
    public void insertAndGetLocation() throws InterruptedException {
        RealEstateComplete lReCompReturned;

        //Prepare data for test
        mReDatabase.ReDao().insertRealEstate(mRealEstate);
        mReId = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectMaxReId());
        mReLocation.setLocReId(mReId);

        //Test
        mReDatabase.ReLocationDao().insertReLocation(mReLocation);
        lReCompReturned = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectReComplete(mReId));

        assertEquals(mReLocation.getLocStreet(), lReCompReturned.getReLocation().getLocStreet());

        //Clear data after test
        mReDatabase.ReDao().deleteRealEstate(mReId);
    }


    @Test
    public void insertAndGetPhoto() throws InterruptedException {
        RealEstateComplete lReCompReturned;

        //Prepare data for test
        mReDatabase.ReDao().insertRealEstate(mRealEstate);
        mReId = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectMaxReId());
        mReLocation.setLocReId(mReId);
        mReDatabase.ReLocationDao().insertReLocation(mReLocation);
        mRePhoto1.setPhReId(mReId);

        //Test
        mReDatabase.RePhotoDao().insertRePhoto(mRePhoto1);
        lReCompReturned = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectReComplete(mReId));

        assertEquals(mReId, lReCompReturned.getRePhotoList().get(0).getPhReId());
        assertEquals( mRePhoto1.getPhDescription(), lReCompReturned.getRePhotoList().get(0).getPhDescription());

        //Clear data after test
        mReDatabase.ReDao().deleteRealEstate(mReId);
    }

    @Test
    public void insertAndGetPoi() throws InterruptedException {
        RealEstateComplete lReCompReturned;

        //Prepare data for test
        mReDatabase.ReDao().insertRealEstate(mRealEstate);
        mReId = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectMaxReId());
        mReLocation.setLocReId(mReId);
        mReDatabase.ReLocationDao().insertReLocation(mReLocation);
        mRePhoto1.setPhReId(mReId);
        mReDatabase.RePhotoDao().insertRePhoto(mRePhoto1);
        mRePoiPark.setPoiReId(mReId);

        //Test
        mReDatabase.RePoiDao().insertRePoi(mRePoiPark);
        lReCompReturned = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectReComplete(mReId));

        assertEquals(mReId, lReCompReturned.getPoiList().get(0).getPoiReId());
        assertEquals(mRePoiPark.getPoiName(), lReCompReturned.getPoiList().get(0).getPoiName());

        //Clear data after test
        mReDatabase.ReDao().deleteRealEstate(mReId);
    }

    @Test
    public void insertAndUpdateRealEstate() throws InterruptedException {
        RealEstateComplete lReReturned;
        RealEstate lReToUpdate;

        //Prepare data for test
        mReDatabase.ReDao().insertRealEstate(mRealEstate);
        mReId = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectMaxReId());

        //Retrieve the real estate to update
        lReReturned = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectReComplete(mReId));
        lReToUpdate= lReReturned.getRealEstate();

        String lNewAgentLastName = "LastName";
        lReToUpdate.setReAgentLastName(lNewAgentLastName);

        //Test
        mReDatabase.ReDao().updateRealEstate(lReToUpdate);

        lReReturned = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectReComplete(mReId));

        assertEquals(lNewAgentLastName, lReReturned.getRealEstate().getReAgentLastName());

        //Clear data after test
        mReDatabase.ReDao().deleteRealEstate(mReId);
    }

    @Test
    public void insertAndUpdateLocation() throws InterruptedException {
        RealEstateComplete lReCompReturned;
        ReLocation lReLocToUpdate;

        //Prepare data for test
        mReDatabase.ReDao().insertRealEstate(mRealEstate);
        mReId = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectMaxReId());
        mReLocation.setLocReId(mReId);
        mReDatabase.ReLocationDao().insertReLocation(mReLocation);

        //Retrieve the real estate to update
        lReCompReturned = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectReComplete(mReId));
        lReLocToUpdate = lReCompReturned.getReLocation();

        String lNewZipCode = "93460";
        lReLocToUpdate.setLocZipCode(lNewZipCode);

        //Test
        mReDatabase.ReLocationDao().updateReLocation(lReLocToUpdate);
        lReCompReturned = LiveDataTestUtil.getValue(mReDatabase.ReDao().selectReComplete(mReId));

        assertEquals(lNewZipCode, lReCompReturned.getReLocation().getLocZipCode());

        //Clear data after test
        mReDatabase.ReDao().deleteRealEstate(mReId);
    }

}
