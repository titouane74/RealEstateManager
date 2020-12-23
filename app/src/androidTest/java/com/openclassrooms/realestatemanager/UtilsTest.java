package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Florence LE BOURNOT on 17/11/2020
 */
@RunWith(AndroidJUnit4.class)
public class UtilsTest {

    @Before
    public void setup() throws InterruptedException {
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            WifiManager wifiManager = (WifiManager) InstrumentationRegistry.getInstrumentation().getTargetContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            wifiManager.disconnect();
            Thread.sleep(1000 * 8);
        }
    }

    @Test
    public void isInternetAvailable() {
        assertEquals(true, Utils.isInternetAvailable(InstrumentationRegistry.getInstrumentation().getTargetContext()));
    }

    /**
     * Forced to disabled the wifi to test if internet is available
     * Only for API under 28
     * @throws InterruptedException
     */
    @Test
    public void isWifiDisabled() throws InterruptedException {
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            WifiManager wifiManager = (WifiManager) InstrumentationRegistry.getInstrumentation().getTargetContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(false);
            wifiManager.disconnect();

            Thread.sleep(1000 * 8);

            assertEquals(false, Utils.isInternetAvailable(InstrumentationRegistry.getInstrumentation().getTargetContext()));
        }
    }
}