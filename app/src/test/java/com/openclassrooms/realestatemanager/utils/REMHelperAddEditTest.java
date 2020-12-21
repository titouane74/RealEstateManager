package com.openclassrooms.realestatemanager.utils;

import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RealEstate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Florence LE BOURNOT on 21/12/2020
 */
public class REMHelperAddEditTest {


    private String mRegexAgent;
    private String mRegexStreet;
    private String mRegexCity;
    private String mRegexZipCode;


    @Before
    public void setup() {
        mRegexAgent = "(\\p{L})*((\\s|-|\'?)*\\p{L}\\s?)*";
        mRegexStreet = "((\\d*)?)((\\s|,|-|\'?)*(\\p{L}+)\\s?)*";
        mRegexCity ="(\\p{L}+)((\\s|-|\'?)*\\p{L}\\s?)*";
        mRegexZipCode = "[0-9]{5}";

    }

    @Test
    public void givenAgent_whenCharacterInRegex_thenSucceed() {
        boolean lIsValid = REMHelperAddEdit.controlValidityWithRegex("Aimé-Luc", mRegexAgent);
        assertTrue(lIsValid);
    }

    @Test
    public void givenAgent_whenCharacterNotInRegex_thenFailed() {
        boolean lIsValid = REMHelperAddEdit.controlValidityWithRegex("Jean@Luc",mRegexAgent);
        assertFalse(lIsValid);
    }

    @Test
    public void givenStreet_whenCharacterInRegex_thenSucceed() {
        boolean lIsValid = REMHelperAddEdit.controlValidityWithRegex("24, rue de l'abbé de sainte-marie", mRegexStreet);
        assertTrue(lIsValid);
    }

    @Test
    public void givenStreet_whenCharacterNotInRegex_thenFailed() {
        boolean lIsValid = REMHelperAddEdit.controlValidityWithRegex("24, rue@de l'abbé de sainte-marie", mRegexStreet);
        assertFalse(lIsValid);
    }

    @Test
    public void givenCity_whenCharacterInRegex_thenSucceed() {
        boolean lIsValid = REMHelperAddEdit.controlValidityWithRegex("L'Hay-les roses", mRegexCity);
        assertTrue(lIsValid);
    }

    @Test
    public void givenCity_whenCharacterNotInRegex_thenFailed() {
        boolean lIsValid = REMHelperAddEdit.controlValidityWithRegex("L'Hay-les@roses", mRegexCity);
        assertFalse(lIsValid);
    }

    @Test
    public void givenZipCode_whenCharacterInRegex_thenSucceed() {
        boolean lIsValid = REMHelperAddEdit.controlValidityWithRegex("94220", mRegexZipCode);
        assertTrue(lIsValid);
    }

    @Test
    public void givenZipCode_whenCharacterNotInRegex_thenFailed() {
        boolean lIsValid = REMHelperAddEdit.controlValidityWithRegex("a9123",mRegexZipCode);
        assertFalse(lIsValid);
    }

    @Test
    public void givenMandatoryData_whenAllDataComplete_thenSucceed() {
        RealEstate  lRealEstate = new RealEstate("House", 850000, 120, 5,
                3, 2, "House near the river", false,
                "Anne-Marie", "Titi",
                null, REMHelper.convertStringToDate("01/12/2020"),
                false,0);
        ReLocation lReLocation = new ReLocation("4 rue Nocard", "", "Chanrenton-Le-Pont", "",
                "94220", "",0,0);

        boolean lIsMandatoryDataComplete = REMHelperAddEdit.getIsMandatoryDataComplete(lRealEstate,lReLocation,false);
        assertTrue(lIsMandatoryDataComplete);
    }

    @Test
    public void givenMandatoryData_whenDataIncomplete_thenFailed() {
        RealEstate  lRealEstate = new RealEstate("House", 0, 120, 5,
                3, 2, "House near the river", false,
                "Anne-Marie", "Titi",
                null, REMHelper.convertStringToDate("01/12/2020"),
                false,0);
        ReLocation lReLocation = new ReLocation("", "", "Chanrenton-Le-Pont", "",
                "94220", "",0,0);

        boolean lIsMandatoryDataComplete = REMHelperAddEdit.getIsMandatoryDataComplete(lRealEstate,lReLocation,false);
        assertFalse(lIsMandatoryDataComplete);
    }
}
