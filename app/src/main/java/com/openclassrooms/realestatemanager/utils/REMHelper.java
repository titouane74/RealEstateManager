package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by Florence LE BOURNOT on 23/11/2020
 */

public class REMHelper {

    public static ArrayAdapter<CharSequence> paramSpinAdapter(Context pContext, int pResources) {
        ArrayAdapter<CharSequence> lAdapter = ArrayAdapter.createFromResource(pContext,pResources,android.R.layout.simple_spinner_item);
        lAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return lAdapter;
    }
}
