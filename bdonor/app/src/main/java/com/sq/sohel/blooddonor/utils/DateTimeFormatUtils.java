package com.sq.sohel.blooddonor.utils;

import com.sq.sohel.blooddonor.utils.StringUtils.StringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.sq.sohel.blooddonor.utils.AppConstants.DATE_FORMAT;
import static com.sq.sohel.blooddonor.utils.AppConstants.DISPLAY_DATE_FORMAT;
import static com.sq.sohel.blooddonor.utils.ObservableFieldUtils.ObservableFieldExtension.getStringFromObservableField;

public final class DateTimeFormatUtils {
    private DateTimeFormatUtils() {
    }

    public static String DateFormatChange(String date, String currentFormat, String toFormat){
        if(StringExtension.isNullOrWhiteSpace(date)) return null;
        SimpleDateFormat currentSimpleDateFormat = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat simpleDateToFormat = new SimpleDateFormat(toFormat, Locale.getDefault());
        String result = null;
        try {
            result = simpleDateToFormat.format( currentSimpleDateFormat.parse(date));
        } catch (ParseException e) {

        }
        return result;
    }
}
