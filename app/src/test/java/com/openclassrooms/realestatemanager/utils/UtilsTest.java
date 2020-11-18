package com.openclassrooms.realestatemanager.utils;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Florence LE BOURNOT on 15/11/2020
 */
public class UtilsTest {

    @Test
    public void convertEuroToDollarWithSuccess() {
        int lNewPrice = Utils.convertEuroToDollar(200);
        assertEquals(236,lNewPrice);
    }

    @Test
    public void getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String lNewDateFormatted = Utils.getTodayDate();
        assertEquals(dateFormat.format(new Date()),lNewDateFormatted);
    }
}
