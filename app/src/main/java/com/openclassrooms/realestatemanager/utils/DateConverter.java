package com.openclassrooms.realestatemanager.utils;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by Florence LE BOURNOT on 09/12/2020
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}
