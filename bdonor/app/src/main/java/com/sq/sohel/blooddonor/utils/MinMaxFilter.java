package com.sq.sohel.blooddonor.utils;

import android.text.InputFilter;
import android.text.Spanned;

public final class MinMaxFilter implements InputFilter {

    private int mIntMin, mIntMax;

    public MinMaxFilter(int minValue, int maxValue) {
        this.mIntMin = minValue;
        this.mIntMax = maxValue;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int minCharLength = String.valueOf(mIntMin).length();
            //int maxCharlength = String.valueOf(mIntMax).length();


            int input = Integer.parseInt(dest.toString() + source.toString());
            if (input == 0) {
                return "";
            }
            if ((dest.toString() + source.toString()).length() >= minCharLength) {
                if (isInRange(mIntMin, mIntMax, input))
                    return null;
            } else {
                return source;
            }

        } catch (NumberFormatException nfe) {
        } catch (Exception e) {
        }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}

