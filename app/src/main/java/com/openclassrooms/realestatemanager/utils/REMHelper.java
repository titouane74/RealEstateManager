package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.openclassrooms.realestatemanager.R;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Florence LE BOURNOT on 23/11/2020
 */

public class REMHelper {

    private static final String TAG = "TAG_REMHelper";

    public static ArrayAdapter<CharSequence> paramSpinAdapter(Context pContext, int pResources) {
        ArrayAdapter<CharSequence> lAdapter = ArrayAdapter.createFromResource(pContext,pResources,android.R.layout.simple_spinner_item);
        lAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return lAdapter;
    }

    public static String addValueAndSeparatorToString(String pString, String pSeparator, String pValue) {
        if (pString.length()>0) {
            pString = pString + pSeparator;
        }
        return pString + pValue;
    }

    public static String formatNumberWithCommaAndCurrency(int pValue) {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(pValue);
    }

    public static String formatStringNumberWithCommaAndCurrency(String pStringValue) {
        int lIntValue = Integer.parseInt(pStringValue);
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(lIntValue);
    }
    public static int formatStringNumberWithCommaAndCurrencyToInt(String pValue) {
        pValue = pValue.replaceAll(",","");
        pValue = pValue.substring(1, pValue.length()-3);

        return  Integer.parseInt(pValue);
    }
}
