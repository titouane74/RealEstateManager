package com.openclassrooms.realestatemanager;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Florence LE BOURNOT on 17/11/2020
 */
@RunWith(AndroidJUnit4.class)
public class UtilsTest {

    @Test
    public void isInternetAvailable() {
        assertEquals(true, Utils.isInternetAvailable(InstrumentationRegistry.getContext()));
    }
}