package com.openclassrooms.realestatemanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.openclassrooms.realestatemanager.R;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Florence LE BOURNOT on 23/11/2020
 */

public class REMHelper {

    private static final String TAG = "TAG_REMHelper";
    public static final String TXT_PROVIDER = "fusedLocationProvider";

    /**
     * Configure the spinner adapter
     *
     * @param pContext   : context : context where the spinner is
     * @param pResources : int : number of the resource which gonna implement the spinner
     * @return : adapter : configured adapter
     */
    public static ArrayAdapter<CharSequence> configureSpinAdapter(Context pContext, int pResources) {
        ArrayAdapter<CharSequence> lAdapter = ArrayAdapter.createFromResource(pContext, pResources, R.layout.spinner_item);
        lAdapter.setDropDownViewResource(R.layout.spinner_item);
        return lAdapter;
    }

    /**
     * Concatenate a value to a string with a specific separator
     *
     * @param pString    : string : string to implement
     * @param pSeparator : string : separator
     * @param pValue     : string : string value to add
     * @return : string : concatenated string
     */
    public static String addValueAndSeparatorToString(String pString, String pSeparator, String pValue) {
        if (pString.length() > 0) {
            pString = pString + pSeparator;
        }
        return pString + pValue;
    }

    /**
     * Format an int number into a formatted string with currency
     *
     * @param pValue : int : value to convert
     * @return : string : value formatted with currency
     */
    public static String formatNumberWithCommaAndCurrency(int pValue) {
        NumberFormat lNf = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        lNf.setMaximumFractionDigits(0);

        String lFormattedValue = lNf.format(pValue);

        String lCur = "$";
        if (lFormattedValue.contains("¤")) {
            lFormattedValue = lFormattedValue.replace("¤",lCur);
        }

        return lFormattedValue;
    }

    /**
     * Get the orientation of the material
     *
     * @param pContext : context
     * @return : int : return the orientation
     */
    public static int getOrientation(Context pContext) {
        return pContext.getResources().getConfiguration().orientation;
    }

    /**
     * Return the id of the nav_host_fragment which must be used when it's a tablet in landscape
     *
     * @param pContext  : context
     * @param pIsTablet : boolean : indicator if the material is a tablet or not
     * @return : return the right nav_host_fragment id
     */
    public static int getNavHostId(Context pContext, boolean pIsTablet) {
        if (isTabletLandscape(pContext, pIsTablet)) {
            return R.id.nav_right_fragment;
        } else {
            return R.id.nav_host_fragment;
        }
    }

    /**
     * Indicator of the support is a tablet in landscape mode
     *
     * @param pContext  : context
     * @param pIsTablet : boolean : is a tablet or not
     * @return : boolean : true if it's a tablet in landscape otherwise false
     */
    public static boolean isTabletLandscape(Context pContext, boolean pIsTablet) {
        return pIsTablet && (getOrientation(pContext) == Configuration.ORIENTATION_LANDSCAPE);
    }

    /**
     * Get the position of the value in the spinner
     *
     * @param pContext  : context
     * @param pResource : int : spinner where is the information
     * @param pValue    : string : value to find position
     * @return : int : return the position of the value
     */
    public static int getPositionInSpinner(Context pContext, int pResource, String pValue) {
        return configureSpinAdapter(pContext, pResource).getPosition(pValue);
    }

    /**
     * Convert for the display the room value 5 into a string equals to the spinner "5 +"
     *
     * @param pValue : int : value to convert into a spinner string with sign
     * @return : string : return the room with a sign
     */
    public static String convertSpinRoomToString(int pValue) {
        if (pValue == 5) {
            return "5 +";
        } else {
            return String.valueOf(pValue);
        }
    }

    /**
     * Get the position of the value in the spinner room
     *
     * @param pContext  : context
     * @param pResource : int : spinner where is the information
     * @param pRoom     : int : value to find position
     * @return : int : position of the value in the spinner
     */
    public static int getPositionInRoomSpinner(Context pContext, int pResource, int pRoom) {
        return configureSpinAdapter(pContext, pResource).getPosition(convertSpinRoomToString(pRoom));
    }

    /**
     * Convert the string spinner value "5 +" into int
     *
     * @param pValue : string : value to convert
     * @return : int : return the value without the sign
     */
    public static int convertSpinnerValueToInt(String pValue) {
        if (pValue.indexOf("+") > 0) {
            return Integer.parseInt(pValue.substring(0, pValue.length() - 2));
        } else {
            return Integer.parseInt(pValue);
        }
    }

    /**
     * Convert for the search the number of photo value 10 into a string equals to the spinner "10 +"
     *
     * @param pValue : int : value to convert into a spinner string with sign
     * @return : string : return the room with a sign
     */
    public static String convertSpinPhotoToString(int pValue) {
        if (pValue == 10) {
            return "10 +";
        } else {
            return String.valueOf(pValue);
        }
    }

    /**
     * Convert a string into a date
     *
     * @param pDate : string : date to convert
     * @return : date : date converted
     */
    @SuppressLint("SimpleDateFormat")
    public static @Nullable
    Date convertStringToDate(String pDate) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return lDateFormat.parse(pDate);
        } catch (ParseException pE) {
            pE.printStackTrace();
        }
        return null;
    }

    /**
     * Convert a date into a string
     *
     * @param pDate : date : date to convert
     * @return : string : date converted
     */
    @SuppressLint("SimpleDateFormat")
    public static @Nullable
    String convertDateToString(Date pDate) {
        SimpleDateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (pDate != null) {
            return lDateFormat.format(pDate);
        } else {
            return null;
        }
    }


    /**
     * Convert a latitude and a longitude into a location
     *
     * @param pLat  : double : latitude
     * @param pLng: double : longitude
     * @return : object : location
     */
    public static Location setCurrentLocation(Double pLat, Double pLng) {
        Location lFusedLocationProvider = new Location(TXT_PROVIDER);
        lFusedLocationProvider.setLatitude(pLat);
        lFusedLocationProvider.setLongitude(pLng);
        return lFusedLocationProvider;
    }
}

