package com.openclassrooms.realestatemanager.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Florence LE BOURNOT on 21/12/2020
 */
public class REMHelperTest {

    @Test
    public void givenString_whenAddValueAndSeparator_thenSucceed() {
        String lCondition = " poiName IN ( ";
        String lConditionExpected = lCondition + ", ? ";

        lCondition = REMHelper.addValueAndSeparatorToString(lCondition, ",", " ? ");

        assertEquals(lConditionExpected, lCondition);
    }

    @Test
    public void givenString_whenAddValueAndNoSeparator_thenFailed() {
        String lCondition = " poiName IN ( ";
        String lConditionExpected = lCondition + ", ? ";

        lCondition = REMHelper.addValueAndSeparatorToString(lCondition, "", " ? ");

        assertNotEquals(lConditionExpected, lCondition);
    }

    @Test
    public void given5AsSpinRoom_whenConvertNumberToString_thenSucceed() {

        String lValueReturned = REMHelper.convertSpinRoomToString(5);
        assertEquals("5 +",lValueReturned);
    }

    @Test
    public void given4AsSpinRoom_whenConvertNumberToString_thenSucceed() {

        String lValueReturned = REMHelper.convertSpinRoomToString(4);
        assertEquals("4",lValueReturned);
    }

    @Test
    public void given10AsSpinnerValue_whenConvertStringToInt_thenSucceed() {

        int lValueReturned = REMHelper.convertSpinnerValueToInt("10 +");
        assertEquals(10,lValueReturned);
    }

    @Test
    public void given5AsSpinValue_whenConvertStringToInt_thenSucceed() {

        int lValueReturned = REMHelper.convertSpinnerValueToInt("5 +");
        assertEquals(5,lValueReturned);
    }
}
