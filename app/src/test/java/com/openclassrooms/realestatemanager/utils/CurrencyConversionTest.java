package com.openclassrooms.realestatemanager.utils;

import com.openclassrooms.realestatemanager.Utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Florence LE BOURNOT on 15/11/2020
 */
public class CurrencyConversionTest {

    @Test
    public void concertEuroToDollarWithSuccess() {
        int lNewPrice = Utils.convertEuroToDollar(200);
        assertEquals(236,lNewPrice);
    }
}
