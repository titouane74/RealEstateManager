package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.ArrayAdapter;

import com.openclassrooms.realestatemanager.R;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Florence LE BOURNOT on 23/11/2020
 */

public class REMHelper {

    private static final String TAG = "TAG_REMHelper";

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
        NumberFormat lNf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        lNf.setMaximumFractionDigits(0);
        return lNf.format(pValue);
    }

    /**
     * Convert a string number with currency
     *
     * @param pStringValue : string : string number with currency to convert into an int
     * @return : string : value without format and currency
     */
    /*
    public static String formatStringNumberWithCommaAndCurrency(String pStringValue) {
        Log.d(TAG, "formatStringNumberWithCommaAndCurrency: " + pStringValue);
        if (!pStringValue.contains(Currency.getInstance(Locale.getDefault()).getSymbol())) {
            int lIntValue = Integer.parseInt(pStringValue);
            Log.d(TAG, "formatStringNumberWithCommaAndCurrency: intValue" + lIntValue);
            String pValueFormatted = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(lIntValue);
            Log.d(TAG, "formatStringNumberWithCommaAndCurrency: pValueFormatted " + pValueFormatted);
            return pValueFormatted;
//            return pValueFormatted.substring(0, pValueFormatted.length() - 3);
        } else {
            return pStringValue;
        }
    }

    public static String removeCurrency(String pStringValue) {
        String lCurrency = Currency.getInstance(Locale.getDefault()).getSymbol();
        int lPosCur = pStringValue.indexOf(lCurrency);
        if (lPosCur <= 1) {
            return pStringValue.substring(lPosCur, pStringValue.length() -1);
        } else {
            return pStringValue.substring(0, pStringValue.length() - 2);
        }

    }

*/
    /**
     * Convert a string number with currency into an int
     *
     * @param pValue : string : string number with currency to convert into an int
     * @return : int : value converted in int
     */
    /*
    public static int formatStringNumberWithCommaAndCurrencyToInt(String pValue) {
        Log.d(TAG, "formatStringNumberWithCommaAndCurrencyToInt: pvalue " + pValue);

        pValue = removeCurrency(pValue);
        Log.d(TAG, "formatStringNumberWithCommaAndCurrencyToInt: pvalue " + pValue);

        pValue = pValue.replaceAll(",", "");
//        pValue = pValue.substring(0, pValue.length() - 3);
        return Integer.parseInt(pValue);
//        return Integer.parseInt(newString);
    }

     */

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
            //return R.id.nav_host_fragment;
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

    public static int getPositionInSpinner(Context pContext, int pResource, String pValue) {
        return configureSpinAdapter(pContext, pResource).getPosition(pValue);
    }

    public static String convertSpinRoomToString(int pValue) {
        if (pValue == 5) {
            return "5 +";
        } else {
            return String.valueOf(pValue);
        }
    }

    public static int getPositionInRoomSpinner(Context pContext, int pResource, int pRoom) {
        return configureSpinAdapter(pContext, pResource).getPosition(convertSpinRoomToString(pRoom));
    }

    public static int convertSpinnerValueToInt(String pValue) {
        if (pValue.indexOf("+") > 0) {
            return Integer.parseInt(pValue.substring(0, 1));
        } else {
            return Integer.parseInt(pValue);
        }
    }

    public static String convertSpinTypeToString(int pValue) {
        if (pValue == 10) {
            return "10 +";
        } else {
            return String.valueOf(pValue);
        }
    }

    public static String formatStringToDayMonthYearString(String pDate) {
        SimpleDateFormat sFrom = new SimpleDateFormat("yyyy-MM-dd");
        java.text.SimpleDateFormat sTo = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sTo.format(sFrom.parse(pDate));
        } catch (ParseException pE) {
            pE.printStackTrace();
        }
        return "";
    }

    public static String formatStringToYearMonthDayString(String pDate) {
        SimpleDateFormat sFrom = new SimpleDateFormat("dd/MM/yyyy");
        java.text.SimpleDateFormat sTo = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sTo.format(sFrom.parse(pDate));
        } catch (ParseException pE) {
            pE.printStackTrace();
        }
        return "";
    }
}
