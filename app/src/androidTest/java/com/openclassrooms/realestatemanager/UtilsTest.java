package com.openclassrooms.realestatemanager;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.utils.Utils;

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
        assertEquals(true, Utils.isInternetAvailable(InstrumentationRegistry.getInstrumentation().getTargetContext()));
    }
}