package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Florence LE BOURNOT on 23/11/2020
 */

public class REMHelper {

    private static final String TAG = "TAG_REMHelper";

    /**
     * Configure the spinner adapter
     * @param pContext : context : context where the spinner is
     * @param pResources : int : number of the resource which gonna implement the spinner
     * @return : adapter : configured adapter
     */
    public static ArrayAdapter<CharSequence> configureSpinAdapter(Context pContext, int pResources) {
        ArrayAdapter<CharSequence> lAdapter = ArrayAdapter.createFromResource(pContext,pResources,android.R.layout.simple_spinner_item);
        lAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return lAdapter;
    }

    /**
     * Concatenate a value to a string with a specific separator
     * @param pString : string : string to implement
     * @param pSeparator : string : separator
     * @param pValue : string : string value to add
     * @return : string : concatenated string
     */
    public static String addValueAndSeparatorToString(String pString, String pSeparator, String pValue) {
        if (pString.length()>0) {
            pString = pString + " " + pSeparator + " ";
        }
        return pString + pValue;
    }

    /**
     * Format an int number into a formatted string with currency
     * @param pValue : int : value to convert
     * @return : string : value formatted with currency
     */
    public static String formatNumberWithCommaAndCurrency(int pValue) {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(pValue);
    }

    /**
     * Convert a string number with currency into an int
     * @param pStringValue : string : string number with currency to convert into an int
     * @return : string : value without format and currency
     */
    public static String formatStringNumberWithCommaAndCurrency(String pStringValue) {
        int lIntValue = Integer.parseInt(pStringValue);
        String pValueFormatted = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(lIntValue);
        return pValueFormatted.substring(0, pValueFormatted.length()-3);
    }

    /**
     * Convert a string number with currency into an int
     * @param pValue : string : string number with currency to convert into an int
     * @return : int : value converted in int
     */
    public static int formatStringNumberWithCommaAndCurrencyToInt(String pValue) {
        pValue = pValue.replaceAll(",","");
        pValue = pValue.substring(1, pValue.length()-3);
        return  Integer.parseInt(pValue);
    }
}
