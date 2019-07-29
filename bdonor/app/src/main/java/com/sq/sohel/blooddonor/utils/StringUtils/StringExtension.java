package com.sq.sohel.blooddonor.utils.StringUtils;

import android.databinding.ObservableField;

public final class StringExtension {
    private StringExtension() {
    }

    public static boolean isNullOrEmpty(String value) {
        return (value == null || value.length() == 0);
    }


    public static boolean isNullOrWhiteSpace(String value) {
        if (value == null) return true;
        char[] stringToCharArray = value.toCharArray();

        for (int i = 0; i < stringToCharArray.length; i++) {
            if (!Character.isWhitespace(stringToCharArray[i])) return false;
        }

        return true;
    }


    public static boolean isNullOrWhiteSpace(Object value) {
        if (value == null) return true;
        String str = String.valueOf(value);
        return isNullOrWhiteSpace(str);
    }
}
